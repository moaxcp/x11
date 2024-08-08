package com.github.moaxcp.x11.protocol;

import org.eclipse.collections.api.list.primitive.*;
import org.eclipse.collections.impl.factory.primitive.ByteLists;

import java.io.IOException;

public interface X11Output {
  void writeBool(boolean bool) throws IOException;

  default void writeBool(BooleanList bool) throws IOException{
    for(int i = 0; i < bool.size(); i++) {
      writeBool(bool.get(i));
    }
  }

  void writeByte(byte b) throws IOException;

  void writeInt8(byte int8) throws IOException;

  void writeInt16(short int16) throws IOException;

  void writeInt32(int int32) throws IOException;

  default void writeInt32(IntList int32) throws IOException {
    for(int i = 0; i < int32.size(); i++) {
      writeInt32(int32.get(i));
    }
  }

  void writeInt64(long int64) throws IOException;

  void writeCard8(byte card8) throws IOException;

  default void writeCard8(ByteList card8) throws IOException {
    for(int i = 0; i < card8.size(); i++) {
      writeCard8(card8.get(i));
    }
  }

  void writeCard16(short card16) throws IOException;

  default void writeCard16(ShortList card16) throws IOException {
    for(int i = 0; i < card16.size(); i++) {
      writeCard16(card16.get(i));
    }
  }

  void writeCard32(int card32) throws IOException;

  default void writeCard32(IntList card32) throws IOException {
    for(int i = 0; i < card32.size(); i++) {
      writeCard32(card32.get(i));
    }
  }

  void writeCard64(long card64) throws IOException;

  default void writeCard64(LongList card64) throws IOException {
    for(int i = 0; i < card64.size(); i++) {
      writeCard64(card64.get(i));
    }
  }

  void writeFloat(float f) throws IOException;

  default void writeFloat(FloatList f) throws IOException {
    for(int i = 0; i < f.size(); i++) {
      writeFloat(f.get(i));
    }
  }

  void writeDouble(double d) throws IOException;

  default void writeDouble(DoubleList d) throws IOException {
    for(int i = 0; i < d.size(); i++) {
      writeDouble(d.get(i));
    }
  }

  default void writeChar(ByteList string) throws IOException {
    writeByte(string);
  }

  void writeString8(String string) throws IOException;

  default void writeByte(ByteList bytes) throws IOException {
    for(int i = 0; i < bytes.size(); i++) {
      writeByte(bytes.get(i));
    }
  }

  default void writeVoid(ByteList bytes) throws IOException {
    writeByte(bytes);
  }

  void writeFd(int fd) throws IOException;

  default void writeFd(IntList fd) throws IOException {
    writeInt32(fd);
  }

  default void writePad(int length) throws IOException {
    var result = ByteLists.mutable.withInitialCapacity(length);
    for (int i = 0; i < length; i++) {
      result.add((byte) 0);
    }
    writeByte(result.toImmutable());
  }

  default void writePadAlign(int forLength) throws IOException {
    writePad(XObject.getSizeForPadAlign(forLength));
  }

  default void writePadAlign(int pad, int forLength) throws IOException {
    writePad(XObject.getSizeForPadAlign(pad, forLength));
  }
}
