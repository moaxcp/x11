package com.github.moaxcp.x11client;

import java.io.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class X11InputStreamTest {
  @Test
  void readByte() throws IOException {
    X11InputStream in = makeInputStream(255);
    int value = in.readByte();
    assertThat(value).isEqualTo(255);
  }

  @Test
  void readInt8() throws IOException {
    X11InputStream in = makeInputStream(255);
    int value = in.readInt8();
    assertThat(value).isEqualTo(-1);
  }

  @Test
  void readInt16() throws IOException {
    X11InputStream in = makeInputStream(127, -1);
    int value = in.readInt16();
    assertThat(value).isEqualTo(Short.MAX_VALUE);
  }

  @Test
  void readInt32() throws IOException {
    X11InputStream in = makeInputStream(127, -1, -1, -1);
    int value = in.readInt32();
    assertThat(value).isEqualTo(Integer.MAX_VALUE);
  }

  @Test
  void readCard8() throws IOException {
    X11InputStream in = makeInputStream(255);
    int value = in.readCard8();
    assertThat(value).isEqualTo(255);
  }

  @Test
  void readCard16() throws IOException {
    X11InputStream in = makeInputStream(255, 255);
    int value = in.readCard16();
    assertThat(value).isEqualTo(65535);
  }

  @Test
  void readCard32() throws IOException {
    X11InputStream in = makeInputStream(255, 255, 255, 255);
    int value = in.readCard32();
    assertThat(value).isEqualTo(-1);
  }

  @Test
  void readString8() throws IOException {
    X11InputStream in = makeInputStream('H', 'e', 'l', 'l', 'o');
    String value = in.readString8(5);
    assertThat(value).isEqualTo("Hello");
  }

  private X11InputStream makeInputStream(int... values) {
    byte[] bytes = new byte[values.length];
    for(int i = 0; i < values.length; i++) {
      bytes[i] = (byte) values[i];
    }
    return new X11InputStream(new ByteArrayInputStream(bytes));
  }
}
