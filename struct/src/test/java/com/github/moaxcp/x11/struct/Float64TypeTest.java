package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11.struct.Builders.struct;
import static com.github.moaxcp.x11.struct.ByteArray.ba;
import static com.github.moaxcp.x11.struct.ByteLengthListener.align;
import static com.github.moaxcp.x11.struct.Expression.constant;
import static com.github.moaxcp.x11.struct.Expression.valueOf;
import static com.github.moaxcp.x11.struct.Float64Type.float64Type;
import static com.github.moaxcp.x11.struct.Primitive.FLOAT64;
import static com.github.moaxcp.x11.struct.PrimitiveBuilder.primitive;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Float64TypeTest {

  @Test
  void constructor() {
    var type = float64Type();
    assertThat(type.getPosition()).isEqualTo(-1);
  }

  @Test
  void constructorPosition() {
    var type = float64Type(15);
    assertThat(type.getPosition()).isEqualTo(15);
  }

  @Test
  void constructorEverything() {
    var struct = struct()
        .float64()
        .primitive().constant(3.0d).lengthExpression(valueOf(0)).float64()
        .align(2)
        .build();

    assertThat(struct.getByteLength()).isEqualTo(FLOAT64.size() + 2);
    assertThat(struct.<Float64Type>getType(1))
        .isEqualTo(primitive().position(1).byteLengthListener(align(2)).constant(3.0d).lengthExpression(valueOf(0)).float64());
  }

  @Test
  void copy() {
    var type = float64Type();
    var copy = type.copy(15);
    assertThat(copy.getPosition()).isEqualTo(15);
  }

  @Test
  void getByteLength() {
    var struct = struct()
        .float64()
        .build();

    assertThat(struct.getByteLength()).isEqualTo(FLOAT64.size());
    assertThat(struct.getType(0).getByteLength(struct)).isEqualTo(FLOAT64.size());
  }

  @Test
  void isFixedLength() {
    var struct = struct()
        .float64()
        .build();

    assertThat(struct.getType(0).isFixedLength(struct)).isTrue();
    assertThat(struct.isFixedLength()).isTrue();
  }

  @Test
  void isFixedLengthArray() {
    var struct = struct()
        .float64Array(constant(3))
        .build();

    assertThat(struct.getType(0).isFixedLength(struct)).isTrue();
    assertThat(struct.isFixedLength()).isTrue();
  }

  @Test
  void allocate() {
    var struct = struct()
        .float64()
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0,0,0,0,0,0,0,0});
  }

  @Test
  void allocate_with_constant() {
    var struct = struct()
        .primitive().constant(3.0d).float64()
        .build();

    // 3.0d -> 0x4008000000000000 -> {64,8,0,0,0,0,0,0}
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {64, 8, 0, 0, 0, 0, 0, 0});
  }

  @Test
  void allocate_empty_array() {
    var struct = struct()
        .float64()
        .float64Array(0)
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0,0,0,0,0,0,0,0});
  }

  @Test
  void allocate_array_length_with_constant() {
    var struct = struct()
        .primitive().constant(3.0d).float64()
        .float64Array(0)
        .build();

    // 3.0d then three zeros
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {
        64, 8, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0
    });
  }

  @Test
  void allocate_array_length_and_array_with_constant() {
    var struct = struct()
        .primitive().constant(3.0d).float64()
        .primitive().constant(2.0d).lengthField(0).float64()
        .build();

    // 3.0d then 3 elements of 2.0d
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {
        64, 8, 0, 0, 0, 0, 0, 0,
        64, 0, 0, 0, 0, 0, 0, 0,
        64, 0, 0, 0, 0, 0, 0, 0,
        64, 0, 0, 0, 0, 0, 0, 0
    });
  }

  @Test
  void setWrapper() {
    var struct = struct()
        .float64()
        .build();

    assertThatThrownBy(() -> ((Float64Type) struct.getType(0)).set(struct, Double.valueOf(2.0d)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("set(Pointer, Double) not supported for Float64Type. Use set(Pointer, double) instead.");
  }

  @Test
  void setFloat64() {
    var struct = struct()
        .float64()
        .build();

    struct.setFloat64(0, 2.0d);

    // 2.0d -> 0x4000000000000000
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {64, 0, 0, 0, 0, 0, 0, 0});
  }

  @Test
  void setFloat64_position_negative() {
    var struct = struct()
        .float64()
        .build();

    assertThatThrownBy(() -> struct.setFloat64(-1, 2.0d))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 1");
  }

  @Test
  void setFloat64_position_greater_than_length() {
    var struct = struct()
        .float64()
        .build();

    assertThatThrownBy(() -> struct.setFloat64(2, 2.0d))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 1");
  }

  @Test
  void setFloat64_not_allocated() {
    var struct = struct()
        .allocated()
        .float64()
        .build();

    assertThatThrownBy(() -> struct.setFloat64(0, 2.0d))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void setFloat64_constant() {
    var struct = struct()
        .primitive().constant(3.0d).float64()
        .build();

    assertThatThrownBy(() -> struct.setFloat64(0, 2.0d))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Float64Type at position 0 is constant index: 0 value: 2.0 constant: 3.0");
  }

  @Test
  void setArrayWrapper() {
    var struct = struct()
        .float64()
        .float64Array(0)
        .build();

    assertThatThrownBy(() -> ((Float64Type) struct.getType(1)).set(struct, 0, Double.valueOf(2.0d)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("set(Pointer, long, Double) not supported for Float64Type. Use set(Pointer, long, double) instead.");
  }

  @Test
  void setFloat64Array() {
    var struct = struct()
        .float64()
        .float64Array(0)
        .float64()
        .fromBytes(ba().float64(2).float64(5).float64(3).float64(4))
        .build();

    struct.setFloat64(1, 0, 2.0d);

    assertThat(struct.getFloat64(0)).isEqualTo(2.0);
    assertThat(struct.getFloat64(1, 0)).isEqualTo(2.0d);
    assertThat(struct.getByteArray()).isEqualTo(ba().float64(2).float64(2).float64(3).float64(4));
  }

  @Test
  void setFloat64Array_negative() {
    var struct = struct()
        .float64()
        .float64Array(0)
        .float64()
        .fromBytes(ba().float64(2).float64(2).float64(3).float64(4))
        .build();

    assertThatThrownBy(() -> struct.setFloat64(1, -1, 2.0d))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Float64Type at position 1 index: -1 length: 2");
  }

  @Test
  void setFloat64Array_greater_than_length() {
    var struct = struct()
        .float64()
        .float64Array(0)
        .float64()
        .fromBytes(ba().float64(2).float64(2).float64(3).float64(4))
        .build();

    assertThatThrownBy(() -> struct.setFloat64(1, 2, 2.0d))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Float64Type at position 1 index: 2 length: 2");

    assertThat(struct.getByteArray()).isEqualTo(ba().float64(2).float64(2).float64(3).float64(4));
  }

  @Test
  void setFloat64Array_not_allocated() {
    var struct = struct()
        .allocated()
        .float64()
        .float64Array(0)
        .build();

    assertThatThrownBy(() -> struct.setFloat64(1, 0, 2.0d))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void setFloat64Array_index_0_not_array() {
    var struct = struct()
        .float64()
        .build();

    struct.setFloat64(0, 0, 2.0d);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {64, 0, 0, 0, 0, 0, 0, 0});
  }

  @Test
  void setFloat64Array_index_1_not_array() {
    var struct = struct()
        .float64()
        .build();

    assertThatThrownBy(() -> struct.setFloat64(0, 1, 2.0d))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Float64Type at position 0 index: 1 length: 1");
  }

  @Test
  void setFloat64Array_constant_value_and_length() {
    var struct = struct()
        .primitive().constant(3.0d).lengthExpression(constant(3)).float64()
        .build();

    assertThatThrownBy(() -> struct.setFloat64(0, 2, 2.0d))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Float64Type at position 0 is constant index: 2 value: 2.0 constant: 3.0");
  }

  @Test
  void setFloat64Array_constant_value() {
    var struct = struct()
        .float64()
        .primitive().constant(3.0d).lengthField(0).float64()
        .fromBytes(new byte[] {
            64, 0, 0, 0, 0, 0, 0, 0,
            64, 8, 0, 0, 0, 0, 0, 0,
            64, 8, 0, 0, 0, 0, 0, 0
        })
        .build();

    assertThatThrownBy(() -> struct.setFloat64(1, 1, 2.0d))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Float64Type at position 1 is constant index: 1 value: 2.0 constant: 3.0");
  }

  @Test
  void setFloat64Array_constant_value_same() {
    var struct = struct()
        .float64()
        .primitive().constant(3.0d).lengthField(0).float64()
        .fromBytes(new byte[] {
            64, 0, 0, 0, 0, 0, 0, 0,
            64, 8, 0, 0, 0, 0, 0, 0,
            64, 8, 0, 0, 0, 0, 0, 0
        })
        .build();

    struct.setFloat64(1, 1, 3.0d);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {
        64, 0, 0, 0, 0, 0, 0, 0,
        64, 8, 0, 0, 0, 0, 0, 0,
        64, 8, 0, 0, 0, 0, 0, 0
    });
  }

  @Test
  @Disabled
  void setFloat64Array_set_length_field_without_adding_to_array() {
    var struct = struct()
        .float64()
        .float64Array(0)
        .build();

    assertThatThrownBy(() -> struct.setFloat64(0, 2.0d))
        .isInstanceOf(AssertionError.class)
        .hasMessage("Float64Type at position 0 is being set and will not match the array it is used as a length for.");
  }

  @Test
  void getWrapper() {
    var struct = struct()
        .float64()
        .build();

    assertThatThrownBy(() -> ((Float64Type) struct.getType(0)).get(struct))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("get(Pointer) not supported for Float64Type. Use getFloat64(Pointer) instead.");
  }

  @Test
  void getFloat64() {
    var struct = struct()
        .float64()
        .build();

    struct.setFloat64(0, 2.0d);

    assertThat(struct.getFloat64(0)).isEqualTo(2.0d);
  }

  @Test
  void getFloat64_position_negative() {
    var struct = struct()
        .float64()
        .build();

    assertThatThrownBy(() -> struct.getFloat64(-1))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 1");
  }

  @Test
  void getFloat64_position_greater_than_length() {
    var struct = struct()
        .float64()
        .build();

    assertThatThrownBy(() -> struct.getFloat64(2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 1");
  }

  @Test
  void getFloat64Allocated() {
    var struct = struct()
        .float64()
        .build();

    assertThat(struct.getFloat64(0)).isEqualTo(0.0d);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0,0,0,0,0,0,0,0});
  }

  @Test
  void getFloat64NotAllocated() {
    var struct = struct()
        .allocated()
        .float64()
        .build();

    assertThatThrownBy(() -> struct.getFloat64(0)).isInstanceOf(ArrayIndexOutOfBoundsException.class);
  }

  @Test
  void getFloat64_constant() {
    var struct = struct()
        .primitive().constant(3.0d).float64()
        .build();

    assertThat(struct.getFloat64(0)).isEqualTo(3.0d);
  }

  @Test
  void getFloat64_index_0_not_array() {
    var struct = struct()
        .float64()
        .fromBytes(new byte[] {0,0,0,0,0,0,0,0})
        .build();

    assertThat(struct.getFloat64(0, 0)).isEqualTo(0.0d);
  }

  @Test
  void getFloat64_index_1_not_array() {
    var struct = struct()
        .float64()
        .fromBytes(new byte[] {64, 0, 0, 0, 0, 0, 0, 0})
        .build();

    assertThatThrownBy(() -> struct.getFloat64(0, 1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Float64Type at position 0 index: 1 length: 1");
  }

  @Test
  void getArrayWrapper() {
    var struct = struct()
        .float64()
        .float64Array(0)
        .build();

    assertThatThrownBy(() -> ((Float64Type) struct.getType(1)).get(struct, 0))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("get(Pointer, long) not supported for Float64Type. Use getFloat64(Pointer, long) instead.");
  }

  @Test
  void getFloat64Array() {
    var struct = struct()
        .float64()
        .float64Array(0)
        .fromBytes(new byte[] {
            64, 0, 0, 0, 0, 0, 0, 0,
            64, 8, 0, 0, 0, 0, 0, 0,
            64, 0, 0, 0, 0, 0, 0, 0
        })
        .build();

    assertThat(struct.getFloat64(1, 0)).isEqualTo(3.0d);
    assertThat(struct.getFloat64(1, 1)).isEqualTo(2.0d);
  }

  @Test
  void getFloat64Array_negative() {
    var struct = struct()
        .float64()
        .float64Array(0)
        .build();

    assertThatThrownBy(() -> struct.getFloat64(1, -1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Float64Type at position 1 index: -1 length: 0");
  }

  @Test
  void getFloat64Array_greater_than_length() {
    var struct = struct()
        .float64()
        .float64Array(0)
        .fromBytes(new byte[] {
            64, 0, 0, 0, 0, 0, 0, 0,
            64, 8, 0, 0, 0, 0, 0, 0,
            64, 0, 0, 0, 0, 0, 0, 0
        })
        .build();

    assertThatThrownBy(() -> struct.getFloat64(1, 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Float64Type at position 1 index: 2 length: 2");
  }

  @Test
  void getFloat64Array_not_allocated() {
    var struct = struct()
        .allocated()
        .float64()
        .float64Array(0)
        .build();

    assertThatThrownBy(() -> struct.getFloat64(1, 0))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void getFloat64Array_constant() {
    var struct = struct()
        .primitive().constant(3.0d).lengthExpression(constant(3)).float64()
        .build();

    assertThat(struct.getFloat64(0, 2)).isEqualTo(3.0d);
  }

  @Test
  void addFloat64Wrapper() {
    var struct = struct()
        .float64()
        .float64Array(0)
        .build();

    assertThatThrownBy(() -> ((Float64Type) struct.getType(1)).add(struct, Double.valueOf(1.0d)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("add(Pointer, Double) not supported for Float64Type. Use add(Pointer, double) instead.");
  }

  @Test
  void addFloat64Wrapper_with_index() {
    var struct = struct()
        .float64()
        .float64Array(0)
        .build();

    assertThatThrownBy(() -> ((Float64Type) struct.getType(1)).add(struct, 0, Double.valueOf(1.0d)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("add(Pointer, long, Double) not supported for Float64Type. Use add(Pointer, long, double) instead.");
  }

  @Test
  void addFloat64() {
    var struct = struct()
        .float64()
        .float64Array(0)
        .fromBytes(ba().float64(2).float64(2).float64(3))
        .build();

    struct.addFloat64(1, 3.0d);
    struct.addFloat64(1, 2.0d);

    assertThat(struct.getByteArray()).isEqualTo(ba().float64(4).float64(2).float64(3).float64(3).float64(2));
  }

  @Test
  void addFloat64_position_negative() {
    var struct = struct()
        .float64()
        .float64Array(0)
        .fromBytes(new byte[] {
            64, 0, 0, 0, 0, 0, 0, 0,
            64, 8, 0, 0, 0, 0, 0, 0,
            64, 0, 0, 0, 0, 0, 0, 0
        })
        .build();

    assertThatThrownBy(() -> struct.addFloat64(-1, 3.0d))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 2");
  }

  @Test
  void addFloat64_position_greater_than_length() {
    var struct = struct()
        .float64()
        .float64Array(0)
        .fromBytes(new byte[] {
            64, 0, 0, 0, 0, 0, 0, 0,
            64, 8, 0, 0, 0, 0, 0, 0,
            64, 0, 0, 0, 0, 0, 0, 0
        })
        .build();

    assertThatThrownBy(() -> struct.addFloat64(3, 3.0d))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 3 out of bounds for length 2");
  }

  @Test
  void addFloat64_not_allocated() {
    var struct = struct()
        .allocated()
        .float64()
        .float64Array(0)
        .build();
    assertThatThrownBy(() -> struct.addFloat64(1, 3.0d))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void addFloat64Array_constant() {
    var struct = struct()
        .primitive().constant(3.0d).lengthExpression(constant(3)).float64()
        .build();

    assertThatThrownBy(() -> struct.addFloat64(0, 4.0d))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Float64Type at position 0 is constant index: 3 value: 4.0 constant: 3.0");
  }

  @Test
  void addFloat64_not_array() {
    var struct = struct()
        .float64()
        .fromBytes(new byte[] {64, 0, 0, 0, 0, 0, 0, 0})
        .build();
    assertThatThrownBy(() -> struct.addFloat64(0, 3.0d))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Float64Type cannot add to non-array type at position 0 index: 1 length: 1");
  }

  @Test
  void addFloat64_with_index() {
    var struct = struct()
        .float64()
        .float64Array(0)
        .fromBytes(ba().float64(2).float64(2).float64(3))
        .build();

    struct.addFloat64(1, 0, 3.0d);
    struct.addFloat64(1, 1, 2.0d);

    assertThat(struct.getByteArray()).isEqualTo(ba().float64(4).float64(3).float64(2).float64(2).float64(3));
  }

  @Test
  void addFloat64_with_index_negative() {
    var struct = struct()
        .float64()
        .float64Array(0)
        .fromBytes(new byte[] {
            64, 0, 0, 0, 0, 0, 0, 0,
            64, 8, 0, 0, 0, 0, 0, 0,
            64, 0, 0, 0, 0, 0, 0, 0
        })
        .build();

    assertThatThrownBy(() -> struct.addFloat64(1, -1, 3.0d))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Float64Type at position 1 index: -1 new length: 3");
  }

  @Test
  void addFloat64_with_index_greater_than_length() {
    var struct = struct()
        .float64()
        .float64Array(0)
        .fromBytes(new byte[] {
            64, 0, 0, 0, 0, 0, 0, 0,
            64, 8, 0, 0, 0, 0, 0, 0,
            64, 0, 0, 0, 0, 0, 0, 0
        })
        .build();

    assertThatThrownBy(() -> struct.addFloat64(1, 3, 3.0d))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Float64Type at position 1 index: 3 new length: 3");
  }

  @Test
  void addFloat64_with_index_not_allocated() {
    var struct = struct()
        .allocated()
        .float64()
        .float64Array(0)
        .build();

    assertThatThrownBy(() -> struct.addFloat64(1, 0, 3.0d))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void addFloat64_with_index_0_not_array() {
    var struct = struct()
        .float64()
        .fromBytes(new byte[] {64, 0, 0, 0, 0, 0, 0, 0})
        .build();

    assertThatThrownBy(() -> struct.addFloat64(0, 0, 3.0d))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Float64Type cannot add to non-array type at position 0 index: 0 length: 1");
  }

  @Test
  void addFloat64_with_index_1_not_array() {
    var struct = struct()
        .float64()
        .fromBytes(new byte[] {64, 0, 0, 0, 0, 0, 0, 0})
        .build();

    assertThatThrownBy(() -> struct.addFloat64(0, 1, 3.0d))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Float64Type cannot add to non-array type at position 0 index: 1 length: 1");
  }

  @Test
  void addFloat64Array_with_index_constant() {
    var struct = struct()
        .primitive().constant(3.0d).lengthExpression(constant(3)).float64()
        .build();

    assertThatThrownBy(() -> struct.addFloat64(0, 2, 4.0d))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Float64Type at position 0 is constant index: 2 value: 4.0 constant: 3.0");
  }

  @Test
  void removeFloat64() {
    var struct = struct()
        .float64()
        .float64Array(0)
        .fromBytes(new byte[] {
            64, 0, 0, 0, 0, 0, 0, 0,
            64, 8, 0, 0, 0, 0, 0, 0,
            64, 0, 0, 0, 0, 0, 0, 0
        })
        .build();

    struct.removeAll(1);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] { 0,0,0,0,0,0,0,0 } );
  }

  @Test
  void removeFloat64_position_negative() {
    var struct = struct()
        .float64()
        .float64Array(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(-1))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 2");
  }

  @Test
  void removeFloat64_position_greater_than_length() {
    var struct = struct()
        .float64()
        .float64Array(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 2");
  }

  @Test
  void removeFloat64_not_allocated() {
    var struct = struct()
        .allocated()
        .float64()
        .float64Array(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void removeFloat64_not_array() {
    var struct = struct()
        .float64()
        .build();

    assertThatThrownBy(() -> struct.removeAll(0))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Float64Type cannot remove from non-array type at position 0");
  }

  @Test
  void removeFloat64_with_index_0() {
    var struct = struct()
        .float64()
        .float64Array(0)
        .build();

    struct.addFloat64(1, 3.0d);
    struct.addFloat64(1, 2.0d);
    struct.remove(1, 0);

    assertThat(((Float64Type) struct.getType(1)).getFloat64(struct, 0)).isEqualTo(2.0d);

    assertThat(struct.getByteArray()).isEqualTo(ba().float64(1).float64(2));
  }

  @Test
  void removeFloat64_with_index_1() {
    var struct = struct()
        .float64()
        .float64Array(0)
        .build();
    struct.addFloat64(1, 3.0d);
    struct.addFloat64(1, 2.0d);

    struct.remove(1, 1);

    assertThat(struct.getByteArray()).isEqualTo(ba().float64(1).float64(3));
  }

  @Test
  void removeFloat64_with_index_negative() {
    var struct = struct()
        .float64()
        .float64Array(0)
        .fromBytes(new byte[] {
            64, 0, 0, 0, 0, 0, 0, 0,
            64, 8, 0, 0, 0, 0, 0, 0,
            64, 0, 0, 0, 0, 0, 0, 0
        })
        .build();

    assertThatThrownBy(() -> struct.remove(1, -1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Float64Type at position 1 index: -1 length: 2");
  }

  @Test
  void removeFloat64_with_index_greater_than_length() {
    var struct = struct()
        .float64()
        .float64Array(0)
        .fromBytes(new byte[] {
            64, 0, 0, 0, 0, 0, 0, 0,
            64, 8, 0, 0, 0, 0, 0, 0,
            64, 0, 0, 0, 0, 0, 0, 0
        })
        .build();

    assertThatThrownBy(() -> struct.remove(1, 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Float64Type at position 1 index: 2 length: 2");
  }

  @Test
  void removeFloat64_with_index_not_array() {
    var struct = struct()
        .float64()
        .fromBytes(new byte[] {64, 0, 0, 0, 0, 0, 0, 0})
        .build();

    assertThatThrownBy(() -> struct.remove(0, 0))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Float64Type cannot remove from non-array type at position 0");
  }

  @Test
  void removeFloat64Array_fixed_length() {
    var struct = struct()
        .primitive().constant(3.0d).lengthExpression(constant(3)).float64()
        .build();

    assertThatThrownBy(() -> struct.removeAll(0))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Cannot remove fixed length array Float64Type at position 0");
  }

  @Test
  void removeFloat64Array_fixed_length_with_index() {
    var struct = struct()
        .primitive().constant(3.0d).lengthExpression(constant(3)).float64()
        .build();

    assertThatThrownBy(() -> struct.remove(0, 2))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Cannot remove element from fixed length array Float64Type at position 0 index: 2");
  }
}
