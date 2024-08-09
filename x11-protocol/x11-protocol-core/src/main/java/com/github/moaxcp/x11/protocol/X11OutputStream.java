package com.github.moaxcp.x11.protocol;

import org.eclipse.collections.api.list.primitive.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class X11OutputStream implements X11Output, AutoCloseable {
  private final DataOutputStream out;

  public X11OutputStream(OutputStream outputStream) {
    out = new DataOutputStream(outputStream);
  }

  @Override
  public void writeBool(boolean bool) throws IOException {
    out.writeByte(bool ? 1 : 0);
  }

  @Override
  public void writeBool(ImmutableBooleanList bool) throws IOException {
    for (int i = 0; i < bool.size(); i++) {
      writeBool(bool.get(i));
    }
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
  public void writeInt32(ImmutableIntList int32) throws IOException {
    for (int i = 0; i < int32.size(); i++) {
      writeInt32(int32.get(i));
    }
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
  public void writeCard8(ImmutableByteList card8) throws IOException {
    for (int i = 0; i < card8.size(); i++) {
      writeCard8(card8.get(i));
    }
  }

  @Override
  public void writeCard16(short card16) throws IOException {
    out.writeShort(card16);
  }

  @Override
  public void writeCard16(ImmutableShortList card16) throws IOException {
    for (int i = 0; i < card16.size(); i++) {
      writeCard16(card16.get(i));
    }
  }

  @Override
  public void writeCard32(int card32) throws IOException {
    out.writeInt(card32);
  }

  @Override
  public void writeCard32(ImmutableIntList card32) throws IOException {
    for (int i = 0; i < card32.size(); i++) {
      writeCard32(card32.get(i));
    }
  }

  @Override
  public void writeCard64(long card64) throws IOException {
    out.writeLong(card64);
  }

  @Override
  public void writeCard64(ImmutableLongList card64) throws IOException {
    for (int i = 0; i < card64.size(); i++) {
      writeCard64(card64.get(i));
    }
  }

  @Override
  public void writeChar(ImmutableByteList string) throws IOException {
    writeByte(string);
  }

  @Override
  public void writeString8(String string8) throws IOException {
    writeString8(string8.getBytes());
  }

  @Override
  public void writeByte(ImmutableByteList bytes) throws IOException {
    for (int i = 0; i < bytes.size(); i++) {
      writeByte(bytes.get(i));
    }
  }

  @Override
  public void writeVoid(ImmutableByteList bytes) throws IOException {
    writeByte(bytes);
  }

  @Override
  public void writeFd(int fd) throws IOException {
    writeInt32(fd);
  }

  @Override
  public void writeFd(ImmutableIntList fd) throws IOException {
    writeInt32(fd);
  }

  @Override
  public void writeFloat(float f) throws IOException {
    out.writeFloat(f);
  }

  @Override
  public void writeFloat(ImmutableFloatList f) throws IOException {
    for (int i = 0; i < f.size(); i++) {
      writeFloat(f.get(i));
    }
  }

  @Override
  public void writeDouble(double d) throws IOException {
    out.writeDouble(d);
  }

  @Override
  public void writeDouble(ImmutableDoubleList d) throws IOException {
    for (int i = 0; i < d.size(); i++) {
      writeDouble(d.get(i));
    }
  }

  public void writeString8(byte[] string8) throws IOException {
    out.write(string8);
  }

  public void flush() throws IOException {
    out.flush();
  }

  public void close() throws IOException {
    out.close();
  }
}
