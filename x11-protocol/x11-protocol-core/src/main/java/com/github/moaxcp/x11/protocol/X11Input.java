package com.github.moaxcp.x11.protocol;

import org.eclipse.collections.api.list.primitive.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface X11Input {
  boolean readBool() throws IOException;

  ImmutableBooleanList readBool(int length) throws IOException;

  byte readByte() throws IOException;

  byte peekByte() throws IOException;

  byte readInt8() throws IOException;

  short readInt16() throws IOException;

  int readInt32() throws IOException;

  ImmutableIntList readInt32(int length) throws IOException;

  long readInt64() throws IOException;

  byte readCard8() throws IOException;

  ImmutableByteList readCard8(int length) throws IOException;

  short readCard16() throws IOException;

  ImmutableShortList readCard16(int length) throws IOException;

  int readCard32() throws IOException;

  ImmutableIntList readCard32(int length) throws IOException;

  long readCard64() throws IOException;

  ImmutableLongList readCard64(int length) throws IOException;

  float readFloat() throws IOException;

  ImmutableFloatList readFloat(int length) throws IOException;

  double readDouble() throws IOException;

  ImmutableDoubleList readDouble(int length) throws IOException;

  ImmutableByteList readChar(int length) throws IOException;

  String readString8(int length) throws IOException;

  ImmutableByteList readByte(int length) throws IOException;

  ImmutableByteList readVoid(int length) throws IOException;

  int readFd() throws IOException;

  ImmutableIntList readFd(int length) throws IOException;

  byte[] readPad(int length) throws IOException;

  default void readPadAlign(int forLength) throws IOException {
    readPad(XObject.getSizeForPadAlign(forLength));
  }

  default void readPadAlign(int align, int forLength) throws IOException {
    readPad(XObject.getSizeForPadAlign(align, forLength));
  }

  void peekWith(X11InputConsumer consumer) throws IOException;

  int available() throws IOException;

  default <T> List<T> readObject(XReadFunction<T> reader, int length) throws IOException {
    List<T> result = new ArrayList<>();
    for(int i = 0; i < length; i++) {
      T object = reader.read(this);
      result.add(object);
    }
    return result;
  }
}
