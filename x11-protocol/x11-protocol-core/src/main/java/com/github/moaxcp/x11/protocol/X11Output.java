package com.github.moaxcp.x11.protocol;

import org.eclipse.collections.api.list.primitive.*;
import org.eclipse.collections.impl.factory.primitive.ByteLists;

import java.io.IOException;

public interface X11Output {
  void writeBool(boolean bool) throws IOException;

  void writeBool(ImmutableBooleanList bool) throws IOException;

  void writeByte(byte b) throws IOException;

  void writeInt8(byte int8) throws IOException;

  void writeInt16(short int16) throws IOException;

  void writeInt32(int int32) throws IOException;

  void writeInt32(ImmutableIntList int32) throws IOException;

  void writeInt64(long int64) throws IOException;

  void writeCard8(byte card8) throws IOException;

  void writeCard8(ImmutableByteList card8) throws IOException;

  void writeCard16(short card16) throws IOException;

  void writeCard16(ImmutableShortList card16) throws IOException;

  void writeCard32(int card32) throws IOException;

  void writeCard32(ImmutableIntList card32) throws IOException;

  void writeCard64(long card64) throws IOException;

  void writeCard64(ImmutableLongList card64) throws IOException;

  void writeFloat(float f) throws IOException;

  void writeFloat(ImmutableFloatList f) throws IOException;

  void writeDouble(double d) throws IOException;

  void writeDouble(ImmutableDoubleList d) throws IOException;

  void writeChar(ImmutableByteList string) throws IOException;

  void writeString8(String string) throws IOException;

  void writeByte(ImmutableByteList bytes) throws IOException;

  void writeVoid(ImmutableByteList bytes) throws IOException;

  void writeFd(int fd) throws IOException;

  void writeFd(ImmutableIntList fd) throws IOException;

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
