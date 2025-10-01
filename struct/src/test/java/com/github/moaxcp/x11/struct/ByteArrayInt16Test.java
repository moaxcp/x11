package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.github.moaxcp.x11.struct.ShiftBytes.shiftBytes;
import static org.assertj.core.api.Assertions.assertThat;

public class ByteArrayInt16Test {

  @Test
  void int16_overwrite() {
    var bytes = new ByteArray(new byte[] {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
    for (int i = 0; i < 10; i++) {
      bytes.int16(i * 2, (short) -i);
    }
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, 0, -1, -1, -1, -2, -1, -3, -1, -4, -1, -5, -1, -6, -1, -7, -1, -8, -1, -9});
    for (int i = 0; i < 10; i++) {
      assertThat(bytes.int16(i * 2)).isEqualTo((short) -i);
    }
  }

  @Test
  void addInt16() {
    var bytes = new ByteArray(new byte[] {100});
    var events = new ArrayList<ShiftBytes>();
    bytes.addListener(events::add);
    for (int i = 0; i < 10; i++) {
      bytes.addInt16(0, (short) i);
    }
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, 9, 0, 8, 0, 7, 0, 6, 0, 5, 0, 4, 0, 3, 0, 2, 0, 1, 0, 0, 100});
    assertThat(events).containsExactly(shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2));
  }

  @Test
  void removeInt16Events() {
    var bytes = new ByteArray(new byte[] {0, 9, 0, 8, 0, 7, 0, 6, 0, 5, 0, 4, 0, 3, 0, 2, 0, 1, 0, 0, 100});
    var events = new ArrayList<ShiftBytes>();
    bytes.addListener(events::add);
    for (int i = 0; i < 10; i++) {
      bytes.removeInt16(0);
    }
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {100});
    assertThat(events).containsExactly(shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2));
  }
}
