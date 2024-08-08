package com.github.moaxcp.x11.protocol;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.github.moaxcp.x11.protocol.LittleEndian.*;

public class X11LittleEndianInputStream implements X11InputStream {
  private final InputStream in;

  private final byte[] buffer = new byte[8];

  public X11LittleEndianInputStream(InputStream inputStream) {
    in = inputStream;
  }

  @Override
  public boolean readBool() throws IOException {
    return readByte() > 0;
  }

  @Override
  public byte readByte() throws IOException {
    var read = in.read();
    if (read == -1) {
      throw new EOFException();
    }
    return (byte) read;
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
    in.readNBytes(buffer, 0, 2);
    return toShort(buffer);
  }

  @Override
  public int readInt32() throws IOException {
    in.readNBytes(buffer, 0, 4);
    return toInt(buffer);
  }

  @Override
  public long readInt64() throws IOException {
    in.readNBytes(buffer, 0, 8);
    return toLong(buffer);
  }

  @Override
  public byte readCard8() throws IOException {
    return readByte();
  }

  @Override
  public short readCard16() throws IOException {
    return readInt16();
  }

  @Override
  public int readCard32() throws IOException {
    return readInt32();
  }

  @Override
  public long readCard64() throws IOException {
    return readInt64();
  }

  @Override
  public float readFloat() throws IOException {
    in.readNBytes(buffer, 0, 4);
    return toFloat(buffer);
  }

  @Override
  public double readDouble() throws IOException {
    in.readNBytes(buffer, 0, 8);
    return toDouble(buffer);
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
