package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11.struct.StructBuilder.struct;
import static org.assertj.core.api.Assertions.assertThat;

public class StructTest {

  @Test
  void constructor() {
    var struct = new Struct();

    assertThat(struct).isEqualTo(new Struct());
  }

  @Test
  void setByte() {
    var struct = struct()
        .withByte(1)
        .withByte(2)
        .withByte(3)
        .withByte(4)
        .withByte(5)
        .withByte(6)
        .build();
    struct.setByte(5, (byte) 223);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {1, 2, 3, 4, 5, (byte) 223});
    assertThat(struct.getByte(5)).isEqualTo((byte) 223);
  }

  @Test
  void setShort() {
    var struct = struct()
        .withShort(1)
        .withShort(2)
        .withShort(3)
        .withShort(4)
        .withShort(5)
        .withShort(6)
        .build();
    struct.setShort(5, (short) 223);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 0, (byte) 223});
    assertThat(struct.getShort(5)).isEqualTo((short) 223);
  }
}
