package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.X11Input;

import java.io.*;
import java.nio.charset.StandardCharsets;

class X11InputStream implements X11Input {
  private final DataInputStream in;

  public X11InputStream(InputStream inputStream) {
    in = new DataInputStream(inputStream);
  }

  @Override
  public byte readByte() throws IOException {
    return (byte) in.readUnsignedByte();
  }

  @Override
  public byte readInt8() throws IOException {
    return (byte) in.readByte();
  }

  @Override
  public short readInt16() throws IOException {
    return (short) in.readShort();
  }

  @Override
  public int readInt32() throws IOException {
    return in.readInt();
  }

  @Override
  public byte readCard8() throws IOException {
    return (byte) in.readUnsignedByte();
  }

  @Override
  public short readCard16() throws IOException {
    return (short) in.readUnsignedShort();
  }

  @Override
  public int readCard32() throws IOException {
    return in.readInt();
  }

  @Override
  public String readString8(int length) throws IOException {
    byte[] bytes = readByte(length);
    return new String(bytes, StandardCharsets.US_ASCII);
  }

  @Override
  public byte[] readByte(int length) throws IOException {
    byte[] bytes = new byte[length];
    int read = in.read(bytes);
    if(read != bytes.length) {
      throw new IllegalStateException("could not read all bytes for length: \"" + bytes.length + "\"");
    }
    return bytes;
  }

  @Override
  public void readPad(int forLength) throws IOException {
    readByte((4 - forLength % 4) % 4);
  }

  public void close() throws IOException {
    in.close();
  }
}
