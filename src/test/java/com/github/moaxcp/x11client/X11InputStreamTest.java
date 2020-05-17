package com.github.moaxcp.x11client;

import java.io.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class X11InputStreamTest {
  @Captor
  ArgumentCaptor<byte[]> captor;

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

  @Test
  void readBytes() throws IOException {
    X11InputStream in = makeInputStream(1, 2, 3, 4);
    byte[] bytes = in.readBytes(4);
    assertThat(bytes).containsExactly(1, 2, 3, 4);
  }

  @Test
  void readBytes_wrongLength() throws IOException {
    InputStream origin = mock(InputStream.class);
    X11InputStream in = new X11InputStream(origin);
    when(origin.read(any(), eq(0), eq(4))).thenReturn(2);
    IllegalStateException exception = assertThrows(IllegalStateException.class, () -> in.readBytes(4));
    assertThat(exception).hasMessage("could not read all bytes for length: \"4\"");
  }

  @Test
  void readPad() throws IOException {
    InputStream origin = mock(InputStream.class);
    X11InputStream in = new X11InputStream(origin);
    when(origin.read(any(), eq(0), eq(3))).thenReturn(3);
    in.readPad(9);
    then(origin).should().read(captor.capture(), eq(0), eq(3));
    assertThat(captor.getValue()).hasSize(3);
  }

  @Test
  void close() throws IOException {
    InputStream origin = mock(InputStream.class);
    X11InputStream in = new X11InputStream(origin);
    in.close();
    then(origin).should().close();
  }

  private X11InputStream makeInputStream(int... values) {
    byte[] bytes = new byte[values.length];
    for(int i = 0; i < values.length; i++) {
      bytes[i] = (byte) values[i];
    }
    return new X11InputStream(new ByteArrayInputStream(bytes));
  }
}
