package com.github.moaxcp.x11.protocol;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.github.moaxcp.x11.protocol.LittleEndian.*;

public class X11LittleEndianInputStream implements X11InputStream {
  private final InputStream in;

  private final byte[] buffer = new byte[8];

  private interface IOSupplier<T> {
    T get() throws IOException;
  }

  public X11LittleEndianInputStream(InputStream inputStream) {
    in = inputStream;
  }

  @Override
  public boolean readBool() throws IOException {
    return readByte() > 0;
  }

  @Override
  public List<Boolean> readBool(int length) throws IOException {
    return readList(length, this::readBool);
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
  public List<Integer> readInt32(int length) throws IOException {
    return readList(length, this::readInt32);
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
    in.readNBytes(buffer, 0, 8);
    return toLong(buffer);
  }

  @Override
  public byte readCard8() throws IOException {
    return readByte();
  }

  @Override
  public List<Byte> readCard8(int length) throws IOException {
    return readList(length, this::readCard8);
  }

  @Override
  public short readCard16() throws IOException {
    return readInt16();
  }

  @Override
  public List<Short> readCard16(int length) throws IOException {
    return readList(length, this::readCard16);
  }

  @Override
  public int readCard32() throws IOException {
    return readInt32();
  }

  @Override
  public List<Integer> readCard32(int length) throws IOException {
    return readList(length, this::readCard32);
  }

  @Override
  public long readCard64() throws IOException {
    return readInt64();
  }

  @Override
  public List<Long> readCard64(int length) throws IOException {
    return readList(length, this::readCard64);
  }

  @Override
  public float readFloat() throws IOException {
    in.readNBytes(buffer, 0, 4);
    return toFloat(buffer);
  }

  @Override
  public List<Float> readFloat(int length) throws IOException {
    return readList(length, this::readFloat);
  }

  @Override
  public double readDouble() throws IOException {
    in.readNBytes(buffer, 0, 8);
    return toDouble(buffer);
  }

  @Override
  public List<Double> readDouble(int length) throws IOException {
    return readList(length, this::readDouble);
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
    if(length == 0) {
      return new ArrayList<>();
    }
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

  @Override
  public int readFd() throws IOException {
    return readInt32();
  }

  @Override
  public List<Integer> readFd(int length) throws IOException {
    return readInt32(length);
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
