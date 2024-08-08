package com.github.moaxcp.x11.protocol;

import org.eclipse.collections.api.list.primitive.ByteList;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class X11BigEndianInputStream implements X11InputStream {
  private final DataInputStream in;

  public X11BigEndianInputStream(InputStream inputStream) {
    in = new DataInputStream(inputStream);
  }

  @Override
  public boolean readBool() throws IOException {
    return readByte() > 0;
  }

  @Override
  public byte readByte() throws IOException {
      return in.readByte();
  }

  @Override
  public byte peekByte() throws IOException {
    in.mark(1);
    byte read = readByte();
    in.reset();
    return read;
  }

  @Override
  public byte readInt8() throws IOException {
    return readByte();
  }

  @Override
  public short readInt16() throws IOException {
    return in.readShort();
  }

  @Override
  public int readInt32() throws IOException {
    return in.readInt();
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
  public short readCard16() throws IOException {
    return (short) in.readUnsignedShort();
  }

  @Override
  public int readCard32() throws IOException {
    return in.readInt();
  }

  @Override
  public long readCard64() throws IOException {
    return in.readLong();
  }

  @Override
  public float readFloat() throws IOException {
    return in.readFloat();
  }

  @Override
  public double readDouble() throws IOException {
    return in.readDouble();
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
  public ByteList readVoid(int length) throws IOException {
    return readByte(length);
  }

  @Override
  public int readFd() throws IOException {
    return readInt32();
  }

  @Override
  public byte[] readPad(int length) throws IOException {
    byte[] bytes = new byte[length];
    if(length == 0) {
      return bytes;
    }
    int read = in.read(bytes);
    int sum = read;
    while(read != -1 && sum < length) {
      read = in.read(bytes, sum, length - sum);
      sum += read;
    }
    if(read == -1) {
      throw new IOException("could not read " + length + " bytes for pad");
    }
    return bytes;
  }

  @Override
  public void peekWith(X11InputConsumer consumer) throws IOException {
    in.mark(Integer.MAX_VALUE);
    consumer.accept(this);
    in.reset();
  }

  @Override
  public int available() throws IOException {
    return in.available();
  }

  @Override
  public void close() throws IOException {
    in.close();
  }

  @Override
  public void mark(int readLimit) {
    in.mark(readLimit);
  }

  @Override
  public void reset() throws IOException {
    in.reset();
  }
}
