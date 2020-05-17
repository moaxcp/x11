package com.github.moaxcp.x11client;

import java.io.IOException;

public interface X11Output {
  void writeByte(int b) throws IOException;

  void writeInt8(int int8) throws IOException;

  void writeInt16(int int16) throws IOException;

  void writeInt32(int int32) throws IOException;

  void writeCard8(int card8) throws IOException;

  void writeCard16(int card16) throws IOException;

  void writeCard32(int card32) throws IOException;

  void writeString8(String string8) throws IOException;

  void writeString8(byte[] string8) throws IOException;

  void writePad(int forLength) throws IOException;
}
