package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.github.moaxcp.x11.struct.ByteArray.ba;
import static com.github.moaxcp.x11.struct.ShiftBytes.shiftBytes;
import static org.assertj.core.api.Assertions.assertThat;

public class ByteArrayBooleanTest {
  private List<ShiftBytes> events = new ArrayList<>();

  @Test
  void getBool() {
    var bytes = ba().bool(true).addListener(events::add);
    assertThat(bytes.getBool(0)).isTrue();
    assertThat(events).isEmpty();
  }

  @Test
  void getBoolArray() {
    var bytes = ba().bool(true, false, true, false, true).addListener(events::add);
    assertThat(bytes.getBool(0, 5)).containsExactly(true, false, true, false, true);
    assertThat(events).isEmpty();
  }

  @Test
  void setBool() {
    var bytes = ba().allocate(1).addListener(events::add);
    bytes.setBool(0, true);

    assertThat(bytes).isEqualTo(ba().bool(true));
    assertThat(events).isEmpty();
  }

  @Test
  void setBoolArray() {
    var bytes = ba().allocate(5).addListener(events::add);
    bytes.setBool(0, true, false, true, false, true);

    assertThat(bytes).isEqualTo(ba().bool(true, false, true, false, true));
    assertThat(events).isEmpty();
  }

  @Test
  void bool_overwrite() {
    var bytes = ba().bool(true, true, true, true, true).addListener(events::add);
    bytes.setBool(0, true, false, true, false, true);

    assertThat(bytes).isEqualTo(ba().bool(true, false, true, false, true));
    assertThat(events).isEmpty();
  }

  @Test
  void addBool() {
    var bytes = ba(10).int8(100).addListener(events::add);
      bytes.addBool(0, true);
    assertThat(bytes).isEqualTo(ba().bool(true).int8(100));
    assertThat(events).containsExactly(shiftBytes(0, 1));
  }

  @Test
  void addBoolArray() {
    var bytes = ba(10).int8(100).addListener(events::add);
    bytes.addBool(0, true, false, true, false, true);
    assertThat(bytes).isEqualTo(ba().bool(true, false, true, false, true).int8(100));
    assertThat(events).containsExactly(shiftBytes(0, 5));
  }

  @Test
  void removeBool() {
    var bytes = ba().bool(true).addListener(events::add);
    bytes.removeBool(0);
    assertThat(bytes).isEqualTo(ba());
    assertThat(events).containsExactly(shiftBytes(0, -1));
  }

  @Test
  void removeBoolArray() {
    var bytes = ba().bool(true, false, true, false, true).addListener(events::add);
    bytes.removeBool(0, 5);
    assertThat(bytes).isEqualTo(ba());
    assertThat(events).containsExactly(shiftBytes(0, -5));
  }
}
