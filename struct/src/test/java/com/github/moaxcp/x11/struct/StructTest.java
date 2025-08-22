package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11.struct.StructBuilder.struct;
import static com.github.moaxcp.x11.struct.StructTypeBuilder.structType;
import static org.assertj.core.api.Assertions.assertThat;

public class StructTest {

  @Test
  void complexTypes() {
    var struct = struct()
        .byteType()
        .shortType()
        .byteType()
        .shortArray(2)
        .fromBytes(new byte[] {88, 12, 34, 3, 0, 1, 0, 2, 0, 3})
        .build();

    assertThat(struct.getByte(0)).isEqualTo(88);
    assertThat(struct.getShort(1)).isEqualTo(3106);
    assertThat(struct.getByte(2)).isEqualTo(3);
    assertThat(struct.getShort(3, 0)).isEqualTo(1);
    assertThat(struct.getShort(3, 1)).isEqualTo(2);
    assertThat(struct.getShort(3, 2)).isEqualTo(3);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {88, 12, 34, 3, 0, 1, 0, 2, 0, 3});
  }

  @Test
  void withStruct() {
    var struct = struct()
        .structType(structType()
            .build())
        .build();
  }
}
