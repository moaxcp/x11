package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.github.moaxcp.x11.struct.ByteArray.ba;
import static com.github.moaxcp.x11.struct.ShiftBytes.shiftBytes;
import static org.assertj.core.api.Assertions.assertThat;

public class ByteArrayFloat32Test {

  @Test
  void float32_overwrite() {
    var bytes = new ByteArray(new byte[] {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
    for (int i = 0; i < 10; i++) {
      bytes.setFloat32(i * 4, -i);
    }
    assertThat(bytes).isEqualTo(ba().float32(0, -1, -2, -3, -4, -5, -6, -7, -8, -9));
    for (int i = 0; i < 10; i++) {
      assertThat(bytes.getFloat32(i * 4)).isEqualTo(-i);
    }
  }

  @Test
  void addFloat32() {
    var bytes = new ByteArray(new byte[] {100});
    var events = new ArrayList<ShiftBytes>();
    bytes.addListener(events::add);
    for (int i = 0; i < 10; i++) {
      bytes.addFloat32(0, i);
    }
    assertThat(bytes).isEqualTo(ba().float32(9, 8, 7, 6, 5, 4, 3, 2, 1, 0).int8(100));
    assertThat(events).containsExactly(shiftBytes(0, 4), shiftBytes(0, 4), shiftBytes(0, 4), shiftBytes(0, 4), shiftBytes(0, 4), shiftBytes(0, 4), shiftBytes(0, 4), shiftBytes(0, 4), shiftBytes(0, 4), shiftBytes(0, 4));
  }

  @Test
  void removeFloat32Events() {
    var bytes = new ByteArray(new byte[] {65, 16, 0, 0, 65, 0, 0, 0, 64, -32, 0, 0, 64, -64, 0, 0, 64, -96, 0, 0, 64, -128, 0, 0, 64, 64, 0, 0, 64, 0, 0, 0, 63, -128, 0, 0, 0, 0, 0, 0, 100});
    var events = new ArrayList<ShiftBytes>();
    bytes.addListener(events::add);
    for (int i = 0; i < 10; i++) {
      bytes.removeFloat32(0);
    }
    assertThat(bytes).isEqualTo(ba().int8(100));
    assertThat(events).containsExactly(shiftBytes(0, -4), shiftBytes(0, -4), shiftBytes(0, -4), shiftBytes(0, -4), shiftBytes(0, -4), shiftBytes(0, -4), shiftBytes(0, -4), shiftBytes(0, -4), shiftBytes(0, -4), shiftBytes(0, -4));
  }
}
