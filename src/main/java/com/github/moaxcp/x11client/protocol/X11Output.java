package com.github.moaxcp.x11client.protocol;

import java.io.IOException;
import java.lang.String;

public interface X11Output {
  void writeBool(boolean bool) throws IOException;

  void writeByte(byte b) throws IOException;

  void writeInt8(byte int8) throws IOException;

  void writeInt16(short int16) throws IOException;

  void writeInt32(int int32) throws IOException;

  void writeInt32(int[] int32) throws IOException;

  void writeInt64(long int64) throws IOException;

  void writeCard8(byte card8) throws IOException;

  void writeCard8(byte[] card8) throws IOException;

  void writeCard16(short card16) throws IOException;

  void writeCard16(short[] card16) throws IOException;

  void writeCard32(int card32) throws IOException;

  void writeCard32(int[] card32) throws IOException;

  void writeCard64(long card64) throws IOException;

  void writeChar(byte[] string) throws IOException;

  void writeString8(String string) throws IOException;

  void writeByte(byte[] bytes) throws IOException;

  void writeVoid(byte[] bytes) throws IOException;

  default void writePad(int length) throws IOException {
    writeByte(new byte[length]);
  }

  default void writePadAlign(int forLength) throws IOException {
    writePadAlign(4, forLength);
  }

  default void writePadAlign(int pad, int forLength) throws IOException {
    int n = forLength % pad;
    if (n > 0)
      n = pad - n;
    writeByte(new byte[n]);
  }
}
