package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class X11OutputStream implements X11Output {
  private final DataOutputStream out;

  public X11OutputStream(OutputStream outputStream) {
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
  public void writeInt32(List<Integer> int32) throws IOException {
    for(int i : int32) {
      writeInt32(i);
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
  public void writeCard8(List<Byte> card8) throws IOException {
    for(byte i : card8) {
      writeCard8(i);
    }
  }

  @Override
  public void writeCard16(short card16) throws IOException {
    out.writeShort(card16);
  }

  @Override
  public void writeCard16(List<Short> card16) throws IOException {
    for(short i : card16) {
      writeCard16(i);
    }
  }

  @Override
  public void writeCard32(int card32) throws IOException {
    out.writeInt(card32);
  }

  @Override
  public void writeCard32(List<Integer> card32) throws IOException {
    for(int i : card32) {
      writeCard32(i);
    }
  }

  @Override
  public void writeCard64(long card64) throws IOException {
    out.writeLong(card64);
  }

  @Override
  public void writeChar(List<Byte> string) throws IOException {
    writeByte(string);
  }

  @Override
  public void writeString8(String string8) throws IOException {
    writeString8(string8.getBytes());
  }

  @Override
  public void writeByte(List<Byte> bytes) throws IOException {
    for(byte i : bytes) {
      writeByte(i);
    }
  }

  @Override
  public void writeVoid(List<Byte> bytes) throws IOException {
    writeByte(bytes);
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
