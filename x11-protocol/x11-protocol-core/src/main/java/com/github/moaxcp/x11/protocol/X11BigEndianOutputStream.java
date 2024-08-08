package com.github.moaxcp.x11.protocol;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class X11BigEndianOutputStream implements X11OutputStream {
  private final DataOutputStream out;

  public X11BigEndianOutputStream(OutputStream outputStream) {
    out = new DataOutputStream(outputStream);
  }

  @Override
  public void writeBool(boolean bool) throws IOException {
    out.writeByte(bool ? 1 : 0);
  }

  @Override
  public void writeByte(byte b) throws IOException {
    out.write(b);
  }

  @Override
  public void writeInt8(byte int8) throws IOException {
    out.writeByte(int8);
  }

  @Override
  public void writeInt16(short int16) throws IOException {
    out.writeShort(int16);
  }

  @Override
  public void writeInt32(int int32) throws IOException {
    out.writeInt(int32);
  }

  @Override
  public void writeInt64(long int64) throws IOException {
    out.writeLong(int64);
  }

  @Override
  public void writeCard8(byte card8) throws IOException {
    out.writeByte(card8);
  }

  @Override
  public void writeCard16(short card16) throws IOException {
    out.writeShort(card16);
  }

  @Override
  public void writeCard32(int card32) throws IOException {
    out.writeInt(card32);
  }

  @Override
  public void writeCard64(long card64) throws IOException {
    out.writeLong(card64);
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
    out.writeFloat(f);
  }

  @Override
  public void writeDouble(double d) throws IOException {
    out.writeDouble(d);
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
