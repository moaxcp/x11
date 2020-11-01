package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.X11Input;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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
  public List<Integer> readInt32(int length) throws IOException {
    return readList(length, this::readInt32);
  }

  private interface IOSupplier<T> {
    T get() throws IOException;
  }

  private <T> List<T> readList(int length, IOSupplier<T> supplier) throws IOException {
    List<T> result = new ArrayList<>(length);
    for(int i = 0; i < length; i++) {
      result.add(supplier.get());
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
  public List<Byte> readCard8(int length) throws IOException {
    return readList(length, this::readCard8);
  }

  @Override
  public short readCard16() throws IOException {
    return (short) in.readUnsignedShort();
  }

  @Override
  public List<Short> readCard16(int length) throws IOException {
    return readList(length, this::readCard16);
  }

  @Override
  public int readCard32() throws IOException {
    return in.readInt();
  }

  @Override
  public List<Integer> readCard32(int length) throws IOException {
    return readList(length, this::readCard32);
  }

  @Override
  public long readCard64() throws IOException {
    return 0;
  }

  @Override
  public List<Byte> readChar(int length) throws IOException {
    return readList(length, this::readByte);
  }

  @Override
  public String readString8(int length) throws IOException {
    byte[] bytes = new byte[length];
    int read = in.read(bytes);
    if(read != bytes.length) {
      throw new IllegalStateException("could not read all bytes for length: \"" + bytes.length + "\"");
    }
    return new String(bytes, StandardCharsets.US_ASCII);
  }

  @Override
  public List<Byte> readByte(int length) throws IOException {
    byte[] bytes = new byte[length];
    int read = in.read(bytes);
    if(read != bytes.length) {
      throw new IllegalStateException("could not read all bytes for length: \"" + bytes.length + "\"");
    }
    List<Byte> result = new ArrayList<>(length);
    for(byte b : bytes) {
      result.add(b);
    }
    return result;
  }

  @Override
  public List<Byte> readVoid(int length) throws IOException {
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
