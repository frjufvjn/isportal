package com.hansol.isportal.hostservice;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ByteBufferUtil
{
	private byte[] value;
	private int count;
	private boolean shared;

	public ByteBufferUtil()
	{
		this(16);
	}

	public ByteBufferUtil(int length)
	{
		this.value = new byte[length];
		this.shared = false;
	}

	public ByteBufferUtil(byte[] bytes)
	{
		this(bytes.length + 16);
		append(bytes);
	}

	public ByteBufferUtil(String string)
	{
		this(string.getBytes());
	}

	public synchronized int length()
	{
		return this.count;
	}

	public synchronized int capacity()
	{
		return this.value.length;
	}

	private final void copy()
	{
		byte[] newValue = new byte[this.value.length];
		System.arraycopy(this.value, 0, newValue, 0, this.count);
		this.value = newValue;
		this.shared = false;
	}

	public synchronized void ensureCapacity(int minimumCapacity)
	{
		if (minimumCapacity > this.value.length) {
			expandCapacity(minimumCapacity);
		}
	}

	private void expandCapacity(int minimumCapacity)
	{
		int newCapacity = (this.value.length + 1) * 2;
		if (newCapacity < 0) {
			newCapacity = 2147483647;
		} else if (minimumCapacity > newCapacity) {
			newCapacity = minimumCapacity;
		}
		byte[] newValue = new byte[newCapacity];
		System.arraycopy(this.value, 0, newValue, 0, this.count);
		this.value = newValue;
		this.shared = false;
	}

	public synchronized void setLength(int newLength)
	{
		if (newLength < 0) {
			throw new StringIndexOutOfBoundsException(newLength);
		}
		if (newLength > this.value.length) {
			expandCapacity(newLength);
		}
		if (this.count < newLength)
		{
			if (this.shared) {
				copy();
			}
			for (; this.count < newLength; this.count += 1) {
				this.value[this.count] = 0;
			}
		}
		this.count = newLength;
		if (this.shared) {
			if (newLength > 0)
			{
				copy();
			}
			else
			{
				this.value = new byte[16];
				this.shared = false;
			}
		}
	}

	public synchronized byte byteAt(int index)
	{
		if ((index < 0) || (index >= this.count)) {
			throw new IndexOutOfBoundsException(index + " : index is 0 < index < this.length() ");
		}
		return this.value[index];
	}

	public synchronized void getBytes(int srcBegin, int srcEnd, byte[] dst, int dstBegin)
	{
		if (srcBegin < 0) {
			throw new StringIndexOutOfBoundsException(srcBegin);
		}
		if ((srcEnd < 0) || (srcEnd > this.count)) {
			throw new StringIndexOutOfBoundsException(srcEnd);
		}
		if (srcBegin > srcEnd) {
			throw new StringIndexOutOfBoundsException("srcBegin > srcEnd");
		}
		System.arraycopy(this.value, srcBegin, dst, dstBegin, srcEnd - srcBegin);
	}

	public synchronized void setByteAt(int index, byte ch)
	{
		if ((index < 0) || (index >= this.count)) {
			throw new StringIndexOutOfBoundsException(index);
		}
		if (this.shared) {
			copy();
		}
		this.value[index] = ch;
	}

	public synchronized ByteBufferUtil append(Object obj)
	{
		if ((obj instanceof byte[])) {
			return append((byte[])obj);
		}
		if ((obj instanceof ByteBufferUtil)) {
			return append((ByteBufferUtil)obj);
		}
		return append(String.valueOf(obj));
	}

	public synchronized ByteBufferUtil append(String str)
	{
		return append(str.getBytes());
	}

	public synchronized ByteBufferUtil append(ByteBufferUtil sb)
	{
		return append(sb.toBytes());
	}

	public synchronized ByteBufferUtil append(byte[] str)
	{
		int len = str.length;
		int newcount = this.count + len;
		if (newcount > this.value.length) {
			expandCapacity(newcount);
		}
		System.arraycopy(str, 0, this.value, this.count, len);
		this.count = newcount;
		return this;
	}

	public synchronized ByteBufferUtil append(byte[] str, int offset, int len)
	{
		int newcount = this.count + len;
		if (newcount > this.value.length) {
			expandCapacity(newcount);
		}
		System.arraycopy(str, offset, this.value, this.count, len);
		this.count = newcount;
		return this;
	}

	public synchronized ByteBufferUtil append(boolean b)
	{
		if (b)
		{
			int newcount = this.count + 4;
			if (newcount > this.value.length) {
				expandCapacity(newcount);
			}
			this.value[(this.count++)] = 116;
			this.value[(this.count++)] = 114;
			this.value[(this.count++)] = 117;
			this.value[(this.count++)] = 101;
		}
		else
		{
			int newcount = this.count + 5;
			if (newcount > this.value.length) {
				expandCapacity(newcount);
			}
			this.value[(this.count++)] = 102;
			this.value[(this.count++)] = 97;
			this.value[(this.count++)] = 108;
			this.value[(this.count++)] = 115;
			this.value[(this.count++)] = 101;
		}
		return this;
	}

	public synchronized ByteBufferUtil append(byte c)
	{
		int newcount = this.count + 1;
		if (newcount > this.value.length) {
			expandCapacity(newcount);
		}
		this.value[(this.count++)] = c;
		return this;
	}

	public synchronized ByteBufferUtil append(char c)
	{
		return append((byte)c);
	}

	public synchronized ByteBufferUtil append(int i)
	{
		return append((byte)i);
	}

	public synchronized ByteBufferUtil append(long l)
	{
		return append((byte)(int)l);
	}

	public synchronized ByteBufferUtil append(float f)
	{
		return append((byte)(int)f);
	}

	public synchronized ByteBufferUtil append(double d)
	{
		return append((byte)(int)d);
	}

	public synchronized ByteBufferUtil delete(int start, int end)
	{
		if (start < 0) {
			throw new StringIndexOutOfBoundsException(start);
		}
		if (end > this.count) {
			end = this.count;
		}
		if (start > end) {
			throw new StringIndexOutOfBoundsException();
		}
		int len = end - start;
		if (len > 0)
		{
			if (this.shared) {
				copy();
			}
			System.arraycopy(this.value, start + len, this.value, start, this.count - end);
			this.count -= len;
		}
		return this;
	}

	public synchronized ByteBufferUtil deleteByteAt(int index)
	{
		if ((index < 0) || (index >= this.count)) {
			throw new StringIndexOutOfBoundsException();
		}
		if (this.shared) {
			copy();
		}
		System.arraycopy(this.value, index + 1, this.value, index, this.count - index - 1);
		this.count -= 1;
		return this;
	}

	public synchronized ByteBufferUtil replace(int start, int end, String str)
	{
		if (start < 0) {
			throw new StringIndexOutOfBoundsException(start);
		}
		if (end > this.count) {
			end = this.count;
		}
		if (start > end) {
			throw new StringIndexOutOfBoundsException();
		}
		int len = str.length();
		int newCount = this.count + len - (end - start);
		if (newCount > this.value.length) {
			expandCapacity(newCount);
		} else if (this.shared) {
			copy();
		}
		System.arraycopy(this.value, end, this.value, start + len, this.count - end);
		str.getBytes(0, len, this.value, start);
		this.count = newCount;
		return this;
	}

	public ByteBufferUtil replace(char oldChar, char newChar)
	{
		if (oldChar != newChar)
		{
			int len = this.count;
			int i = -1;
			byte[] value = this.value;
			int off = 0;
			for (;;)
			{
				i++;
				if (i < len) {
					if ((char)value[(off + i)] == oldChar) {
						break;
					}
				}
			}
			if (i < len)
			{
				byte[] buf = new byte[len];
				for (int j = 0; j < i; j++) {
					buf[j] = value[(off + j)];
				}
				while (i < len)
				{
					byte c = value[(off + i)];
					buf[i] = (c == oldChar ? (byte)newChar : c);
					i++;
				}
				return new ByteBufferUtil(buf);
			}
		}
		return this;
	}

	public synchronized String substring(int start)
	{
		return substring(start, this.count);
	}

	public synchronized String substring(int start, int end)
	{
		if (start < 0) {
			throw new StringIndexOutOfBoundsException(start);
		}
		if (end > this.count) {
			throw new StringIndexOutOfBoundsException(end);
		}
		if (start > end) {
			throw new StringIndexOutOfBoundsException(end - start);
		}
		return new String(this.value, start, end - start);
	}

	public synchronized ByteBufferUtil insert(int index, char[] str, int offset, int len)
	{
		if ((index < 0) || (index > this.count)) {
			throw new StringIndexOutOfBoundsException();
		}
		if ((offset < 0) || (offset + len < 0) || (offset + len > str.length)) {
			throw new StringIndexOutOfBoundsException(offset);
		}
		if (len < 0) {
			throw new StringIndexOutOfBoundsException(len);
		}
		int newCount = this.count + len;
		if (newCount > this.value.length) {
			expandCapacity(newCount);
		} else if (this.shared) {
			copy();
		}
		System.arraycopy(this.value, index, this.value, index + len, this.count - index);
		System.arraycopy(str, offset, this.value, index, len);
		this.count = newCount;
		return this;
	}

	public synchronized ByteBufferUtil insert(int offset, Object obj)
	{
		return insert(offset, String.valueOf(obj));
	}

	public synchronized ByteBufferUtil insert(int offset, String str)
	{
		if ((offset < 0) || (offset > this.count)) {
			throw new StringIndexOutOfBoundsException();
		}
		if (str == null) {
			str = String.valueOf(str);
		}
		int len = str.length();
		int newcount = this.count + len;
		if (newcount > this.value.length) {
			expandCapacity(newcount);
		} else if (this.shared) {
			copy();
		}
		System.arraycopy(this.value, offset, this.value, offset + len, this.count - offset);
		str.getBytes(0, len, this.value, offset);
		this.count = newcount;
		return this;
	}

	public synchronized ByteBufferUtil insert(int offset, byte[] str)
	{
		if ((offset < 0) || (offset > this.count)) {
			throw new StringIndexOutOfBoundsException();
		}
		int len = str.length;
		int newcount = this.count + len;
		if (newcount > this.value.length) {
			expandCapacity(newcount);
		} else if (this.shared) {
			copy();
		}
		System.arraycopy(this.value, offset, this.value, offset + len, this.count - offset);
		System.arraycopy(str, 0, this.value, offset, len);
		this.count = newcount;
		return this;
	}

	public ByteBufferUtil insert(int offset, boolean b)
	{
		return insert(offset, String.valueOf(b));
	}

	public synchronized ByteBufferUtil insert(int offset, byte c)
	{
		int newcount = this.count + 1;
		if (newcount > this.value.length) {
			expandCapacity(newcount);
		} else if (this.shared) {
			copy();
		}
		System.arraycopy(this.value, offset, this.value, offset + 1, this.count - offset);
		this.value[offset] = c;
		this.count = newcount;
		return this;
	}

	public ByteBufferUtil insert(int offset, int i)
	{
		return insert(offset, String.valueOf(i));
	}

	public ByteBufferUtil insert(int offset, long l)
	{
		return insert(offset, String.valueOf(l));
	}

	public ByteBufferUtil insert(int offset, float f)
	{
		return insert(offset, String.valueOf(f));
	}

	public ByteBufferUtil insert(int offset, double d)
	{
		return insert(offset, String.valueOf(d));
	}

	public int indexOf(String str)
	{
		return indexOf(str, 0);
	}

	public synchronized int indexOf(String str, int fromIndex)
	{
		return new String(this.value).indexOf(str, fromIndex);
	}

	public synchronized int lastIndexOf(String str)
	{
		return lastIndexOf(str, this.count);
	}

	public synchronized int lastIndexOf(String str, int fromIndex)
	{
		return new String(this.value).lastIndexOf(str, fromIndex);
	}

	public synchronized ByteBufferUtil reverse()
	{
		if (this.shared) {
			copy();
		}
		int n = this.count - 1;
		for (int j = n - 1 >> 1; j >= 0; j--)
		{
			byte temp = this.value[j];
			this.value[j] = this.value[(n - j)];
			this.value[(n - j)] = temp;
		}
		return this;
	}

	public String toString()
	{
		byte[] tmp = new byte[length()];
		getBytes(0, length(), tmp, 0);
		return new String(tmp);
	}

	public byte[] toBytes()
	{
		byte[] rtn = new byte[length()];
		getBytes(0, rtn.length, rtn, 0);
		return rtn;
	}

	final void setShared()
	{
		this.shared = true;
	}

	final byte[] getValue()
	{
		return this.value;
	}

	private synchronized void readObject(ObjectInputStream s)
			throws IOException, ClassNotFoundException
	{
		s.defaultReadObject();
		this.value = ((byte[])this.value.clone());
		this.shared = false;
	}

	public String toHexString()
	{
		return toHexFormat(toBytes());
	}

	private String toHexFormat(byte[] b)
	{
		return toHexFormat(b, 0, b.length - 1);
	}

	String toHexFormat(byte[] b, int beginidx, int endidx)
	{
		int count = 1;
		String hex = "";
		StringBuffer sb = new StringBuffer(b.length * 2 + 1);
		int lastIdx = b.length - 1 < endidx ? b.length - 1 : endidx;
		for (int i = beginidx; i <= lastIdx; i++)
		{
			hex = Integer.toHexString(b[i]);
			if (hex.length() == 1) {
				hex = "0" + hex;
			} else if (hex.length() == 8) {
				hex = hex.substring(6);
			}
			sb.append(hex.toUpperCase() + " ");
			if (count % 16 == 0) {
				sb.append("\n");
			} else if (count % 8 == 0) {
				sb.append(" ");
			}
			count++;
		}
		return sb.toString();
	}
}
