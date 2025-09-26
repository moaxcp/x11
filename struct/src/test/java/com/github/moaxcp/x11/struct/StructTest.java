package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static com.github.moaxcp.x11.struct.Builders.struct;
import static org.assertj.core.api.Assertions.assertThat;

public class StructTest {

  @Test
  void AllTypes() {
    var struct = struct()
        .int8()
        .int8Array(0)
        .uint8()
        .uint8Array(2)
        .int16()
        .int16Array(4)
        .uint16()
        .uint16Array(6)
        .int32()
        .int32Array(8)
        .uint32()
        .uint32Array(10)
        .int64()
        .int64Array(12)
        .uint64()
        .uint64Array(14)
        .float32()
        .float32Array(16)
        .float64()
        .float64Array(18)
        .fromBytes(new byte[] {2, -20, -21,
            2, 20, 21,
            0, 2, -1, -20, -1, -21,
            0, 2, 0, 20, 0, 21,
            0, 0, 0, 2, -1, -1, -1, -20, -1, -1, -1, -21,
            0, 0, 0, 2, 0, 0, 0, 20, 0, 0, 0, 21,
            0, 0, 0, 0, 0, 0, 0, 2, -1, -1, -1 ,-1, -1, -1, -1, -20, -1, -1, -1, -1, -1, -1, -1, -21,
            0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 20, 0, 0, 0, 0, 0, 0, 0, 21,
            64, 0, 0, 0, 64, 64, 0, 0, 64, -128, 0, 0,
            64, 0, 0, 0, 0, 0, 0, 0, 64, 8, 0, 0, 0, 0, 0, 0, 64, 16, 0, 0, 0, 0, 0, 0
        })
        .build();

    assertThat(struct.getInt8(0)).isEqualTo((byte) 2);
    assertThat(struct.getInt8(1, 0)).isEqualTo((byte) -20);
    assertThat(struct.getInt8(1, 1)).isEqualTo((byte) -21);
    assertThat(struct.getUint8(2)).isEqualTo((byte) 2);
    assertThat(struct.getUint8(3, 0)).isEqualTo((byte) 20);
    assertThat(struct.getUint8(3, 1)).isEqualTo((byte) 21);
    assertThat(struct.getInt16(4)).isEqualTo((short) 2);
    assertThat(struct.getInt16(5, 0)).isEqualTo((short) -20);
    assertThat(struct.getInt16(5, 1)).isEqualTo((short) -21);
    assertThat(struct.getUint16(6)).isEqualTo(2);
    assertThat(struct.getUint16(7, 0)).isEqualTo(20);
    assertThat(struct.getUint16(7, 1)).isEqualTo(21);
    assertThat(struct.getInt32(8)).isEqualTo(2);
    assertThat(struct.getInt32(9, 0)).isEqualTo(-20);
    assertThat(struct.getInt32(9, 1)).isEqualTo(-21);
    assertThat(struct.getUint32(10)).isEqualTo(2);
    assertThat(struct.getUint32(11, 0)).isEqualTo(20);
    assertThat(struct.getUint32(11, 1)).isEqualTo(21);
    assertThat(struct.getInt64(12)).isEqualTo(2);
    assertThat(struct.getInt64(13, 0)).isEqualTo(-20);
    assertThat(struct.getInt64(13, 1)).isEqualTo(-21);
    assertThat(struct.getUint64(14)).isEqualTo(BigInteger.valueOf(2));
    assertThat(struct.getUint64(15, 0)).isEqualTo(BigInteger.valueOf(20));
    assertThat(struct.getUint64(15, 1)).isEqualTo(BigInteger.valueOf(21));
    assertThat(struct.getFloat32(16)).isEqualTo(2);
    assertThat(struct.getFloat32(17, 0)).isEqualTo(3);
    assertThat(struct.getFloat32(17, 1)).isEqualTo(4);
    assertThat(struct.getFloat64(18)).isEqualTo(2);
    assertThat(struct.getFloat64(19, 0)).isEqualTo(3);
    assertThat(struct.getFloat64(19, 1)).isEqualTo(4);
  }

  @Test
  void withStruct() {
    var struct = struct()
        .int8()
        .int16()
        .int8Array(1)
        .struct()
            .int16()
            .int16Array(0)
            .end()
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
