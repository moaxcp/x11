package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.github.moaxcp.x11.struct.ByteArray.ba;
import static com.github.moaxcp.x11.struct.ShiftBytes.shiftBytes;
import static org.assertj.core.api.Assertions.assertThat;

public class ByteArrayInt16Test {

  @Test
  void int16_overwrite() {
    var bytes = new ByteArray(new byte[] {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
    for (int i = 0; i < 10; i++) {
      bytes.setInt16(i * 2, (short) -i);
    }
    assertThat(bytes).isEqualTo(ba().int16(0, -1, -2, -3, -4, -5, -6, -7, -8, -9));
    for (int i = 0; i < 10; i++) {
      assertThat(bytes.getInt16(i * 2)).isEqualTo((short) -i);
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
    assertThat(bytes).isEqualTo(ba().int16(9, 8, 7, 6, 5, 4, 3, 2, 1, 0).int8(100));
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
    assertThat(bytes).isEqualTo(ba().int8(100));
    assertThat(events).containsExactly(shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2));
  }
}
