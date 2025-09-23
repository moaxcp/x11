package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11.struct.Int8Type.int8;
import static com.github.moaxcp.x11.struct.StructBuilder.struct;
import static com.github.moaxcp.x11.struct.Size.INT8;
import static com.github.moaxcp.x11.struct.StructTypeBuilder.structType;
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
        .structType(structType()
            .int8()
            .build())
        .build();
    assertThat(struct.getType(0).getByteLength(struct)).isEqualTo(INT8.size());
  }

  @Test
  void setByte() {
    var struct = struct()
        .structType(structType()
            .int8()
            .build())
        .build();

    struct.setInt8(0, (byte) 2);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {2});
  }

  @Test
  void getByte() {
    var struct = struct()
        .structType(structType()
            .int8()
            .build())
        .build();
    struct.setInt8(0, (byte) 2);

    assertThat(struct.getInt8(0)).isEqualTo((byte) 2);
  }

  @Test
  void getByteNotSet() {
    var struct = struct()
        .structType(structType()
            .int8()
            .build())
        .build();

    assertThat(struct.getInt8(0)).isEqualTo((byte) 0);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0});
  }

  @Test
  void getByteNotAllocated() {
    var struct = struct()
        .allocated()
        .structType(structType()
            .int8()
            .build())
        .build();

    assertThatThrownBy(() -> struct.getInt8(0)).isInstanceOf(ArrayIndexOutOfBoundsException.class);
  }

  @Test
  void getByteIndexed() {
    var struct = struct()
        .structType(structType()
            .int8()
            .int8Array(0)
            .build())
        .build();

    struct.addInt8(1, (byte) 1);
    struct.addInt8(1, (byte) 2);

    assertThat(struct.getInt8(1, 0)).isEqualTo((byte) 1);
    assertThat(struct.getInt8(1, 1)).isEqualTo((byte) 2);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {2, 1, 2} );
  }

  @Test
  void removeByteIndexed() {
    var struct = struct()
        .structType(structType()
            .int8()
            .int8Array(0)
            .build())
        .build();

    struct.addInt8(1, (byte) 1);
    struct.addInt8(1, (byte) 2);
    struct.removeInt8(1, 0);

    assertThat(((NumberType) struct.getType(1)).get(struct, 0)).isEqualTo((byte) 2);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {1, 2} );
  }
}
