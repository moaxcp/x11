package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.github.moaxcp.x11.struct.ByteArray.ba;
import static com.github.moaxcp.x11.struct.ShiftBytes.shiftBytes;
import static org.assertj.core.api.Assertions.assertThat;

public class ByteArrayFloat64Test {

  @Test
  void float64_overwrite() {
    var bytes = new ByteArray(new byte[] {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
    for (int i = 0; i < 10; i++) {
      bytes.setFloat64(i * 8, -i);
    }
    assertThat(bytes).isEqualTo(ba().float64(0, -1, -2, -3, -4, -5, -6, -7, -8, -9));
    for (int i = 0; i < 10; i++) {
      assertThat(bytes.getFloat64(i * 8)).isEqualTo(-i);
    }
  }

  @Test
  void addFloat64() {
    var bytes = new ByteArray(new byte[] {100});
    var events = new ArrayList<ShiftBytes>();
    bytes.addListener(events::add);
    for (int i = 0; i < 10; i++) {
      bytes.addFloat64(0, i);
    }
    assertThat(bytes).isEqualTo(ba().float64(9, 8, 7, 6, 5, 4, 3, 2, 1, 0).int8(100));
    assertThat(events).containsExactly(shiftBytes(0, 8), shiftBytes(0, 8), shiftBytes(0, 8), shiftBytes(0, 8), shiftBytes(0, 8), shiftBytes(0, 8), shiftBytes(0, 8), shiftBytes(0, 8), shiftBytes(0, 8), shiftBytes(0, 8));
  }

  @Test
  void removeFloat64Events() {
    var bytes = new ByteArray(new byte[] {64, 34, 0, 0, 0, 0, 0, 0, 64, 32, 0, 0, 0, 0, 0, 0, 64, 28, 0, 0, 0, 0, 0, 0, 64, 24, 0, 0, 0, 0, 0, 0, 64, 20, 0, 0, 0, 0, 0, 0, 64, 16, 0, 0, 0, 0, 0, 0, 64, 8, 0, 0, 0, 0, 0, 0, 64, 0, 0, 0, 0, 0, 0, 0, 63, -16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100});
    var events = new ArrayList<ShiftBytes>();
    bytes.addListener(events::add);
    for (int i = 0; i < 10; i++) {
      bytes.removeFloat64(0);
    }
    assertThat(bytes).isEqualTo(ba().int8(100));
    assertThat(events).containsExactly(shiftBytes(0, -8), shiftBytes(0, -8), shiftBytes(0, -8), shiftBytes(0, -8), shiftBytes(0, -8), shiftBytes(0, -8), shiftBytes(0, -8), shiftBytes(0, -8), shiftBytes(0, -8), shiftBytes(0, -8));
  }
}
