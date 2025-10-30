package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.github.moaxcp.x11.struct.ShiftBytes.shiftBytes;
import static org.assertj.core.api.Assertions.assertThat;

public class ByteArrayBooleanTest {

  @Test
  void bool_overwrite() {
    var bytes = new ByteArray(new byte[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
    for (int i = 0; i < 10; i++) {
      bytes.setBool(i, i % 2 == 0);
    }
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {1, 0, 1, 0, 1, 0, 1, 0, 1, 0});
    for (int i = 0; i < 10; i++) {
      assertThat(bytes.getBool(i)).isEqualTo(i % 2 == 0);
    }
  }

  @Test
  void addBool() {
    var bytes = new ByteArray(new byte[] {100});
    var events = new ArrayList<ShiftBytes>();
    bytes.addListener(events::add);
    for (int i = 0; i < 10; i++) {
      bytes.addBool(0, i % 2 != 0);
    }
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 100});
    assertThat(events).containsExactly(shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1));
  }

  @Test
  void removeBoolEvents() {
    var bytes = new ByteArray(new byte[] {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 100});
    var events = new ArrayList<ShiftBytes>();
    bytes.addListener(events::add);
    for (int i = 0; i < 10; i++) {
      bytes.removeBool(0);
    }
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {100});
    assertThat(events).containsExactly(shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1));
  }
}
