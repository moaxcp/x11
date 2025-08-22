package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11.struct.StructBuilder.struct;
import static com.github.moaxcp.x11.struct.NumberType.Size.BYTE;
import static com.github.moaxcp.x11.struct.NumberType.byteType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ByteTypeTest {

  @Test
  void constructor() {
    var type = byteType();
    assertThat(type.getPosition()).isEqualTo(-1);
  }

  @Test
  void constructorPosition() {
    var type = byteType(15);
    assertThat(type.getPosition()).isEqualTo(15);
  }

  @Test
  void copy() {
    var type = byteType();
    var copy = type.copy(15);
    assertThat(copy.getPosition()).isEqualTo(15);
  }

  @Test
  void getByteLength() {
    var struct = struct()
        .byteType()
        .build();
    assertThat(struct.getType(0).getByteLength(struct)).isEqualTo(BYTE.size());
  }

  @Test
  void setByte() {
    var struct = struct()
        .byteType()
        .build();

    struct.setByte(0, 2L);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {2});
  }

  @Test
  void getByte() {
    var struct = struct()
        .byteType()
        .build();
    struct.setByte(0, 2L);

    assertThat(struct.getByte(0)).isEqualTo(2L);
  }

  @Test
  void getByteNotSet() {
    var struct = struct()
        .byteType()
        .build();

    assertThat(struct.getByte(0)).isEqualTo(0L);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0});
  }

  @Test
  void getByteNotAllocated() {
    var struct = struct()
        .allocated()
        .byteType()
        .build();

    assertThatThrownBy(() -> struct.getByte(0)).isInstanceOf(ArrayIndexOutOfBoundsException.class);
  }

  @Test
  void getByteIndexed() {
    var struct = struct()
        .byteType()
        .byteArray(0)
        .build();

    struct.addByte(1, 1);
    struct.addByte(1, 2);

    assertThat(struct.getByte(1, 0)).isEqualTo(1L);
    assertThat(struct.getByte(1, 1)).isEqualTo(2L);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {2, 1, 2} );
  }

  @Test
  void removeByteIndexed() {
    var struct = struct()
        .byteType()
        .byteArray(0)
        .build();

    struct.addByte(1, 1);
    struct.addByte(1, 2);
    struct.removeByte(1, 0);

    assertThat(((NumberType) struct.getType(1)).get(struct, 0)).isEqualTo(2L);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {1, 2} );
  }
}
