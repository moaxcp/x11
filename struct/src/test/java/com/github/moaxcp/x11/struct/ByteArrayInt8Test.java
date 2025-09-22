package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.github.moaxcp.x11.struct.ShiftBytes.shiftBytes;
import static org.assertj.core.api.Assertions.assertThat;

public class ByteArrayInt8Test {

  @Test
  void int8_expand_array() {
    var bytes = new ByteArray();
    for (int i = 0; i < 10; i++) {
      bytes.int8(i, (byte) -i);
    }
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, -1, -2, -3, -4, -5, -6, -7, -8, -9});
    for (int i = 0; i < 10; i++) {
      assertThat(bytes.int8(i)).isEqualTo((byte) -i);
    }
  }

  @Test
  void int8_overwrite() {
    var bytes = new ByteArray(new byte[] {5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
    for (int i = 0; i < 10; i++) {
      bytes.int8(i, (byte) -i);
    }
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, -1, -2, -3, -4, -5, -6, -7, -8, -9});
    for (int i = 0; i < 10; i++) {
      assertThat(bytes.int8(i)).isEqualTo((byte) -i);
    }
  }

  @Test
  void addInt8() {
    var bytes = new ByteArray(new byte[] {100});
    var events = new ArrayList<ShiftBytes>();
    bytes.addListener(events::add);
    for (int i = 0; i < 10; i++) {
      bytes.addInt8(0, (byte) i);
    }
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 100});
    assertThat(events).containsExactly(shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1));
  }

  @Test
  void removeInt8Events() {
    var bytes = new ByteArray(new byte[] {9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 100});
    var events = new ArrayList<ShiftBytes>();
    bytes.addListener(events::add);
    for (int i = 0; i < 10; i++) {
      bytes.removeInt8(0);
    }
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {100});
    assertThat(events).containsExactly(shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1));
  }
}
