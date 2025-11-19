package com.github.moaxcp.x11.struct;

import java.math.BigInteger;

public final class BigEndianSerializer implements Serializer {

  public static final BigEndianSerializer INSTANCE = new BigEndianSerializer();

  public static BigEndianSerializer bigEndianSerializer() {
    return INSTANCE;
  }

  private BigEndianSerializer() {

  }

  public boolean readBool(byte[] bytes, int index) {
    return (bytes[index] & 0xFF) != 0;
  }
  
  public boolean[] readBool(byte[] bytes, int index, int length) {
    boolean[] out = new boolean[length];
    for (int i = 0; i < length; i++) {
      out[i] = (bytes[index + i] & 0xFF) != 0;
    }
    return out;
  }

  public void writeBool(byte[] bytes, int index, boolean value) {
    bytes[index] = (byte) (value ? 1 : 0);
  }

  public void writeBool(byte[] bytes, int index, boolean[] values) {
    for (int i = 0; i < values.length; i++) {
      bytes[index + i] = (byte) (values[i] ? 1 : 0);
    }
  }

  public byte readInt8(byte[] bytes, int index) {
    return bytes[index];
  }

  public byte[] readInt8(byte[] bytes, int index, int length) {
    byte[] out = new byte[length];
    System.arraycopy(bytes, index, out, 0, length);
    return out;
  }

  public void writeInt8(byte[] bytes, int index, byte b) {
    bytes[index] = b;
  }

  public void writeInt8(byte[] bytes, int index, byte[] values) {
    System.arraycopy(values, 0, bytes, index, values.length);
  }

  public short readUint8(byte[] bytes, int index) {
    return (short) (bytes[index] & 0xFF);
  }

  public short[] readUint8(byte[] bytes, int index, int length) {
    short[] out = new short[length];
    for (int i = 0; i < length; i++) {
      out[i] = (short) (bytes[index + i] & 0xFF);
    }
    return out;
  }

  public void writeUint8(byte[] bytes, int index, short b) {
    bytes[index] = (byte) (b & 0xFF);
  }

  public void writeUint8(byte[] bytes, int index, short[] values) {
    for (int i = 0; i < values.length; i++) {
      bytes[index + i] = (byte) (values[i] & 0xFF);
    }
  }

  public short readInt16(byte[] bytes, int index) {
    return (short) ((((bytes[index] & 0xFF) << 8)) | (bytes[index + 1] & 0xFF));
  }
  
  public short[] readInt16(byte[] bytes, int index, int length) {
    short[] out = new short[length];
    int pos = index;
    for (int i = 0; i < length; i++) {
      int b0 = bytes[pos++] & 0xFF;
      int b1 = bytes[pos++] & 0xFF;
      out[i] = (short) ((b0 << 8) | b1);
    }
    return out;
  }
  
  public void writeInt16(byte[] bytes, int index, short s) {
    bytes[index] = (byte) (s >> 8);
    bytes[index + 1] = (byte) s;
  }

  public void writeInt16(byte[] bytes, int index, short[] values) {
    int pos = index;
    for (short v : values) {
      bytes[pos++] = (byte) (v >> 8);
      bytes[pos++] = (byte) v;
    }
  }

  public int readUint16(byte[] bytes, int index) {
    return ((bytes[index] & 0xFF) << 8) | (bytes[index + 1] & 0xFF);
  }

  public int[] readUint16(byte[] bytes, int index, int length) {
    int[] out = new int[length];
    int pos = index;
    for (int i = 0; i < length; i++) {
      int b0 = bytes[pos++] & 0xFF;
      int b1 = bytes[pos++] & 0xFF;
      out[i] = (b0 << 8) | b1;
    }
    return out;
  }

  public void writeUint16(byte[] bytes, int index, int value) {
    bytes[index] = (byte) (value >> 8);
    bytes[index + 1] = (byte) value;
  }

  public void writeUint16(byte[] bytes, int index, int[] values) {
    int pos = index;
    for (int v : values) {
      bytes[pos++] = (byte) (v >> 8);
      bytes[pos++] = (byte) v;
    }
  }

  public int readInt32(byte[] bytes, int index) {
    return ((bytes[index] & 0xFF) << 24)
        | ((bytes[index + 1] & 0xFF) << 16)
        | ((bytes[index + 2] & 0xFF) << 8)
        | (bytes[index + 3] & 0xFF);
  }

  public int[] readInt32(byte[] bytes, int index, int length) {
    int[] out = new int[length];
    int pos = index;
    for (int i = 0; i < length; i++) {
      int b0 = bytes[pos++] & 0xFF;
      int b1 = bytes[pos++] & 0xFF;
      int b2 = bytes[pos++] & 0xFF;
      int b3 = bytes[pos++] & 0xFF;
      out[i] = (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
    }
    return out;
  }

  public void writeInt32(byte[] bytes, int index, int value) {
    bytes[index] = (byte) (value >> 24);
    bytes[index + 1] = (byte) (value >> 16);
    bytes[index + 2] = (byte) (value >> 8);
    bytes[index + 3] = (byte) value;
  }

  public void writeInt32(byte[] bytes, int index, int[] values) {
    int pos = index;
    for (int v : values) {
      bytes[pos++] = (byte) (v >> 24);
      bytes[pos++] = (byte) (v >> 16);
      bytes[pos++] = (byte) (v >> 8);
      bytes[pos++] = (byte) v;
    }
  }

  public long readUint32(byte[] bytes, int index) {
    return ((long) (bytes[index] & 0xFF) << 24)
        | ((long) (bytes[index + 1] & 0xFF) << 16)
        | ((long) (bytes[index + 2] & 0xFF) << 8)
        | ((long) (bytes[index + 3] & 0xFF));
  }

  public long[] readUint32(byte[] bytes, int index, int length) {
    long[] out = new long[length];
    int pos = index;
    for (int i = 0; i < length; i++) {
      long b0 = bytes[pos++] & 0xFFL;
      long b1 = bytes[pos++] & 0xFFL;
      long b2 = bytes[pos++] & 0xFFL;
      long b3 = bytes[pos++] & 0xFFL;
      out[i] = (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
    }
    return out;
  }

  public void writeUint32(byte[] bytes, int index, long value) {
    bytes[index] = (byte) (value >> 24);
    bytes[index + 1] = (byte) (value >> 16);
    bytes[index + 2] = (byte) (value >> 8);
    bytes[index + 3] = (byte) value;
  }

  public void writeUint32(byte[] bytes, int index, long[] values) {
    int pos = index;
    for (long v : values) {
      bytes[pos++] = (byte) (v >> 24);
      bytes[pos++] = (byte) (v >> 16);
      bytes[pos++] = (byte) (v >> 8);
      bytes[pos++] = (byte) v;
    }
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

  public long[] readInt64(byte[] bytes, int index, int length) {
    long[] out = new long[length];
    int pos = index;
    for (int i = 0; i < length; i++) {
      long b0 = bytes[pos++] & 0xFFL;
      long b1 = bytes[pos++] & 0xFFL;
      long b2 = bytes[pos++] & 0xFFL;
      long b3 = bytes[pos++] & 0xFFL;
      long b4 = bytes[pos++] & 0xFFL;
      long b5 = bytes[pos++] & 0xFFL;
      long b6 = bytes[pos++] & 0xFFL;
      long b7 = bytes[pos++] & 0xFFL;
      out[i] = (b0 << 56) | (b1 << 48) | (b2 << 40) | (b3 << 32)
          | (b4 << 24) | (b5 << 16) | (b6 << 8) | b7;
    }
    return out;
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

  public void writeInt64(byte[] bytes, int index, long[] values) {
    int pos = index;
    for (long v : values) {
      bytes[pos++] = (byte) (v >> 56);
      bytes[pos++] = (byte) (v >> 48);
      bytes[pos++] = (byte) (v >> 40);
      bytes[pos++] = (byte) (v >> 32);
      bytes[pos++] = (byte) (v >> 24);
      bytes[pos++] = (byte) (v >> 16);
      bytes[pos++] = (byte) (v >> 8);
      bytes[pos++] = (byte) v;
    }
  }

  public BigInteger readUint64(byte[] bytes, int index) {
    byte[] buf = new byte[8];
    System.arraycopy(bytes, index, buf, 0, 8);
    return new BigInteger(1, buf);
  }

  public BigInteger[] readUint64(byte[] bytes, int index, int length) {
    BigInteger[] out = new BigInteger[length];
    int pos = index;
    for (int i = 0; i < length; i++) {
      byte[] buf = new byte[8];
      System.arraycopy(bytes, pos, buf, 0, 8);
      out[i] = new BigInteger(1, buf);
      pos += 8;
    }
    return out;
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

  public void writeUint64(byte[] bytes, int index, BigInteger[] values) {
    int pos = index;
    for (BigInteger v : values) {
      writeUint64(bytes, pos, v);
      pos += 8;
    }
  }

  public float readFloat32(byte[] bytes, int index) {
    int bits = ((bytes[index] & 0xFF) << 24)
        | ((bytes[index + 1] & 0xFF) << 16)
        | ((bytes[index + 2] & 0xFF) << 8)
        | (bytes[index + 3] & 0xFF);
    return Float.intBitsToFloat(bits);
  }

  public float[] readFloat32(byte[] bytes, int index, int length) {
    float[] out = new float[length];
    int pos = index;
    for (int i = 0; i < length; i++) {
      int b0 = bytes[pos++] & 0xFF;
      int b1 = bytes[pos++] & 0xFF;
      int b2 = bytes[pos++] & 0xFF;
      int b3 = bytes[pos++] & 0xFF;
      int bits = (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
      out[i] = Float.intBitsToFloat(bits);
    }
    return out;
  }

  public void writeFloat32(byte[] bytes, int index, float value) {
    int bits = Float.floatToIntBits(value);
    writeInt32(bytes, index, bits);
  }

  public void writeFloat32(byte[] bytes, int index, float[] values) {
    int pos = index;
    for (float v : values) {
      int bits = Float.floatToIntBits(v);
      writeInt32(bytes, pos, bits);
      pos += 4;
    }
  }

  public double readFloat64(byte[] bytes, int index) {
    long b0 = bytes[index] & 0xFFL;
    long b1 = bytes[index + 1] & 0xFFL;
    long b2 = bytes[index + 2] & 0xFFL;
    long b3 = bytes[index + 3] & 0xFFL;
    long b4 = bytes[index + 4] & 0xFFL;
    long b5 = bytes[index + 5] & 0xFFL;
    long b6 = bytes[index + 6] & 0xFFL;
    long b7 = bytes[index + 7] & 0xFFL;
    long bits = (b0 << 56) | (b1 << 48) | (b2 << 40) | (b3 << 32)
        | (b4 << 24) | (b5 << 16) | (b6 << 8) | b7;
    return Double.longBitsToDouble(bits);
  }

  public double[] readFloat64(byte[] bytes, int index, int length) {
    double[] out = new double[length];
    int pos = index;
    for (int i = 0; i < length; i++) {
      long b0 = bytes[pos++] & 0xFFL;
      long b1 = bytes[pos++] & 0xFFL;
      long b2 = bytes[pos++] & 0xFFL;
      long b3 = bytes[pos++] & 0xFFL;
      long b4 = bytes[pos++] & 0xFFL;
      long b5 = bytes[pos++] & 0xFFL;
      long b6 = bytes[pos++] & 0xFFL;
      long b7 = bytes[pos++] & 0xFFL;
      long bits = (b0 << 56) | (b1 << 48) | (b2 << 40) | (b3 << 32)
          | (b4 << 24) | (b5 << 16) | (b6 << 8) | b7;
      out[i] = Double.longBitsToDouble(bits);
    }
    return out;
  }

  public void writeFloat64(byte[] bytes, int index, double value) {
    long bits = Double.doubleToLongBits(value);
    writeInt64(bytes, index, bits);
  }

  public void writeFloat64(byte[] bytes, int index, double[] values) {
    int pos = index;
    for (double v : values) {
      long bits = Double.doubleToLongBits(v);
      writeInt64(bytes, pos, bits);
      pos += 8;
    }
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof BigEndianSerializer;
  }

  @Override
  public int hashCode() {
    return 1;
  }

  @Override
  public String toString() {
    return "BigEndianSerializer";
  }
}
