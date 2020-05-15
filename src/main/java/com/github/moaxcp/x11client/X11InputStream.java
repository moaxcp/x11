package com.github.moaxcp.x11client;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class X11InputStream {
  private final DataInputStream in;
  public X11InputStream(DataInputStream in) {
    this.in = in;
  }

  public int readInt8() throws IOException {
    return in.readUnsignedByte();
  }

  public int readCard16() throws IOException {
    return in.readUnsignedShort();
  }

  public String readString8(int length) throws IOException {
    byte[] bytes = readBytes(new byte[length]);
    return new String(bytes, StandardCharsets.US_ASCII);
  }

  public byte[] readBytes(byte[] bytes) throws IOException {
    int read = in.read(bytes);
    if(read != bytes.length) {
      throw new IllegalStateException("could not read all bytes for length: \"" + bytes.length + "\"");
    }
    return bytes;
  }

  public void readPad(int forLength) throws IOException {
    byte[] pad = readBytes(new byte[(4 - forLength % 4) % 4]);
  }

  public void close() throws IOException {
    in.close();
  }

}
