package com.hansol.isportal.hostservice;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class PBXSocketConnector
{
	private Socket socket = null;
	private String serverIp;
	private int serverPort;
	private int timeOut;

	public PBXSocketConnector()
			throws IOException
	{
		this.timeOut = 2000;

		System.out.println("timeOut" + this.timeOut);
		if (this.timeOut == 0) {
			this.timeOut = 20000;
		}
	}

	public void connect(String ip, int port)
			throws UnknownHostException, IOException
	{
		if (this.socket != null)
		{
			if (this.socket.isConnected()) {
				throw new IOException("Already socket connected.");
			}
			this.socket.close();
		}
		this.serverIp = ip;
		this.serverPort = port;

		this.socket = new Socket(this.serverIp, this.serverPort);

		System.out.println("[PBXSocketHost.connect] Socket connected");
	}

	public void close()
			throws IOException
	{
		if (this.socket != null) {
			this.socket.close();
		}
		System.out.println("[PBXSocketHost.close]Socket closed");
	}

	public byte[] call(byte[] sendPacket)
			throws SocketTimeoutException, UnknownHostException, IOException
	{
		if ((this.socket == null) || (!this.socket.isConnected())) {
			connect(this.serverIp, this.serverPort);
		}
		this.socket.setSoTimeout(this.timeOut);
		byte[] recievePacket = null;

		OutputStream toServer = this.socket.getOutputStream();
		InputStream fromServer = this.socket.getInputStream();

		ByteArrayInputStream bis = new ByteArrayInputStream(sendPacket);
		ByteArrayOutputStream bout = new ByteArrayOutputStream();



		int readSize = 1024;
		byte[] bkey = new byte[readSize];
		for (;;)
		{
			int n = bis.read(bkey);
			if (n <= readSize)
			{
				if ((n == 0) || (n == -1)) {
					break;
				}
				bout.write(bkey, 0, n);
				toServer.write(bout.toByteArray());
			}
		}
		BufferedInputStream bisSvr = new BufferedInputStream(fromServer);
		ByteArrayOutputStream boutSvr = new ByteArrayOutputStream();

		int j = 0;
		int checkLen = 0;
		for (;;)
		{
			int n = bisSvr.read(bkey);
			boutSvr.write(bkey, 0, n);
			if (j == 0)
			{
				byte[] rbLenght = new byte[4];
				System.arraycopy(boutSvr.toByteArray(), 4, rbLenght, 0, 4);
				checkLen = BigEndianByteHandler.byteToInt(rbLenght, 0) + 12;
				j++;
			}
			if (boutSvr.size() >= checkLen) {
				break;
			}
		}
		boutSvr.flush();
		recievePacket = boutSvr.toByteArray();
		if (bis != null) {
			bis.close();
		}
		if (bout != null) {
			bout.close();
		}
		if (bisSvr != null) {
			bisSvr.close();
		}
		if (boutSvr != null) {
			boutSvr.close();
		}
		if (toServer != null) {
			toServer.close();
		}
		if (fromServer != null) {
			fromServer.close();
		}
		if (this.socket != null) {
			this.socket.close();
		}
		System.out.println("[PBXSocketHost.call]Socket called");
		return recievePacket;
	}
}
