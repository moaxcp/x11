package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11.struct.Builders.struct;
import static com.github.moaxcp.x11.struct.ByteArray.ba;
import static com.github.moaxcp.x11.struct.Expression.constant;
import static com.github.moaxcp.x11.struct.PadType.align;
import static com.github.moaxcp.x11.struct.PadType.pad;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PadTypeTest {

  @Test
  void constructor() {
    var type = pad(4);
    assertThat(type).isEqualTo(new PadType(-1, null, 4, false));
  }

  @Test
  void constructorPosition() {
    var type = pad(15, 4);
    assertThat(type).isEqualTo(new PadType(15, null, 4, false));
  }

  @Test
  void alignConstructor() {
    var type = align(4);
    assertThat(type).isEqualTo(new PadType(-1, null, 4, true));
  }

  @Test
  void alignPosition() {
    var type = align(15, 4);
    assertThat(type).isEqualTo(new PadType(15, null, 4, true));
  }

  @Test
  void copyPad() {
    var type = pad(15, 4);
    var copy = type.copy(15);
    assertThat(type).isEqualTo(copy);
  }

  @Test
  void copyAlign() {
    var type = align(15, 4);
    var copy = type.copy(15);
    assertThat(type).isEqualTo(copy);
  }

  @Test
  void getByteLength() {
    var struct = struct()
        .pad(4)
        .build();

    assertThat(struct.getByteLength()).isEqualTo(4);
    assertThat(struct.getType(0).getByteLength(struct)).isEqualTo(4);
  }

  @Test
  void isFixedLength() {
    var struct = struct()
        .pad(4)
        .build();

    assertThat(struct.isFixedLength()).isTrue();
  }

  @Test
  void isFixedLengthAlign() {
    var struct = struct()
        .int8()
        .align(4)
        .build();

    assertThat(struct.isFixedLength()).isTrue();
  }

  @Test
  void isFixedLengthAlignArray() {
    var struct = struct()
        .int8()
        .int8Array(0)
        .align(4)
        .build();

    assertThat(struct.isFixedLength()).isFalse();
  }

  @Test
  void isFixedLengthAlignArrayConstant() {
    var struct = struct()
        .int8Array(constant(8))
        .align(4)
        .build();

    assertThat(struct.isFixedLength()).isTrue();
  }

  @Test
  void isAllocated() {
    var struct = struct()
        .int64()
        .pad(4)
        .build();
    struct.setInt64(0, 11111111);

    assertThat(struct.getByteArray()).isEqualTo(ba().int64(11111111L).pad(4));
  }

  @Test
  void remove() {
    var struct = struct()
        .pad(8)
        .build();

    assertThatThrownBy(() -> struct.removeAll(0))
        .isInstanceOf(ClassCastException.class)
        .hasMessageContaining("PadType cannot be cast to class");
  }
}
