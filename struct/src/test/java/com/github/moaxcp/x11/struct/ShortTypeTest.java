package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11.struct.NumberType.Size.SHORT;
import static com.github.moaxcp.x11.struct.NumberType.shortType;
import static com.github.moaxcp.x11.struct.StructBuilder.struct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ShortTypeTest {

  @Test
  void constructor() {
    var type = shortType();
    assertThat(type.getPosition()).isEqualTo(-1);
  }

  @Test
  void constructorPosition() {
    var type = shortType(15);
    assertThat(type.getPosition()).isEqualTo(15);
  }

  @Test
  void copy() {
    var type = shortType();
    var copy = type.copy(15);
    assertThat(copy.getPosition()).isEqualTo(15);
  }

  @Test
  void getByteLength() {
    var struct = struct()
        .shortType()
        .build();
    assertThat(struct.getType(0).getByteLength(struct)).isEqualTo(SHORT.size());
  }

  @Test
  void setShort() {
    var struct = struct()
        .shortType()
        .build();

    struct.setShort(0, 2L);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 2});
  }

  @Test
  void getShort() {
    var struct = struct()
        .shortType()
        .build();
    struct.setShort(0, 2L);

    assertThat(struct.getShort(0)).isEqualTo(2L);
  }

  @Test
  void getShortNotSet() {
    var struct = struct()
        .shortType()
        .build();

    assertThat(struct.getShort(0)).isEqualTo(0L);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 0});
  }

  @Test
  void getShortNotAllocated() {
    var struct = struct()
        .allocated()
        .shortType()
        .build();

    assertThatThrownBy(() -> struct.getShort(0)).isInstanceOf(ArrayIndexOutOfBoundsException.class);
  }

  @Test
  void getShortIndexed() {
    var struct = struct()
        .shortType()
        .shortArray(0)
        .build();

    struct.addShort(1, 1);
    struct.addShort(1, 2);

    assertThat(struct.getShort(1, 0)).isEqualTo(1L);
    assertThat(struct.getShort(1, 1)).isEqualTo(2L);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 2, 0, 1, 0, 2} );
  }

  @Test
  void removeShortIndexed() {
    var struct = struct()
        .shortType()
        .shortArray(0)
        .build();

    struct.addShort(1, 1);
    struct.addShort(1, 2);
    struct.removeShort(1, 0);

    assertThat(((NumberType) struct.getType(1)).get(struct, 0)).isEqualTo(2L);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 1, 0, 2} );
  }
}
