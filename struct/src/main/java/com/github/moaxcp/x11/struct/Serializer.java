package com.github.moaxcp.x11.struct;

import java.math.BigInteger;

/**
 * Common serialization interface for reading and writing primitive numeric types
 * from/to a byte array at a given index.
 */
public interface Serializer {
  // boolean primitive: 0=false, 1=true
  boolean readBool(byte[] bytes, int index);
  boolean[] readBool(byte[] bytes, int index, int length);
  void writeBool(byte[] bytes, int index, boolean value);
  void writeBool(byte[] bytes, int index, boolean[] values);

  byte readInt8(byte[] bytes, int index);
  byte[] readInt8(byte[] bytes, int index, int length);
  void writeInt8(byte[] bytes, int index, byte b);
  void writeInt8(byte[] bytes, int index, byte[] values);

  short readUint8(byte[] bytes, int index);
  short[] readUint8(byte[] bytes, int index, int length);
  void writeUint8(byte[] bytes, int index, short b);
  void writeUint8(byte[] bytes, int index, short[] values);

  short readInt16(byte[] bytes, int index);
  short[] readInt16(byte[] bytes, int index, int length);
  void writeInt16(byte[] bytes, int index, short s);
  void writeInt16(byte[] bytes, int index, short[] values);

  int readUint16(byte[] bytes, int index);
  int[] readUint16(byte[] bytes, int index, int length);
  void writeUint16(byte[] bytes, int index, int value);
  void writeUint16(byte[] bytes, int index, int[] values);

  int readInt32(byte[] bytes, int index);
  int[] readInt32(byte[] bytes, int index, int length);
  void writeInt32(byte[] bytes, int index, int value);
  void writeInt32(byte[] bytes, int index, int[] values);

  long readUint32(byte[] bytes, int index);
  long[] readUint32(byte[] bytes, int index, int length);
  void writeUint32(byte[] bytes, int index, long value);
  void writeUint32(byte[] bytes, int index, long[] values);

  long readInt64(byte[] bytes, int index);
  long[] readInt64(byte[] bytes, int index, int length);
  void writeInt64(byte[] bytes, int index, long value);
  void writeInt64(byte[] bytes, int index, long[] values);

  BigInteger readUint64(byte[] bytes, int index);
  BigInteger[] readUint64(byte[] bytes, int index, int length);
  void writeUint64(byte[] bytes, int index, BigInteger value);
  void writeUint64(byte[] bytes, int index, BigInteger[] values);

  float readFloat32(byte[] bytes, int index);
  float[] readFloat32(byte[] bytes, int index, int length);
  void writeFloat32(byte[] bytes, int index, float value);
  void writeFloat32(byte[] bytes, int index, float[] values);

  double readFloat64(byte[] bytes, int index);
  double[] readFloat64(byte[] bytes, int index, int length);
  void writeFloat64(byte[] bytes, int index, double value);
  void writeFloat64(byte[] bytes, int index, double[] values);
}
