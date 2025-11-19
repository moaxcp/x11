package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.github.moaxcp.x11.struct.ByteArray.ba;
import static com.github.moaxcp.x11.struct.ShiftBytes.shiftBytes;
import static org.assertj.core.api.Assertions.assertThat;

public class ByteArrayInt64Test {

  @Test
  void int64_overwrite() {
    var bytes = new ByteArray(new byte[] {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
    for (int i = 0; i < 10; i++) {
      bytes.setInt64(i * 8, -i);
    }
    assertThat(bytes).isEqualTo(ba().int64(0, -1, -2, -3, -4, -5, -6, -7, -8, -9));
    for (int i = 0; i < 10; i++) {
      assertThat(bytes.getInt64(i * 8)).isEqualTo(-i);
    }
  }

  @Test
  void addInt32() {
    var bytes = new ByteArray(new byte[] {100});
    var events = new ArrayList<ShiftBytes>();
    bytes.addListener(events::add);
    for (int i = 0; i < 10; i++) {
      bytes.addInt64(0, i);
    }
    assertThat(bytes).isEqualTo(ba().int64(9, 8, 7, 6, 5, 4, 3, 2, 1, 0).int8(100));
    assertThat(events).containsExactly(shiftBytes(0, 8), shiftBytes(0, 8), shiftBytes(0, 8), shiftBytes(0, 8), shiftBytes(0, 8), shiftBytes(0, 8), shiftBytes(0, 8), shiftBytes(0, 8), shiftBytes(0, 8), shiftBytes(0, 8));
  }

  @Test
  void removeInt32Events() {
    var bytes = new ByteArray(new byte[] {
        0, 0, 0, 0, 0, 0, 0, 9,
        0, 0, 0, 0, 0, 0, 0, 8,
        0, 0, 0, 0, 0, 0, 0, 7,
        0, 0, 0, 0, 0, 0, 0, 6,
        0, 0, 0, 0, 0, 0, 0, 5,
        0, 0, 0, 0, 0, 0, 0, 4,
        0, 0, 0, 0, 0, 0, 0, 3,
        0, 0, 0, 0, 0, 0, 0, 2,
        0, 0, 0, 0, 0, 0, 0, 1,
        0, 0, 0, 0, 0, 0, 0, 0,
        100
    });
    var events = new ArrayList<ShiftBytes>();
    bytes.addListener(events::add);
    for (int i = 0; i < 10; i++) {
      bytes.removeInt64(0);
    }
    assertThat(bytes).isEqualTo(ba().int8(100));
    assertThat(events).containsExactly(shiftBytes(0, -8), shiftBytes(0, -8), shiftBytes(0, -8), shiftBytes(0, -8), shiftBytes(0, -8), shiftBytes(0, -8), shiftBytes(0, -8), shiftBytes(0, -8), shiftBytes(0, -8), shiftBytes(0, -8));
  }
}
