package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.github.moaxcp.x11.struct.ShiftBytes.shiftBytes;
import static org.assertj.core.api.Assertions.assertThat;

public class ByteArrayUint8Test {

  @Test
  void uint8_overwrite() {
    var bytes = new ByteArray(new byte[] {5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
    for (int i = 0; i < 10; i++) {
      bytes.setUint8(i, (byte) (255 - i));
    }
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {-1, -2, -3, -4, -5, -6, -7, -8, -9, -10});
    for (int i = 0; i < 10; i++) {
      assertThat(bytes.getUint8(i)).isEqualTo((short) (255 - i));
    }
  }

  @Test
  void addUint8() {
    var bytes = new ByteArray(new byte[] {100});
    var events = new ArrayList<ShiftBytes>();
    bytes.addListener(events::add);
    for (int i = 0; i < 10; i++) {
      bytes.addUint8(0, (short) i);
    }
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 100});
    assertThat(events).containsExactly(shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1));
  }

  @Test
  void removeUint8Events() {
    var bytes = new ByteArray(new byte[] {9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 100});
    var events = new ArrayList<ShiftBytes>();
    bytes.addListener(events::add);
    for (int i = 0; i < 10; i++) {
      bytes.removeUint8(0);
    }
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {100});
    assertThat(events).containsExactly(shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1));
  }
}
