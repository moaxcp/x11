package com.github.moaxcp.x11client;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class X11OutputStream {
  private final DataOutputStream out;

  public X11OutputStream(OutputStream outputStream) {
    out = new DataOutputStream(outputStream);
  }

  public void writeByte(int b) throws IOException {
    out.write(b);
  }

  public void writeInt8(int int8) throws IOException {
    writeByte(int8);
  }

  public void writeInt16(int int16) throws IOException {
    out.writeShort(int16);
  }

  public void writeInt32(int int32) throws IOException {
    out.writeInt(int32);
  }

  public void writeCard8(int card8) throws IOException {
    out.writeByte(card8);
  }

  public void writeCard16(int card16) throws IOException {
    out.writeShort(card16);
  }

  public void writeCard32(int card32) throws IOException {
    out.writeInt(card32);
  }

  public void writeString8(String string8) throws IOException {
    writeString8(string8.getBytes(StandardCharsets.US_ASCII));
  }

  public void writeString8(byte[] string8) throws IOException {
    out.write(string8);
  }

  public void writePad(int forLength) throws IOException {
    out.write(new byte[(4 - forLength % 4) % 4]);
  }

  public void flush() throws IOException {
    out.flush();
  }

  public void close() throws IOException {
    out.close();
  }
}
