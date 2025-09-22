package com.github.moaxcp.x11.struct;

import java.math.BigInteger;

public class BigEndianSerializer implements Serializer {

  public byte readInt8(byte[] bytes, int index) {
    return bytes[index];
  }

  public void writeInt8(byte[] bytes, int index, byte b) {
    bytes[index] = b;
  }

  public short readUint8(byte[] bytes, int index) {
    return (short) (bytes[index] & 0xFF);
  }

  public void writeUint8(byte[] bytes, int index, short b) {
    bytes[index] = (byte) (b & 0xFF);
  }

  public short readInt16(byte[] bytes, int index) {
    return (short) ((((bytes[index] & 0xFF) << 8)) | (bytes[index + 1] & 0xFF));
  }
  
  public void writeInt16(byte[] bytes, int index, short s) {
    bytes[index] = (byte) (s >> 8);
    bytes[index + 1] = (byte) s;
  }

  public int readUint16(byte[] bytes, int index) {
    return ((bytes[index] & 0xFF) << 8) | (bytes[index + 1] & 0xFF);
  }

  public void writeUint16(byte[] bytes, int index, int value) {
    bytes[index] = (byte) (value >> 8);
    bytes[index + 1] = (byte) value;
  }

  public int readInt32(byte[] bytes, int index) {
    return ((bytes[index] & 0xFF) << 24)
        | ((bytes[index + 1] & 0xFF) << 16)
        | ((bytes[index + 2] & 0xFF) << 8)
        | (bytes[index + 3] & 0xFF);
  }

  public void writeInt32(byte[] bytes, int index, int value) {
    bytes[index] = (byte) (value >> 24);
    bytes[index + 1] = (byte) (value >> 16);
    bytes[index + 2] = (byte) (value >> 8);
    bytes[index + 3] = (byte) value;
  }

  public long readUint32(byte[] bytes, int index) {
    return ((long) (bytes[index] & 0xFF) << 24)
        | ((long) (bytes[index + 1] & 0xFF) << 16)
        | ((long) (bytes[index + 2] & 0xFF) << 8)
        | ((long) (bytes[index + 3] & 0xFF));
  }

  public void writeUint32(byte[] bytes, int index, long value) {
    bytes[index] = (byte) (value >> 24);
    bytes[index + 1] = (byte) (value >> 16);
    bytes[index + 2] = (byte) (value >> 8);
    bytes[index + 3] = (byte) value;
  }

  public long readInt64(byte[] bytes, int index) {
    return ((long) (bytes[index] & 0xFF) << 56)
        | ((long) (bytes[index + 1] & 0xFF) << 48)
        | ((long) (bytes[index + 2] & 0xFF) << 40)
        | ((long) (bytes[index + 3] & 0xFF) << 32)
        | ((long) (bytes[index + 4] & 0xFF) << 24)
        | ((long) (bytes[index + 5] & 0xFF) << 16)
        | ((long) (bytes[index + 6] & 0xFF) << 8)
        | ((long) (bytes[index + 7] & 0xFF));
  }

  public void writeInt64(byte[] bytes, int index, long value) {
    bytes[index] = (byte) (value >> 56);
    bytes[index + 1] = (byte) (value >> 48);
    bytes[index + 2] = (byte) (value >> 40);
    bytes[index + 3] = (byte) (value >> 32);
    bytes[index + 4] = (byte) (value >> 24);
    bytes[index + 5] = (byte) (value >> 16);
    bytes[index + 6] = (byte) (value >> 8);
    bytes[index + 7] = (byte) value;
  }

  public BigInteger readUint64(byte[] bytes, int index) {
    byte[] buf = new byte[8];
    System.arraycopy(bytes, index, buf, 0, 8);
    return new BigInteger(1, buf);
  }

  public void writeUint64(byte[] bytes, int index, BigInteger value) {
    byte[] mag = value.toByteArray();
    int srcPos = Math.max(0, mag.length - 8);
    int length = mag.length - srcPos;
    int pad = 8 - length;
    for (int i = 0; i < pad; i++) {
      bytes[index + i] = 0;
    }
    System.arraycopy(mag, srcPos, bytes, index + pad, length);
  }

  public float readFloat(byte[] bytes, int index) {
    int bits = readInt32(bytes, index);
    return Float.intBitsToFloat(bits);
  }

  public void writeFloat(byte[] bytes, int index, float value) {
    int bits = Float.floatToIntBits(value);
    writeInt32(bytes, index, bits);
  }

  public double readDouble(byte[] bytes, int index) {
    long bits = readInt64(bytes, index);
    return Double.longBitsToDouble(bits);
  }

  public void writeDouble(byte[] bytes, int index, double value) {
    long bits = Double.doubleToLongBits(value);
    writeInt64(bytes, index, bits);
  }
}
