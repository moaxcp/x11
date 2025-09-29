package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11.struct.Int8Type.int8;
import static com.github.moaxcp.x11.struct.Size.INT8;
import static com.github.moaxcp.x11.struct.Builders.struct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Int8TypeTest {

  @Test
  void constructor() {
    var type = Int8Type.int8();
    assertThat(type.getPosition()).isEqualTo(-1);
  }

  @Test
  void constructorPosition() {
    var type = int8(15);
    assertThat(type.getPosition()).isEqualTo(15);
  }

  @Test
  void copy() {
    var type = Int8Type.int8();
    var copy = type.copy(15);
    assertThat(copy.getPosition()).isEqualTo(15);
  }

  @Test
  void getByteLength() {
    var struct = struct()
        .int8()
        .build();

    assertThat(struct.getType(0).getByteLength(struct)).isEqualTo(INT8.size());
  }

  @Test
  void setByte() {
    var struct = struct()
        .int8()
        .build();

    struct.setInt8(0, (byte) 2);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {2});
  }

  @Test
  void setByte_position_negative() {
    var struct = struct()
        .int8()
        .build();

    assertThatThrownBy(() -> struct.setInt8(-1, (byte) 2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 1");
  }

  @Test
  void setByte_position_greater_than_length() {
    var struct = struct()
        .int8()
        .build();

    assertThatThrownBy(() -> struct.setInt8(2, (byte) 2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 1");
  }

  @Test
  void getByte() {
    var struct = struct()
        .int8()
        .build();

    struct.setInt8(0, (byte) 2);

    assertThat(struct.getInt8(0)).isEqualTo((byte) 2);
  }

  @Test
  void getByte_position_negative() {
    var struct = struct()
        .int8()
        .build();

    assertThatThrownBy(() -> struct.setInt8(-1, (byte) 2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 1");
  }

  @Test
  void getByte_position_greater_than_length() {
    var struct = struct()
        .int8()
        .build();

    assertThatThrownBy(() -> struct.getInt8(2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 1");
  }

  @Test
  void getByteNotSet() {
    var struct = struct()
        .int8()
        .build();

    assertThat(struct.getInt8(0)).isEqualTo((byte) 0);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0});
  }

  @Test
  void getByteNotAllocated() {
    var struct = struct()
        .allocated()
        .int8()
        .build();

    assertThatThrownBy(() -> struct.getInt8(0)).isInstanceOf(ArrayIndexOutOfBoundsException.class);
  }

  @Test
  void addByteWithArray() {
    var struct = struct()
        .int8()
        .int8Array(0)
        .build();

    struct.addInt8(1, (byte) 1);
    struct.addInt8(1, (byte) 2);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {2, 1, 2} );
  }

  @Test
  void getByteIndexed() {
    var struct = struct()
        .int8()
        .int8Array(0)
        .fromBytes(new byte[] {2, 1, 2})
        .build();

    assertThat(struct.getInt8(1, 0)).isEqualTo((byte) 1);
    assertThat(struct.getInt8(1, 1)).isEqualTo((byte) 2);
  }

  @Test
  void removeByteIndexed() {
    var struct = struct()
        .int8()
        .int8Array(0)
        .build();

    struct.addInt8(1, (byte) 1);
    struct.addInt8(1, (byte) 2);
    struct.removeInt8(1, 0);

    assertThat(((Int8Type) struct.getType(1)).getInt8(struct, 0)).isEqualTo((byte) 2);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {1, 2} );
  }
}
