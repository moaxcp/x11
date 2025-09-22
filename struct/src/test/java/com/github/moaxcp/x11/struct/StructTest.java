package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11.struct.StructBuilder.struct;
import static com.github.moaxcp.x11.struct.StructTypeBuilder.structType;
import static org.assertj.core.api.Assertions.assertThat;

public class StructTest {

  @Test
  void complexTypes() {
    var struct = struct()
        .structType(structType()
            .int8()
            .int16()
            .int8()
            .int16Array(2)
            .build())
        .fromBytes(new byte[] {88, 12, 34, 3, 0, 1, 0, 2, 0, 3})
        .build();

    assertThat(struct.getInt8(0)).isEqualTo((byte) 88);
    assertThat(struct.getInt16(1)).isEqualTo((short) 3106);
    assertThat(struct.getInt8(2)).isEqualTo((byte) 3);
    assertThat(struct.getInt16(3, 0)).isEqualTo((short) 1);
    assertThat(struct.getInt16(3, 1)).isEqualTo((short) 2);
    assertThat(struct.getInt16(3, 2)).isEqualTo((short) 3);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {88, 12, 34, 3, 0, 1, 0, 2, 0, 3});
  }

  @Test
  void withStruct() {
    var struct = struct()
        .structType(structType()
            .int8()
            .int16()
            .int8Array(1)
            .struct(structType()
                .int16()
                .int16Array(0)
                .build())
            .build())
        .fromBytes(new byte[] {1, 0, 2, 1, 2, 0, 3, 0, 1, 0, 2, 0, 3})
        .build();

    assertThat(struct.getInt8(0)).isEqualTo((byte) 1);
    assertThat(struct.getInt16(1)).isEqualTo((short) 2);
    assertThat(struct.getInt8(2, 0)).isEqualTo((byte) 1);
    assertThat(struct.getInt8(2, 1)).isEqualTo((byte) 2);
    var inner = struct.getStruct(3);
    assertThat(inner.getInt16(0)).isEqualTo((short) 3);
    assertThat(inner.getInt16(1, 0)).isEqualTo((short) 1);
    assertThat(inner.getInt16(1, 1)).isEqualTo((short) 2);
    assertThat(inner.getInt16(1, 2)).isEqualTo((short) 3);
  }
}
