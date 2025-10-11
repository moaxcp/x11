package com.github.moaxcp.x11.struct;

import java.math.BigInteger;

/**
 * Common serialization interface for reading and writing primitive numeric types
 * from/to a byte array at a given index.
 */
public interface Serializer {
  // boolean primitive: 0=false, 1=true
  boolean readBoolean(byte[] bytes, int index);
  void writeBoolean(byte[] bytes, int index, boolean value);

  byte readInt8(byte[] bytes, int index);
  void writeInt8(byte[] bytes, int index, byte b);

  short readUint8(byte[] bytes, int index);
  void writeUint8(byte[] bytes, int index, short b);

  short readInt16(byte[] bytes, int index);
  void writeInt16(byte[] bytes, int index, short s);

  int readUint16(byte[] bytes, int index);
  void writeUint16(byte[] bytes, int index, int value);

  int readInt32(byte[] bytes, int index);
  void writeInt32(byte[] bytes, int index, int value);

  long readUint32(byte[] bytes, int index);
  void writeUint32(byte[] bytes, int index, long value);

  long readInt64(byte[] bytes, int index);
  void writeInt64(byte[] bytes, int index, long value);

  BigInteger readUint64(byte[] bytes, int index);
  void writeUint64(byte[] bytes, int index, BigInteger value);

  float readFloat(byte[] bytes, int index);
  void writeFloat(byte[] bytes, int index, float value);

  double readDouble(byte[] bytes, int index);
  void writeDouble(byte[] bytes, int index, double value);
}
