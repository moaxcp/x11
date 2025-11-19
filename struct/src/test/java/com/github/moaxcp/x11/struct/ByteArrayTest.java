package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11.struct.ByteArray.ba;
import static com.github.moaxcp.x11.struct.ShiftBytes.shiftBytes;
import static org.assertj.core.api.Assertions.assertThat;

public class ByteArrayTest {
  @Test
  void constructor() {
    var bytes = new ByteArray();
    assertThat(bytes.getBytes()).hasSize(0);
    assertThat(bytes.getAllocated()).isEqualTo(0);
  }

  @Test
  void constructorSize() {
    var bytes = new ByteArray(10);
    assertThat(bytes.getBytes()).hasSize(10);
    assertThat(bytes.getAllocated()).isEqualTo(0);
  }

  @Test
  void constructorBytes() {
    var bytes = new ByteArray(new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
    assertThat(bytes.getAllocated()).isEqualTo(10);
  }

  @Test
  void copy() {
    var bytes = new ByteArray(new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
    var copy = bytes.copy();
    assertThat(copy.getBytes()).isEqualTo(new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
    assertThat(bytes.getAllocated()).isEqualTo(10);
    assertThat(copy).isNotSameAs(bytes);
  }

  @Test
  void addListener() {
    var listener = new ByteArrayListener() {
      @Override
      public void shift(ShiftBytes shift) {}
    };

    var bytes = ba().addListener(listener);

    assertThat(bytes.getListeners()).containsExactly(listener);
  }

  @Test
  void removeListener() {
    var listener = new ByteArrayListener() {
      @Override
      public void shift(ShiftBytes shift) {}
    };

    var bytes = ba().addListener(listener).removeListener(listener);

    assertThat(bytes.getListeners()).doesNotContain(listener);
  }

  @Test
  void notifyListeners() {
    var listener = new ByteArrayListener() {
      ShiftBytes event;
      @Override
      public void shift(ShiftBytes shift) {event = shift;}
    };

    var bytes = ba().addListener(listener);
    bytes.addInt8(0, 20);

    assertThat(listener.event).isEqualTo(shiftBytes(0, 1));
  }

  @Test
  void setBytes() {
    var bytes = new ByteArray(10);
    var source = new ByteArray(10);
    for (int i = 0; i < 10; i++) {
      source.setInt8(i, (byte) i);
    }
    bytes.setBytes(source, 0, 0, 10);
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
    assertThat(bytes.getAllocated()).isEqualTo(10);
  }

  @Test
  void setBytesSource0Offset0() {
    var bytes = new ByteArray(10);
    var source = new ByteArray(2);
    source.setInt8(0, (byte) 1);
    source.setInt8(1, (byte) 1);
    bytes.setBytes(source, 0, 0, 2);
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {1, 1, 0, 0, 0, 0, 0, 0, 0, 0});
    assertThat(bytes.getAllocated()).isEqualTo(2);
  }

  @Test
  void setBytesSource1offset4() {
    var bytes = new ByteArray(10);
    var source = new ByteArray(4);
    source.setInt8(1, (byte) 1);
    source.setInt8(2, (byte) 1);
    bytes.setBytes(source, 1, 4, 2);
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, 0, 0, 0, 1, 1, 0, 0, 0, 0});
    assertThat(bytes.getAllocated()).isEqualTo(6);
  }

  @Test
  void setBytesSource1offset8() {
    var bytes = new ByteArray(10);
    var source = new ByteArray(4);
    source.setInt8(1, (byte) 1);
    source.setInt8(2, (byte) 1);
    bytes.setBytes(source, 1, 8, 2);
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, 0, 0, 0, 0, 0, 0, 0, 1, 1});
    assertThat(bytes.getAllocated()).isEqualTo(10);
  }

  @Test
  void setBytesSource1offset15Resize() {
    var bytes = new ByteArray(10);
    var source = new ByteArray(4);
    source.setInt8(1, (byte) 1);
    source.setInt8(2, (byte) 1);
    bytes.setBytes(source, 1, 15, 2);
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1});
    assertThat(bytes.getAllocated()).isEqualTo(17);
  }

  @Test
  void setBytesOversized() {
    var bytes = new ByteArray(3);
    var source = new ByteArray(10);
    for (int i = 0; i < 10; i++) {
      source.setInt8(i, (byte) i);
    }
    bytes.setBytes(source, 0, 0, 10);
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
    assertThat(bytes.getAllocated()).isEqualTo(10);
  }

  @Test
  void compareBytes() {
    var bytes1 = new ByteArray(new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
    var bytes2 = new ByteArray(new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
    assertThat(bytes1.compareBytes(0, bytes2, 0, 10)).isTrue();
  }
}
