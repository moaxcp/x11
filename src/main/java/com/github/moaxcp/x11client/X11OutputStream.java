package com.github.moaxcp.x11client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class X11OutputStream {
  private final DataOutputStream out;
  X11OutputStream(DataOutputStream out) {
    this.out = out;
  }

  public void writeByte(int b) throws IOException {
    out.write(b);
  }

  public void writeCard16(int card16) throws IOException {
    out.writeShort(card16);
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
