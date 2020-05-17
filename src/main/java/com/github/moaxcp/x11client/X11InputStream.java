package com.github.moaxcp.x11client;

import java.io.*;
import java.nio.charset.StandardCharsets;

class X11InputStream implements X11Input {
  private final DataInputStream in;

  public X11InputStream(InputStream inputStream) {
    in = new DataInputStream(inputStream);
  }

  @Override
  public int readByte() throws IOException {
    return in.readUnsignedByte();
  }

  @Override
  public int readInt8() throws IOException {
    return in.readByte();
  }

  @Override
  public int readInt16() throws IOException {
    return in.readShort();
  }

  @Override
  public int readInt32() throws IOException {
    return in.readInt();
  }

  @Override
  public int readCard8() throws IOException {
    return in.readUnsignedByte();
  }

  @Override
  public int readCard16() throws IOException {
    return in.readUnsignedShort();
  }

  @Override
  public int readCard32() throws IOException {
    return in.readInt();
  }

  @Override
  public String readString8(int length) throws IOException {
    byte[] bytes = readBytes(length);
    return new String(bytes, StandardCharsets.US_ASCII);
  }

  @Override
  public byte[] readBytes(int length) throws IOException {
    byte[] bytes = new byte[length];
    int read = in.read(bytes);
    if(read != bytes.length) {
      throw new IllegalStateException("could not read all bytes for length: \"" + bytes.length + "\"");
    }
    return bytes;
  }

  @Override
  public void readPad(int forLength) throws IOException {
    readBytes((4 - forLength % 4) % 4);
  }

  public void close() throws IOException {
    in.close();
  }
}
