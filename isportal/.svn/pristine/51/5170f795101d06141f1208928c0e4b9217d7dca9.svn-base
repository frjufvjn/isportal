package com.hansol.isportal.hostservice;

public class LittleEndianByteHandler
{
  public static final byte[] shortToByte(short shortVar)
  {
    byte[] littleShort = new byte[2];
    byte[] clittleShort = new byte[2];
    
    littleShort[0] = ((byte)(shortVar >> 0 & 0xFF));
    littleShort[1] = ((byte)(shortVar >> 8 & 0xFF));
    for (int i = 0; i < 2; i++) {
      clittleShort[i] = littleShort[(2 - i)];
    }
    return clittleShort;
  }
  
  public static final byte[] intToByte(int intVar)
  {
    byte[] littleInt = new byte[4];
    byte[] clittleInt = new byte[4];
    
    littleInt[0] = ((byte)(intVar >> 0 & 0xFF));
    littleInt[1] = ((byte)(intVar >> 8 & 0xFF));
    littleInt[2] = ((byte)(intVar >> 16 & 0xFF));
    littleInt[3] = ((byte)(intVar >> 24 & 0xFF));
    for (int i = 0; i < 4; i++) {
      clittleInt[i] = littleInt[(3 - i)];
    }
    return clittleInt;
  }
  
  public static final byte[] intToByte2(int intVar)
  {
    byte[] littleInt = new byte[2];
    byte[] clittleInt = new byte[2];
    
    littleInt[0] = ((byte)(intVar >> 0 & 0xFF));
    littleInt[1] = ((byte)(intVar >> 8 & 0xFF));
    for (int i = 0; i < 2; i++) {
      clittleInt[i] = littleInt[(2 - i)];
    }
    return clittleInt;
  }
  
  public static final byte[] longToByte(long longVar)
  {
    byte[] littleLong = new byte[8];
    byte[] clittleLong = new byte[8];
    
    littleLong[0] = ((byte)(int)(longVar >> 0 & 0xFF));
    littleLong[1] = ((byte)(int)(longVar >> 8 & 0xFF));
    littleLong[2] = ((byte)(int)(longVar >> 16 & 0xFF));
    littleLong[3] = ((byte)(int)(longVar >> 24 & 0xFF));
    littleLong[4] = ((byte)(int)(longVar >> 32 & 0xFF));
    littleLong[5] = ((byte)(int)(longVar >> 40 & 0xFF));
    littleLong[6] = ((byte)(int)(longVar >> 48 & 0xFF));
    littleLong[7] = ((byte)(int)(longVar >> 56 & 0xFF));
    for (int i = 0; i < 8; i++) {
      clittleLong[i] = littleLong[(2 - i)];
    }
    return clittleLong;
  }
  
  public static final byte[] doubleToByte(double doubleVar)
  {
    byte[] littleDouble = new byte[8];
    long temp = Double.doubleToLongBits(doubleVar);
    littleDouble = longToByte(temp);
    return littleDouble;
  }
  
  public static final short byteToShort(byte[] buffer)
  {
    return byteToShort(buffer, 0);
  }
  
  public static final short byteToShort(byte[] buffer, int offset)
  {
    return (short)((buffer[(offset + 1)] & 0xFF) << 8 | buffer[offset] & 0xFF);
  }
  
  public static final int byte1ToInt(byte b)
  {
    return b & 0xFF;
  }
  
  public static final int byte1ToInt(byte[] b, int offset)
  {
    return b[offset] & 0xFF;
  }
  
  public static final int byte2ToInt(byte[] buffer, int offset)
  {
    return (buffer[(offset + 1)] & 0xFF) << 8 | buffer[offset] & 0xFF;
  }
  
  public static final int byteToInt(byte[] buffer)
  {
    return byteToInt(buffer, 0);
  }
  
  public static final int byteToInt(byte[] buffer, int offset)
  {
    return (buffer[(offset + 3)] & 0xFF) << 24 | (buffer[(offset + 2)] & 0xFF) << 16 | (buffer[(offset + 1)] & 0xFF) << 8 | buffer[offset] & 0xFF;
  }
  
  public static final long byteToLong(byte[] buffer)
  {
    return byteToLong(buffer, 0);
  }
  
  public static final long byteToLong(byte[] buffer, int offset)
  {
    return byteToInt(buffer, offset + 4) << 32 | byteToInt(buffer, offset) & 0xFFFFFFFF;
  }
  
  public static final double byteToDouble(byte[] buffer, int offset)
  {
    long temp = byteToLong(buffer, offset);
    return Double.longBitsToDouble(temp);
  }
  
  public static final double byteToDouble(byte[] buffer)
  {
    long temp = byteToLong(buffer, 0);
    return Double.longBitsToDouble(temp);
  }
}
