package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ByteArrayTest {
  @Test
  void constructor() {
    var bytes = new ByteArray();
    assertThat(bytes.getBytes()).hasSize(0);
  }

  @Test
  void constructorSize() {
    var bytes = new ByteArray(10);
    assertThat(bytes.getBytes()).hasSize(10);
  }

  @Test
  void constructorBytes() {
    var bytes = new ByteArray(new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
  }

  @Test
  void copy() {
    var bytes = new ByteArray(new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
    var copy = bytes.copy();
    assertThat(copy.getBytes()).isEqualTo(new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
    assertThat(copy).isNotSameAs(bytes);
  }

  @Test
  void setBytes() {
    var bytes = new ByteArray(10);
    var source = new ByteArray(10);
    for (int i = 0; i < 10; i++) {
      source.int8(i, (byte) i);
    }
    bytes.setBytes(source, 0, 0, 10);
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
  }

  @Test
  void setBytesSource0Offset0() {
    var bytes = new ByteArray(10);
    var source = new ByteArray(2);
    source.int8(0, (byte) 1);
    source.int8(1, (byte) 1);
    bytes.setBytes(source, 0, 0, 2);
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {1, 1, 0, 0, 0, 0, 0, 0, 0, 0});
  }

  @Test
  void setBytesSource1ffset4() {
    var bytes = new ByteArray(10);
    var source = new ByteArray(4);
    source.int8(1, (byte) 1);
    source.int8(2, (byte) 1);
    bytes.setBytes(source, 1, 4, 2);
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, 0, 0, 0, 1, 1, 0, 0, 0, 0});
  }

  @Test
  void setBytesSource1offset8() {
    var bytes = new ByteArray(10);
    var source = new ByteArray(4);
    source.int8(1, (byte) 1);
    source.int8(2, (byte) 1);
    bytes.setBytes(source, 1, 8, 2);
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, 0, 0, 0, 0, 0, 0, 0, 1, 1});
  }

  @Test
  void setBytesSource1offset15Resize() {
    var bytes = new ByteArray(10);
    var source = new ByteArray(4);
    source.int8(1, (byte) 1);
    source.int8(2, (byte) 1);
    bytes.setBytes(source, 1, 15, 2);
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1});
  }

  @Test
  void setBytesOversized() {
    var bytes = new ByteArray(3);
    var source = new ByteArray(10);
    for (int i = 0; i < 10; i++) {
      source.int8(i, (byte) i);
    }
    bytes.setBytes(source, 0, 0, 10);
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
  }

  @Test
  void compareBytes() {
    var bytes1 = new ByteArray(new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
    var bytes2 = new ByteArray(new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
    assertThat(bytes1.compareBytes(0, bytes2, 0, 10)).isTrue();
  }
}
