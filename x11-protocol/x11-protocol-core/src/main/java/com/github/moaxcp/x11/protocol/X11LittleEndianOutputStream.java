package com.github.moaxcp.x11.protocol;

import java.io.IOException;
import java.io.OutputStream;

import static com.github.moaxcp.x11.protocol.LittleEndian.write;

public class X11LittleEndianOutputStream implements X11OutputStream {
  private final OutputStream out;

  private final byte[] buffer = new byte[8];

  public X11LittleEndianOutputStream(OutputStream outputStream) {
    out = outputStream;
  }

  @Override
  public void writeBool(boolean bool) throws IOException {
    out.write(bool ? 1 : 0);
  }

  @Override
  public void writeByte(byte b) throws IOException {
    out.write(b);
  }

  @Override
  public void writeInt8(byte int8) throws IOException {
    out.write(int8);
  }

  @Override
  public void writeInt16(short int16) throws IOException {
    write(int16, buffer);
    out.write(buffer, 0, 2);
  }

  @Override
  public void writeInt32(int int32) throws IOException {
    LittleEndian.write(int32, buffer);
    out.write(buffer, 0, 4);
  }

  @Override
  public void writeInt64(long int64) throws IOException {
    LittleEndian.write(int64, buffer);
    out.write(buffer, 0, 8);
  }

  @Override
  public void writeCard8(byte card8) throws IOException {
    out.write(card8);
  }

  @Override
  public void writeCard16(short card16) throws IOException {
    writeInt16(card16);
  }

  @Override
  public void writeCard32(int card32) throws IOException {
    writeInt32(card32);
  }

  @Override
  public void writeCard64(long card64) throws IOException {
    writeInt64(card64);
  }

  @Override
  public void writeString8(String string8) throws IOException {
    writeString8(string8.getBytes());
  }

  @Override
  public void writeFd(int fd) throws IOException {
    writeInt32(fd);
  }

  @Override
  public void writeFloat(float f) throws IOException {
    write(f, buffer);
    out.write(buffer, 0, 4);
  }

  @Override
  public void writeDouble(double d) throws IOException {
    write(d, buffer);
    out.write(buffer, 0, 8);
  }

  public void writeString8(byte[] string8) throws IOException {
    out.write(string8);
  }

  @Override
  public void flush() throws IOException {
    out.flush();
  }

  @Override
  public void close() throws IOException {
    out.close();
  }
}
