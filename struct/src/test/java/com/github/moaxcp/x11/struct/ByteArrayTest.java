package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.github.moaxcp.x11.struct.ByteArray.shift;
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
  void setByte() {
    var bytes = new ByteArray();
    for (int i = 0; i < 10; i++) {
      bytes.setByte(i, (byte) i);
    }
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
    for (int i = 0; i < 10; i++) {
      assertThat(bytes.getByte(i)).isEqualTo((byte) i);
    }
  }

  @Test
  void addByte() {
    var bytes = new ByteArray();
    var events = new ArrayList<ByteArray.ByteShift>();
    bytes.addListener(events::add);
    for (int i = 0; i < 10; i++) {
      bytes.addByte(0, (byte) i);
    }
    assertThat(events).containsExactly(shift(0, 1), shift(0, 1), shift(0, 1), shift(0, 1), shift(0, 1), shift(0, 1), shift(0, 1), shift(0, 1), shift(0, 1), shift(0, 1));
  }

  @Test
  void setShort() {
    var bytes = new ByteArray(0);
    for (int i = 0; i < 10; i++) {
      bytes.setShort(i * 2, (short) i);
    }
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 0, 6, 0, 7, 0, 8, 0, 9});
    for (int i = 0; i < 10; i++) {
      assertThat(bytes.getShort(i * 2)).isEqualTo((short) i);
    }
  }

  @Test
  void addShort() {
    var bytes = new ByteArray();
    var events = new ArrayList<ByteArray.ByteShift>();
    bytes.addListener(events::add);
    for (int i = 0; i < 10; i++) {
      bytes.addShort(0, (short) i);
    }
    assertThat(events).containsExactly(shift(0, 2), shift(0, 2), shift(0, 2), shift(0, 2), shift(0, 2), shift(0, 2), shift(0, 2), shift(0, 2), shift(0, 2), shift(0, 2));
  }

  @Test
  void setBytes() {
    var bytes = new ByteArray(10);
    var source = new ByteArray(10);
    for (int i = 0; i < 10; i++) {
      source.setByte(i, (byte) i);
    }
    bytes.setBytes(source, 0, 0, 10);
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
  }

  @Test
  void setBytesSource0Offset0() {
    var bytes = new ByteArray(10);
    var source = new ByteArray(2);
    source.setByte(0, (byte) 1);
    source.setByte(1, (byte) 1);
    bytes.setBytes(source, 0, 0, 2);
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {1, 1, 0, 0, 0, 0, 0, 0, 0, 0});
  }

  @Test
  void setBytesSource1ffset4() {
    var bytes = new ByteArray(10);
    var source = new ByteArray(4);
    source.setByte(1, (byte) 1);
    source.setByte(2, (byte) 1);
    bytes.setBytes(source, 1, 4, 2);
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, 0, 0, 0, 1, 1, 0, 0, 0, 0});
  }

  @Test
  void setBytesSource1offset8() {
    var bytes = new ByteArray(10);
    var source = new ByteArray(4);
    source.setByte(1, (byte) 1);
    source.setByte(2, (byte) 1);
    bytes.setBytes(source, 1, 8, 2);
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, 0, 0, 0, 0, 0, 0, 0, 1, 1});
  }

  @Test
  void setBytesSource1offset15Resize() {
    var bytes = new ByteArray(10);
    var source = new ByteArray(4);
    source.setByte(1, (byte) 1);
    source.setByte(2, (byte) 1);
    bytes.setBytes(source, 1, 15, 2);
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1});
  }

  @Test
  void setBytesOversized() {
    var bytes = new ByteArray(3);
    var source = new ByteArray(10);
    for (int i = 0; i < 10; i++) {
      source.setByte(i, (byte) i);
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
