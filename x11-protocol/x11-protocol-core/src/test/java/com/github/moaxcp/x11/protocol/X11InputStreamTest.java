package com.github.moaxcp.x11.protocol;

import org.eclipse.collections.impl.factory.primitive.ByteLists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
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
    X11BigEndianInputStream in = makeInputStream(255);
    int value = in.readByte();
    assertThat(value).isEqualTo(-1);
  }

  @Test
  void readInt8() throws IOException {
    X11BigEndianInputStream in = makeInputStream(255);
    int value = in.readInt8();
    assertThat(value).isEqualTo(-1);
  }

  @Test
  void readInt16() throws IOException {
    X11BigEndianInputStream in = makeInputStream(127, -1);
    int value = in.readInt16();
    assertThat(value).isEqualTo(Short.MAX_VALUE);
  }

  @Test
  void readInt32() throws IOException {
    X11BigEndianInputStream in = makeInputStream(127, -1, -1, -1);
    int value = in.readInt32();
    assertThat(value).isEqualTo(Integer.MAX_VALUE);
  }

  @Test
  void readCard8() throws IOException {
    X11BigEndianInputStream in = makeInputStream(255);
    int value = in.readCard8();
    assertThat(value).isEqualTo(-1);
  }

  @Test
  void readCard16() throws IOException {
    X11BigEndianInputStream in = makeInputStream(255, 255);
    int value = in.readCard16();
    assertThat(value).isEqualTo(-1);
  }

  @Test
  void readCard32() throws IOException {
    X11BigEndianInputStream in = makeInputStream(255, 255, 255, 255);
    int value = in.readCard32();
    assertThat(value).isEqualTo(-1);
  }

  @Test
  void readString8() throws IOException {
    X11BigEndianInputStream in = makeInputStream('H', 'e', 'l', 'l', 'o');
    String value = in.readString8(5);
    assertThat(value).isEqualTo("Hello");
  }

  @Test
  void readBytes() throws IOException {
    X11BigEndianInputStream in = makeInputStream(1, 2, 3, 4);
    var bytes = in.readByte(4);
    assertThat(bytes).isEqualTo(ByteLists.immutable.of((byte) 1, (byte) 2, (byte) 3, (byte) 4));
  }

  @Test
  void readPadAlign() throws IOException {
    InputStream origin = mock(InputStream.class);
    X11BigEndianInputStream in = new X11BigEndianInputStream(origin);
    when(origin.read(any(), eq(0), eq(3))).thenReturn(3);
    in.readPadAlign(9);
    then(origin).should().read(captor.capture(), eq(0), eq(3));
    assertThat(captor.getValue()).hasSize(3);
  }

  @Test
  void close() throws IOException {
    InputStream origin = mock(InputStream.class);
    X11BigEndianInputStream in = new X11BigEndianInputStream(origin);
    in.close();
    then(origin).should().close();
  }

  private X11BigEndianInputStream makeInputStream(int... values) {
    byte[] bytes = new byte[values.length];
    for(int i = 0; i < values.length; i++) {
      bytes[i] = (byte) values[i];
    }
    return new X11BigEndianInputStream(new ByteArrayInputStream(bytes));
  }
}
