package com.github.moaxcp.x11.protocol;

import org.eclipse.collections.api.factory.primitive.*;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.*;
import org.eclipse.collections.impl.factory.Lists;

import java.io.IOException;

public interface X11Input {
  boolean readBool() throws IOException;

  default BooleanList readBool(int length) throws IOException {
    var list = BooleanLists.mutable.withInitialCapacity(length);
    for (int i = 0; i < length; i++) {
      list.add(readBool());
    }
    return list.toImmutable();
  }

  byte readByte() throws IOException;

  byte peekByte() throws IOException;

  byte readInt8() throws IOException;

  short readInt16() throws IOException;

  int readInt32() throws IOException;

  default IntList readInt32(int length) throws IOException {
    var list = IntLists.mutable.withInitialCapacity(length);
    for (int i = 0; i < length; i++) {
      list.add(readInt32());
    }
    return list.toImmutable();
  }

  long readInt64() throws IOException;

  byte readCard8() throws IOException;

  default ByteList readCard8(int length) throws IOException {
    var list = ByteLists.mutable.withInitialCapacity(length);
    for (int i = 0; i < length; i++) {
      list.add(readCard8());
    }
    return list.toImmutable();
  }

  short readCard16() throws IOException;

  default ShortList readCard16(int length) throws IOException {
    var list = ShortLists.mutable.withInitialCapacity(length);
    for (int i = 0; i < length; i++) {
      list.add(readCard16());
    }
    return list.toImmutable();
  }

  int readCard32() throws IOException;

  default IntList readCard32(int length) throws IOException {
    var list = IntLists.mutable.withInitialCapacity(length);
    for (int i = 0; i < length; i++) {
      list.add(readCard32());
    }
    return list.toImmutable();
  }

  long readCard64() throws IOException;

  default LongList readCard64(int length) throws IOException {
    var list = LongLists.mutable.withInitialCapacity(length);
    for (int i = 0; i < length; i++) {
      list.add(readCard64());
    }
    return list.toImmutable();
  }

  float readFloat() throws IOException;

  default FloatList readFloat(int length) throws IOException {
    var list = FloatLists.mutable.withInitialCapacity(length);
    for (int i = 0; i < length; i++) {
      list.add(readFloat());
    }
    return list.toImmutable();
  }

  double readDouble() throws IOException;

  default DoubleList readDouble(int length) throws IOException {
    var list = DoubleLists.mutable.withInitialCapacity(length);
    for (int i = 0; i < length; i++) {
      list.add(readDouble());
    }
    return list.toImmutable();
  }

  default ByteList readChar(int length) throws IOException {
    var list = ByteLists.mutable.withInitialCapacity(length);
    for (int i = 0; i < length; i++) {
      list.add(readByte());
    }
    return list.toImmutable();
  }

  String readString8(int length) throws IOException;

  default ByteList readByte(int length) throws IOException {
    var list = ByteLists.mutable.withInitialCapacity(length);
    for (int i = 0; i < length; i++) {
      list.add(readCard8());
    }
    return list.toImmutable();
  }

  default ByteList readVoid(int length) throws IOException {
    return readByte(length);
  }

  int readFd() throws IOException;

  default IntList readFd(int length) throws IOException {
    return readInt32(length);
  }

  byte[] readPad(int length) throws IOException;

  default void readPadAlign(int forLength) throws IOException {
    readPad(XObject.getSizeForPadAlign(forLength));
  }

  default void readPadAlign(int align, int forLength) throws IOException {
    readPad(XObject.getSizeForPadAlign(align, forLength));
  }

  void peekWith(X11InputConsumer consumer) throws IOException;

  int available() throws IOException;

  default <T> ImmutableList<T> readObject(XReadFunction<T> reader, int length) throws IOException {
    var result = Lists.mutable.<T>withInitialCapacity(length);
    for(int i = 0; i < length; i++) {
      T object = reader.read(this);
      result.add(object);
    }
    return result.toImmutable();
  }
}
