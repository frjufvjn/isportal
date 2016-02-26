package com.hansol.isportal.hostservice;

public class BigEndianByteHandler
{
	public static final byte[] shortToByte(short s)
	{
		byte[] dest = new byte[2];
		dest[1] = ((byte)(s & 0xFF));
		dest[0] = ((byte)(s >> 8 & 0xFF));
		return dest;
	}

	public static final byte[] intToByte(int i)
	{
		byte[] dest = new byte[4];
		dest[3] = ((byte)(i & 0xFF));
		dest[2] = ((byte)(i >> 8 & 0xFF));
		dest[1] = ((byte)(i >> 16 & 0xFF));
		dest[0] = ((byte)(i >> 24 & 0xFF));
		return dest;
	}

	public static final byte[] intToByte2(int i)
	{
		byte[] dest = new byte[2];
		dest[1] = ((byte)(i & 0xFF));
		dest[0] = ((byte)(i >> 8 & 0xFF));
		return dest;
	}

	public static final byte[] longToByte(long l)
	{
		byte[] dest = new byte[8];
		dest[7] = ((byte)(int)(l & 0xFF));
		dest[6] = ((byte)(int)(l >> 8 & 0xFF));
		dest[5] = ((byte)(int)(l >> 16 & 0xFF));
		dest[4] = ((byte)(int)(l >> 24 & 0xFF));
		dest[3] = ((byte)(int)(l >> 32 & 0xFF));
		dest[2] = ((byte)(int)(l >> 40 & 0xFF));
		dest[1] = ((byte)(int)(l >> 48 & 0xFF));
		dest[0] = ((byte)(int)(l >> 56 & 0xFF));
		return dest;
	}

	public static final byte[] doubleToByte(double d)
	{
		byte[] dest = new byte[8];
		long temp = Double.doubleToLongBits(d);
		dest = longToByte(temp);
		return dest;
	}

	public static final short byteToShort(byte[] src, int offset)
	{
		return (short)((src[offset] & 0xFF) << 8 | src[(offset + 1)] & 0xFF);
	}

	public static final short byteToShort(byte[] src)
	{
		return byteToShort(src, 0);
	}

	public static final int byteToInt(byte[] src, int offset)
	{
		return (src[offset] & 0xFF) << 24 | (src[(offset + 1)] & 0xFF) << 16 | (src[(offset + 2)] & 0xFF) << 8 | src[(offset + 3)] & 0xFF;
	}

	public static final int byte2ToInt(byte[] src, int offset)
	{
		return (src[offset] & 0xFF) << 8 | src[(offset + 1)] & 0xFF;
	}

	public static final int byteToInt(byte[] src, int offset, int len)
	{
		int intValue = 0;
		for (int i = 0; i < len; i++) {
			intValue |= (src[(offset + i)] & 0xFF) << 8 * (3 - i);
		}
		return intValue;
	}

	public static final int byteToInt(byte[] src)
	{
		return byteToInt(src, 0);
	}

	public static final long byteToLong(byte[] src, int offset)
	{
		return byteToInt(src, offset) << 32 | byteToInt(src, offset + 4) & 0xFFFFFFFF;
	}

	public static final long byteToLong(byte[] src)
	{
		return byteToLong(src, 0);
	}

	public static final double byteToDouble(byte[] buffer, int offset)
	{
		long temp = byteToLong(buffer, offset);
		return Double.longBitsToDouble(temp);
	}

	public static final double byteToDouble(byte[] src)
	{
		return byteToDouble(src, 0);
	}

	public static final byte[] setShort(byte[] dest, int offset, short s)
	{
		dest[(offset + 1)] = ((byte)(s & 0xFF));
		dest[offset] = ((byte)(s >> 8 & 0xFF));
		return dest;
	}

	public static final byte[] setInt(byte[] dest, int offset, int i)
	{
		dest[(offset + 3)] = ((byte)(i & 0xFF));
		dest[(offset + 2)] = ((byte)(i >> 8 & 0xFF));
		dest[(offset + 1)] = ((byte)(i >> 16 & 0xFF));
		dest[offset] = ((byte)(i >> 24 & 0xFF));
		return dest;
	}

	public static final byte[] setLong(byte[] dest, int offset, long l)
	{
		setInt(dest, offset, (int)(l >> 32));
		setInt(dest, offset + 4, (int)(l & 0xFFFFFFFF));
		return dest;
	}
}
