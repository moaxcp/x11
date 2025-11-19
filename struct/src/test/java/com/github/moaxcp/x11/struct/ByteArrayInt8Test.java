package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.github.moaxcp.x11.struct.ByteArray.ba;
import static com.github.moaxcp.x11.struct.ShiftBytes.shiftBytes;
import static org.assertj.core.api.Assertions.assertThat;

public class ByteArrayInt8Test {
  private List<ShiftBytes> events = new ArrayList<>();

  @Test
  void getInt8() {
    var bytes = ba().int8(100).addListener(events::add);
    assertThat(bytes.getInt8(0)).isEqualTo((byte) 100);
    assertThat(events).isEmpty();
  }

  @Test
  void getInt8Array() {
    var bytes = ba().int8(1, 2, 3, 4, 5).addListener(events::add);
    assertThat(bytes.getInt8(0, 5)).containsExactly((byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5);
    assertThat(events).isEmpty();
  }

  @Test
  void setInt8() {
    var bytes = ba().allocate(1).addListener(events::add);
    bytes.setInt8(0, 100);

    assertThat(bytes).isEqualTo(ba().int8(100));
    assertThat(events).isEmpty();
  }

  @Test
  void setInt8Array() {
    var bytes = ba().allocate(5).addListener(events::add);
    bytes.setInt8(0, 1, 2, 3, 4, 5);

    assertThat(bytes).isEqualTo(ba().int8(1, 2, 3, 4, 5));
    assertThat(events).isEmpty();
  }

  @Test
  void int8_overwrite() {
    var bytes = ba().int8(5, 5, 5, 5, 5, 5, 5, 5, 5, 5).addListener(events::add);
    bytes.setInt8(0, 0, -1, -2, -3, -4, -5, -6, -7, -8, -9);

    assertThat(bytes).isEqualTo(ba().int8(0, -1, -2, -3, -4, -5, -6, -7, -8, -9));
    assertThat(events).isEmpty();
  }

  @Test
  void addInt8() {
    var bytes = ba(10).int8(100).addListener(events::add);
    bytes.addInt8(0, 1);
    assertThat(bytes).isEqualTo(ba().int8(1).int8(100));
    assertThat(events).containsExactly(shiftBytes(0, 1));
  }

  @Test
  void addInt8Array() {
    var bytes = ba(10).int8(100).addListener(events::add);
    bytes.addInt8(0, 1, 2, 3, 4, 5);
    assertThat(bytes).isEqualTo(ba().int8(1, 2, 3, 4, 5).int8(100));
    assertThat(events).containsExactly(shiftBytes(0, 5));
  }

  @Test
  void removeInt8() {
    var bytes = ba().int8(1).addListener(events::add);
    bytes.removeInt8(0);
    assertThat(bytes).isEqualTo(ba());
    assertThat(events).containsExactly(shiftBytes(0, -1));
  }

  @Test
  void removeInt8Array() {
    var bytes = ba().int8(1, 2, 3, 4, 5).addListener(events::add);
    bytes.removeInt8(0, 5);
    assertThat(bytes).isEqualTo(ba());
    assertThat(events).containsExactly(shiftBytes(0, -5));
  }
}
