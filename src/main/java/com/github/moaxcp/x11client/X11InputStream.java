package com.github.moaxcp.x11client;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class X11InputStream {
  private final DataInputStream in;

  public X11InputStream(InputStream inputStream) {
    in = new DataInputStream(inputStream);
  }

  public int readByte() throws IOException {
    return in.readUnsignedByte();
  }

  public int readInt8() throws IOException {
    return in.readByte();
  }

  public int readInt16() throws IOException {
    return in.readShort();
  }

  public int readInt32() throws IOException {
    return in.readInt();
  }

  public int readCard8() throws IOException {
    return in.readUnsignedByte();
  }

  public int readCard16() throws IOException {
    return in.readUnsignedShort();
  }

  public int readCard32() throws IOException {
    return in.readInt();
  }

  public String readString8(int length) throws IOException {
    byte[] bytes = readBytes(length);
    return new String(bytes, StandardCharsets.US_ASCII);
  }

  public byte[] readBytes(int length) throws IOException {
    byte[] bytes = new byte[length];
    int read = in.read(bytes);
    if(read != bytes.length) {
      throw new IllegalStateException("could not read all bytes for length: \"" + bytes.length + "\"");
    }
    return bytes;
  }

  public void readPad(int forLength) throws IOException {
    readBytes((4 - forLength % 4) % 4);
  }

  public void close() throws IOException {
    in.close();
  }
}
