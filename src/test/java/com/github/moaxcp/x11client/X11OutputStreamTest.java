package com.github.moaxcp.x11client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class X11OutputStreamTest {
  private ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
  private X11OutputStream out = new X11OutputStream(byteOut);
  @Test
  void writeByte() throws IOException {
    out.writeByte(Byte.MAX_VALUE);
    assertThat(byteOut.toByteArray())
        .hasSize(1)
        .containsExactly(Byte.MAX_VALUE);
  }

  @Test
  void writeInt8() throws IOException {
    out.writeInt8(Byte.MAX_VALUE);
    assertThat(byteOut.toByteArray())
        .hasSize(1)
        .containsExactly(Byte.MAX_VALUE);
  }

  @Test
  void writeInt16() throws IOException {
    out.writeInt16(Short.MAX_VALUE);
    assertThat(byteOut.toByteArray())
        .hasSize(2)
        .containsExactly(127, -1);
  }

  @Test
  void writeInt32() throws IOException {
    out.writeInt32(Integer.MAX_VALUE);
    assertThat(byteOut.toByteArray())
        .hasSize(4)
        .containsExactly(127, -1, -1, -1);
  }

  @Test
  void writeCard8() throws IOException {
    out.writeCard8(255);
    assertThat(byteOut.toByteArray())
        .hasSize(1)
        .containsExactly(255);
  }

  @Test
  void writeCard16() throws IOException {
    out.writeCard16((short) -1);
    assertThat(byteOut.toByteArray())
        .hasSize(2)
        .containsExactly(255, 255);
  }

  @Test
  void writeCard32() throws IOException {
    out.writeCard32(-1);
    assertThat(byteOut.toByteArray())
        .hasSize(4)
        .containsExactly(255, 255, 255, 255);
  }

  @Test
  void writeString8() throws IOException {
    out.writeString8("Hello");
    assertThat(byteOut.toByteArray())
        .hasSize(5)
        .containsExactly('H', 'e', 'l', 'l', 'o');
  }

  @Test
  void writeString8Bytes() throws IOException {
    out.writeString8(new byte[] {'H', 'e', 'l', 'l', 'o'});
    assertThat(byteOut.toByteArray())
        .hasSize(5)
        .containsExactly('H', 'e', 'l', 'l', 'o');
  }
}
