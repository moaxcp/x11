package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11.struct.Int16Type.int16Type;
import static com.github.moaxcp.x11.struct.Size.INT16;
import static com.github.moaxcp.x11.struct.Builders.struct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Int16TypeTest {

  @Test
  void constructor() {
    var type = int16Type();
    assertThat(type.getPosition()).isEqualTo(-1);
  }

  @Test
  void constructorPosition() {
    var type = int16Type(15);
    assertThat(type.getPosition()).isEqualTo(15);
  }

  @Test
  void copy() {
    var type = int16Type();
    var copy = type.copy(15);
    assertThat(copy.getPosition()).isEqualTo(15);
  }

  @Test
  void getByteLength() {
    var struct = struct()
        .int16()
        .build();
    assertThat(struct.getType(0).getByteLength(struct)).isEqualTo(INT16.size());
  }

  @Test
  void setShort() {
    var struct = struct()
        .int16()
        .build();

    struct.setInt16(0, (short) 2);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 2});
  }

  @Test
  void getShort() {
    var struct = struct()
        .int16()
        .build();

    struct.setInt16(0, (short) 2);

    assertThat(struct.getInt16(0)).isEqualTo((short) 2);
  }

  @Test
  void getShortNotSet() {
    var struct = struct()
        .int16()
        .build();

    assertThat(struct.getInt16(0)).isEqualTo((short) 0);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 0});
  }

  @Test
  void getShortNotAllocated() {
    var struct = struct()
        .allocated()
        .int16()
        .build();

    assertThatThrownBy(() -> struct.getInt16(0)).isInstanceOf(ArrayIndexOutOfBoundsException.class);
  }

  @Test
  void getShortIndexed() {
    var struct = struct()
        .int16()
        .int16Array(0)
        .build();

    struct.addInt16(1, (short) 1);
    struct.addInt16(1, (short) 2);

    assertThat(struct.getInt16(1, 0)).isEqualTo((short) 1);
    assertThat(struct.getInt16(1, 1)).isEqualTo((short) 2);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 2, 0, 1, 0, 2} );
  }

  @Test
  void removeShortIndexed() {
    var struct = struct()
        .int16()
        .int16Array(0)
        .build();

    struct.addInt16(1, (short) 1);
    struct.addInt16(1, (short) 2);
    struct.removeInt16(1, 0);

    assertThat(((Int16Type) struct.getType(1)).getInt16(struct, 0)).isEqualTo((short) 2);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 1, 0, 2} );
  }
}
