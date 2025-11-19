package com.github.moaxcp.x11.struct;

import java.math.BigInteger;

public final class LittleEndianSerializer implements Serializer {

  public static final LittleEndianSerializer INSTANCE = new LittleEndianSerializer();

  public static LittleEndianSerializer littleEndianSerializer() {
    return INSTANCE;
  }

  private LittleEndianSerializer() {

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
    return (short) ((bytes[index] & 0xFF) | ((bytes[index + 1] & 0xFF) << 8));
  }

  public short[] readInt16(byte[] bytes, int index, int length) {
    short[] out = new short[length];
    int pos = index;
    for (int i = 0; i < length; i++) {
      int b0 = bytes[pos++] & 0xFF;
      int b1 = bytes[pos++] & 0xFF;
      out[i] = (short) (b0 | (b1 << 8));
    }
    return out;
  }

  public void writeInt16(byte[] bytes, int index, short s) {
    bytes[index] = (byte) s;
    bytes[index + 1] = (byte) (s >> 8);
  }

  public void writeInt16(byte[] bytes, int index, short[] values) {
    int pos = index;
    for (short v : values) {
      bytes[pos++] = (byte) v;
      bytes[pos++] = (byte) (v >> 8);
    }
  }

  public int readUint16(byte[] bytes, int index) {
    return (bytes[index] & 0xFF) | ((bytes[index + 1] & 0xFF) << 8);
  }

  public int[] readUint16(byte[] bytes, int index, int length) {
    int[] out = new int[length];
    int pos = index;
    for (int i = 0; i < length; i++) {
      int b0 = bytes[pos++] & 0xFF;
      int b1 = bytes[pos++] & 0xFF;
      out[i] = b0 | (b1 << 8);
    }
    return out;
  }

  public void writeUint16(byte[] bytes, int index, int value) {
    bytes[index] = (byte) value;
    bytes[index + 1] = (byte) (value >> 8);
  }

  public void writeUint16(byte[] bytes, int index, int[] values) {
    int pos = index;
    for (int v : values) {
      bytes[pos++] = (byte) v;
      bytes[pos++] = (byte) (v >> 8);
    }
  }

  public int readInt32(byte[] bytes, int index) {
    return (bytes[index] & 0xFF)
        | ((bytes[index + 1] & 0xFF) << 8)
        | ((bytes[index + 2] & 0xFF) << 16)
        | ((bytes[index + 3] & 0xFF) << 24);
  }

  public int[] readInt32(byte[] bytes, int index, int length) {
    int[] out = new int[length];
    int pos = index;
    for (int i = 0; i < length; i++) {
      int b0 = bytes[pos++] & 0xFF;
      int b1 = bytes[pos++] & 0xFF;
      int b2 = bytes[pos++] & 0xFF;
      int b3 = bytes[pos++] & 0xFF;
      out[i] = b0 | (b1 << 8) | (b2 << 16) | (b3 << 24);
    }
    return out;
  }

  public void writeInt32(byte[] bytes, int index, int value) {
    bytes[index] = (byte) value;
    bytes[index + 1] = (byte) (value >> 8);
    bytes[index + 2] = (byte) (value >> 16);
    bytes[index + 3] = (byte) (value >> 24);
  }

  public void writeInt32(byte[] bytes, int index, int[] values) {
    int pos = index;
    for (int v : values) {
      bytes[pos++] = (byte) v;
      bytes[pos++] = (byte) (v >> 8);
      bytes[pos++] = (byte) (v >> 16);
      bytes[pos++] = (byte) (v >> 24);
    }
  }

  public long readUint32(byte[] bytes, int index) {
    return ((long) (bytes[index] & 0xFF))
        | ((long) (bytes[index + 1] & 0xFF) << 8)
        | ((long) (bytes[index + 2] & 0xFF) << 16)
        | ((long) (bytes[index + 3] & 0xFF) << 24);
  }

  public long[] readUint32(byte[] bytes, int index, int length) {
    long[] out = new long[length];
    int pos = index;
    for (int i = 0; i < length; i++) {
      long b0 = bytes[pos++] & 0xFFL;
      long b1 = bytes[pos++] & 0xFFL;
      long b2 = bytes[pos++] & 0xFFL;
      long b3 = bytes[pos++] & 0xFFL;
      out[i] = b0 | (b1 << 8) | (b2 << 16) | (b3 << 24);
    }
    return out;
  }

  public void writeUint32(byte[] bytes, int index, long value) {
    bytes[index] = (byte) value;
    bytes[index + 1] = (byte) (value >> 8);
    bytes[index + 2] = (byte) (value >> 16);
    bytes[index + 3] = (byte) (value >> 24);
  }

  public void writeUint32(byte[] bytes, int index, long[] values) {
    int pos = index;
    for (long v : values) {
      bytes[pos++] = (byte) v;
      bytes[pos++] = (byte) (v >> 8);
      bytes[pos++] = (byte) (v >> 16);
      bytes[pos++] = (byte) (v >> 24);
    }
  }

  public long readInt64(byte[] bytes, int index) {
    return ((long) (bytes[index] & 0xFF))
        | ((long) (bytes[index + 1] & 0xFF) << 8)
        | ((long) (bytes[index + 2] & 0xFF) << 16)
        | ((long) (bytes[index + 3] & 0xFF) << 24)
        | ((long) (bytes[index + 4] & 0xFF) << 32)
        | ((long) (bytes[index + 5] & 0xFF) << 40)
        | ((long) (bytes[index + 6] & 0xFF) << 48)
        | ((long) (bytes[index + 7] & 0xFF) << 56);
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
      out[i] = b0 | (b1 << 8) | (b2 << 16) | (b3 << 24)
          | (b4 << 32) | (b5 << 40) | (b6 << 48) | (b7 << 56);
    }
    return out;
  }

  public void writeInt64(byte[] bytes, int index, long value) {
    bytes[index] = (byte) value;
    bytes[index + 1] = (byte) (value >> 8);
    bytes[index + 2] = (byte) (value >> 16);
    bytes[index + 3] = (byte) (value >> 24);
    bytes[index + 4] = (byte) (value >> 32);
    bytes[index + 5] = (byte) (value >> 40);
    bytes[index + 6] = (byte) (value >> 48);
    bytes[index + 7] = (byte) (value >> 56);
  }

  public void writeInt64(byte[] bytes, int index, long[] values) {
    int pos = index;
    for (long v : values) {
      bytes[pos++] = (byte) v;
      bytes[pos++] = (byte) (v >> 8);
      bytes[pos++] = (byte) (v >> 16);
      bytes[pos++] = (byte) (v >> 24);
      bytes[pos++] = (byte) (v >> 32);
      bytes[pos++] = (byte) (v >> 40);
      bytes[pos++] = (byte) (v >> 48);
      bytes[pos++] = (byte) (v >> 56);
    }
  }

  public BigInteger readUint64(byte[] bytes, int index) {
    byte[] be = new byte[8];
    for (int i = 0; i < 8; i++) {
      be[i] = bytes[index + (7 - i)];
    }
    return new BigInteger(1, be);
  }

  public BigInteger[] readUint64(byte[] bytes, int index, int length) {
    BigInteger[] out = new BigInteger[length];
    int pos = index;
    for (int i = 0; i < length; i++) {
      byte[] be = new byte[8];
      for (int j = 0; j < 8; j++) {
        be[j] = bytes[pos + (7 - j)];
      }
      out[i] = new BigInteger(1, be);
      pos += 8;
    }
    return out;
  }

  public void writeUint64(byte[] bytes, int index, BigInteger value) {
    byte[] mag = value.toByteArray();
    int srcPos = Math.max(0, mag.length - 8);
    int length = mag.length - srcPos;
    for (int i = 0; i < length; i++) {
      bytes[index + i] = mag[mag.length - 1 - i];
    }
    for (int i = length; i < 8; i++) {
      bytes[index + i] = 0;
    }
  }

  public void writeUint64(byte[] bytes, int index, BigInteger[] values) {
    int pos = index;
    for (BigInteger v : values) {
      writeUint64(bytes, pos, v);
      pos += 8;
    }
  }

  public float readFloat32(byte[] bytes, int index) {
    int b0 = bytes[index] & 0xFF;
    int b1 = bytes[index + 1] & 0xFF;
    int b2 = bytes[index + 2] & 0xFF;
    int b3 = bytes[index + 3] & 0xFF;
    int bits = b0 | (b1 << 8) | (b2 << 16) | (b3 << 24);
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
      int bits = b0 | (b1 << 8) | (b2 << 16) | (b3 << 24);
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
    long bits = b0 | (b1 << 8) | (b2 << 16) | (b3 << 24)
        | (b4 << 32) | (b5 << 40) | (b6 << 48) | (b7 << 56);
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
      long bits = b0 | (b1 << 8) | (b2 << 16) | (b3 << 24)
          | (b4 << 32) | (b5 << 40) | (b6 << 48) | (b7 << 56);
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
    return o instanceof LittleEndianSerializer;
  }

  @Override
  public int hashCode() {
    return 1;
  }

  @Override
  public String toString() {
    return "LittleEndianSerializer";
  }
}
