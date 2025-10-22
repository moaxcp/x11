package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigInteger;
import java.util.stream.Stream;

import static com.github.moaxcp.x11.struct.Builders.struct;
import static com.github.moaxcp.x11.struct.ByteArray.ba;
import static com.github.moaxcp.x11.struct.Expression.constant;
import static com.github.moaxcp.x11.struct.PadType.align;
import static com.github.moaxcp.x11.struct.PadType.pad;
import static com.github.moaxcp.x11.struct.Primitive.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PadTypeTest {

  @Test
  void constructor() {
    var type = pad(4);
    assertThat(type).isEqualTo(new PadType(-1, 4, false));
  }

  @Test
  void constructorPosition() {
    var type = pad(15, 4);
    assertThat(type).isEqualTo(new PadType(15, 4, false));
  }

  @Test
  void alignConstructor() {
    var type = align(4);
    assertThat(type).isEqualTo(new PadType(-1, 4, true));
  }

  @Test
  void alignPosition() {
    var type = align(15, 4);
    assertThat(type).isEqualTo(new PadType(15, 4, true));
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
    assertThat(struct.getByteLength(0)).isEqualTo(4);
  }

  static Stream<Arguments> structConstantProvider() {
    return Stream.of(
        Arguments.of(struct().pad(4), 4),
        Arguments.of(struct().bool(), BOOL.size()),
        Arguments.of(struct().int8(), INT8.size()),
        Arguments.of(struct().int16(), INT16.size()),
        Arguments.of(struct().int32(), INT32.size()),
        Arguments.of(struct().int64(), INT64.size()),
        Arguments.of(struct().uint8(), UINT8.size()),
        Arguments.of(struct().uint16(), UINT16.size()),
        Arguments.of(struct().uint32(), UINT32.size()),
        Arguments.of(struct().uint64(), UINT64.size()),
        Arguments.of(struct().float32(), FLOAT32.size()),
        Arguments.of(struct().float64(), FLOAT64.size()),
        Arguments.of(struct().struct().int8().end(), INT8.size()),
        Arguments.of(struct().int8Array(constant(5)), INT8.size() * 5),
        Arguments.of(struct().int16Array(constant(5)), INT16.size() * 5),
        Arguments.of(struct().int32Array(constant(5)), INT32.size() * 5),
        Arguments.of(struct().int64Array(constant(5)), INT64.size() * 5),
        Arguments.of(struct().uint8Array(constant(5)), UINT8.size() * 5),
        Arguments.of(struct().uint16Array(constant(5)), UINT16.size() * 5),
        Arguments.of(struct().uint32Array(constant(5)), UINT32.size() * 5),
        Arguments.of(struct().uint64Array(constant(5)), UINT64.size() * 5),
        Arguments.of(struct().float32Array(constant(5)), FLOAT32.size() * 5),
        Arguments.of(struct().float64Array(constant(5)), FLOAT64.size() * 5),
        Arguments.of(struct().struct().int8Array(constant(5)).end(), INT8.size() * 5)
    );
  }

  @ParameterizedTest
  @MethodSource("structConstantProvider")
  void getByteLengthConstantAlign(StructBuilder builder, long expectedSize) {
    var struct = builder.align(5).build();
    var alignLength = struct.getType(1).getByteLength(struct);
    assertThat(alignLength).isEqualTo(5 - expectedSize % 5);
    assertThat(struct.getByteLength()).isEqualTo(expectedSize + alignLength);
    }

  static Stream<Arguments> structVariableProvider() {
    return Stream.of(
        Arguments.of(struct().int8().int8Array(0).align(5).build().addInt8(1, (byte) 8), 1 + 1 + 4),
        Arguments.of(struct().int8().int16Array(0).align(5).build().addInt16(1, (short) 8), 1 + 2 + 3),
        Arguments.of(struct().int8().int32Array(0).align(5).build().addInt32(1, 8), 1 + 4 + 1),
        Arguments.of(struct().int8().int64Array(0).align(5).build().addInt64(1, 8), 1 + 8 + 2),
        Arguments.of(struct().int8().uint8Array(0).align(5).build().addUint8(1, (short) 8), 1 + 1 + 4),
        Arguments.of(struct().int8().uint16Array(0).align(5).build().addUint16(1, 8), 1 + 2 + 3),
        Arguments.of(struct().int8().uint32Array(0).align(5).build().addUint32(1, 8), 1 + 4 + 1),
        Arguments.of(struct().int8().uint64Array(0).align(5).build().addUint64(1, BigInteger.valueOf(8)), 1 + 8 + 2),
        Arguments.of(struct().int8().float32Array(0).align(5).build().addFloat32(1, 8f), 1 + 4 + 1),
        Arguments.of(struct().int8().float64Array(0).align(5).build().addFloat64(1, 8d), 1 + 8 + 2),
        Arguments.of(struct()
            .primitive().constant((byte) 64).int8()
            .struct()
              .primitive().constant((byte) 6).int8()
              .primitive().lengthExpression(Expression.valueOf(0)).constant((byte) 8).int8()
              .end()
            .align(5)
            .build(), 1 + 1 + 6 + 3)
    );
  }

  @ParameterizedTest
  @MethodSource("structVariableProvider")
  void getByteLengthVariableAlign(Struct struct, long expectedSize) {
    var alignLength = struct.getByteLength(2);
    var dataLength = struct.getByteLength(1);
    assertThat(alignLength).isEqualTo(5 - dataLength % 5);
    assertThat(struct.getByteLength()).isEqualTo(expectedSize);
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
  void isAllocatedAlign() {
    var struct = struct()
        .int64()
        .align(16)
        .build();
    struct.setInt64(0, 11111111);

    assertThat(struct.getByteArray()).isEqualTo(ba().int64(11111111L).pad(8));
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

  @Test
  void removeAlign() {
    var struct = struct()
        .int8()
        .align(8)
        .build();

    assertThatThrownBy(() -> struct.removeAll(1))
        .isInstanceOf(ClassCastException.class)
        .hasMessageContaining("PadType cannot be cast to class");
  }
}
