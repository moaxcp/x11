package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.github.moaxcp.x11.struct.ShiftBytes.shiftBytes;
import static org.assertj.core.api.Assertions.assertThat;

public class ByteArrayUInt16Test {

  @Test
  void uint16_overwrite() {
    var bytes = new ByteArray(new byte[] {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
    for (int i = 0; i < 10; i++) {
      bytes.setUint16(i * 2, 65535 - i);
    }
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {-1, -1, -1, -2, -1, -3, -1, -4, -1, -5, -1, -6, -1, -7, -1, -8, -1, -9, -1, -10});
    for (int i = 0; i < 10; i++) {
      assertThat(bytes.getUint16(i * 2)).isEqualTo(65535 - i);
    }
  }

  @Test
  void addUint16() {
    var bytes = new ByteArray(new byte[] {100});
    var events = new ArrayList<ShiftBytes>();
    bytes.addListener(events::add);
    for (int i = 0; i < 10; i++) {
      bytes.addUint16(0, i);
    }
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, 9, 0, 8, 0, 7, 0, 6, 0, 5, 0, 4, 0, 3, 0, 2, 0, 1, 0, 0, 100});
    assertThat(events).containsExactly(shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2));
  }

  @Test
  void removeUint16Events() {
    var bytes = new ByteArray(new byte[] {0, 9, 0, 8, 0, 7, 0, 6, 0, 5, 0, 4, 0, 3, 0, 2, 0, 1, 0, 0, 100});
    var events = new ArrayList<ShiftBytes>();
    bytes.addListener(events::add);
    for (int i = 0; i < 10; i++) {
      bytes.removeUint16(0);
    }
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {100});
    assertThat(events).containsExactly(shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2));
  }
}
