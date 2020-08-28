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

  void writeChar(char[] string) throws IOException;

  void writeString8(String string) throws IOException;

  void writeByte(byte[] bytes) throws IOException;

  void writeVoid(byte[] bytes) throws IOException;

  void writePad(int length) throws IOException;

  default void writePadAlign(int forLength) throws IOException {
    writeByte(new byte[(4 - forLength % 4) % 4]);
  }

  default void writePadAlign(int pad, int forLength) throws IOException {
    writeByte(new byte[(pad - forLength % pad) % pad]);
  }
}
