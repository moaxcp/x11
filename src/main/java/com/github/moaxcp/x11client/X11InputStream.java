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
  public boolean readBool() throws IOException {
    return in.readByte() > 0;
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
  public int[] readInt32(int length) throws IOException {
    int[] result = new int[length];
    for(int i = 0; i < length; i++) {
      result[i] = readInt32();
    }
    return result;
  }

  @Override
  public long readInt64() throws IOException {
    return in.readLong();
  }

  @Override
  public byte readCard8() throws IOException {
    return (byte) in.readUnsignedByte();
  }

  @Override
  public byte[] readCard8(int length) throws IOException {
    byte[] result = new byte[length];
    for(int i = 0; i < length; i++) {
      result[i] = readCard8();
    }
    return result;
  }

  @Override
  public short readCard16() throws IOException {
    return (short) in.readUnsignedShort();
  }

  @Override
  public short[] readCard16(int length) throws IOException {
    short[] result = new short[length];
    for(int i = 0; i < length; i++) {
      result[i] = readCard16();
    }
    return result;
  }

  @Override
  public int readCard32() throws IOException {
    return in.readInt();
  }

  @Override
  public int[] readCard32(int length) throws IOException {
    int[] result = new int[length];
    for(int i = 0; i < length; i++) {
      result[i] = readCard32();
    }
    return result;
  }

  @Override
  public long readCard64() throws IOException {
    return 0;
  }

  @Override
  public byte[] readChar(int length) throws IOException {
    byte[] result = new byte[length];
    for(int i = 0; i < length; i++) {
      result[i] = readByte();
    }
    return result;
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
  public byte[] readVoid(int length) throws IOException {
    return readByte(length);
  }

  public void close() throws IOException {
    in.close();
  }

  public void mark(int readLimit) {
    in.mark(readLimit);
  }

  public void reset() throws IOException {
    in.reset();
  }
}
