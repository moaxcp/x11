package com.github.moaxcp.x11client;

import java.io.IOException;

public interface X11Input {
  int readByte() throws IOException;

  int readInt8() throws IOException;

  int readInt16() throws IOException;

  int readInt32() throws IOException;

  int readCard8() throws IOException;

  int readCard16() throws IOException;

  int readCard32() throws IOException;

  String readString8(int length) throws IOException;

  byte[] readBytes(int length) throws IOException;

  void readPad(int forLength) throws IOException;
}
