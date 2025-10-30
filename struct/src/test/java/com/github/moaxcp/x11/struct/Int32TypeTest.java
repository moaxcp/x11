package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11.struct.Builders.struct;
import static com.github.moaxcp.x11.struct.ByteLengthListener.align;
import static com.github.moaxcp.x11.struct.Expression.constant;
import static com.github.moaxcp.x11.struct.Expression.valueOf;
import static com.github.moaxcp.x11.struct.Int32Type.int32Type;
import static com.github.moaxcp.x11.struct.Primitive.INT32;
import static com.github.moaxcp.x11.struct.PrimitiveBuilder.primitive;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Int32TypeTest {

  @Test
  void constructor() {
    var type = int32Type();
    assertThat(type.getPosition()).isEqualTo(-1);
  }

  @Test
  void constructorPosition() {
    var type = int32Type(15);
    assertThat(type.getPosition()).isEqualTo(15);
  }

  @Test
  void constructorEverything() {
    var struct = struct()
        .int32()
        .primitive().constant(5).lengthExpression(valueOf(0)).int32()
        .align(2)
        .build();

    assertThat(struct.getByteLength()).isEqualTo(INT32.size() + 2);
    assertThat(struct.<Int32Type>getType(1))
        .isEqualTo(primitive().position(1).byteLengthListener(align(2)).constant(5).lengthExpression(valueOf(0)).int32());
  }

  @Test
  void copy() {
    var type = int32Type();
    var copy = type.copy(15);
    assertThat(copy.getPosition()).isEqualTo(15);
  }

  @Test
  void getByteLength() {
    var struct = struct()
        .int32()
        .build();

    assertThat(struct.getByteLength()).isEqualTo(INT32.size());
    assertThat(struct.getType(0).getByteLength(struct)).isEqualTo(INT32.size());
  }

  @Test
  void isFixedLength() {
    var struct = struct()
        .int32()
        .build();

    assertThat(struct.getType(0).isFixedLength(struct)).isTrue();
    assertThat(struct.isFixedLength()).isTrue();
  }

  @Test
  void isFixedLengthArray() {
    var struct = struct()
        .int32Array(constant(5))
        .build();

    assertThat(struct.getType(0).isFixedLength(struct)).isTrue();
    assertThat(struct.isFixedLength()).isTrue();
  }

  @Test
  void allocate() {
    var struct = struct()
        .int32()
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 0, 0, 0});
  }

  @Test
  void allocate_with_constant() {
    var struct = struct()
        .primitive().constant(5).int32()
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 0, 0, 5});
  }

  @Test
  void allocate_empty_array() {
    var struct = struct()
        .int32()
        .int32Array(0)
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 0, 0, 0});
  }

  @Test
  void allocate_array_length_with_constant() {
    var struct = struct()
        .primitive().constant(5).int32()
        .int32Array(0)
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {
        0, 0, 0, 5,
        0, 0, 0, 0,
        0, 0, 0, 0,
        0, 0, 0, 0,
        0, 0, 0, 0,
        0, 0, 0, 0
    });
  }

  @Test
  void allocate_array_length_and_array_with_constant() {
    var struct = struct()
        .primitive().constant(5).int32()
        .primitive().constant(6).lengthField(0).int32()
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {
        0, 0, 0, 5,
        0, 0, 0, 6,
        0, 0, 0, 6,
        0, 0, 0, 6,
        0, 0, 0, 6,
        0, 0, 0, 6
    });
  }

  @Test
  void setWrapper() {
    var struct = struct()
        .int32()
        .build();

    assertThatThrownBy(() -> ((Int32Type) struct.getType(0)).set(struct, Integer.valueOf(2)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("set(Pointer, Integer) not supported for Int32Type. Use set(Pointer, int) instead.");
  }

  @Test
  void setInt32() {
    var struct = struct()
        .int32()
        .build();

    struct.setInt32(0, 2);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 0, 0, 2});
  }

  @Test
  void setInt32_position_negative() {
    var struct = struct()
        .int32()
        .build();

    assertThatThrownBy(() -> struct.setInt32(-1, 2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 1");
  }

  @Test
  void setInt32_position_greater_than_length() {
    var struct = struct()
        .int32()
        .build();

    assertThatThrownBy(() -> struct.setInt32(2, 2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 1");
  }

  @Test
  void setInt32_not_allocated() {
    var struct = struct()
        .allocated()
        .int32()
        .build();

    assertThatThrownBy(() -> struct.setInt32(0, 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void setInt32_constant() {
    var struct = struct()
        .primitive().constant(5).int32()
        .build();

    assertThatThrownBy(() -> struct.setInt32(0, 2))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Int32Type at position 0 is constant index: 0 value: 2 constant: 5");
  }

  @Test
  void setArrayWrapper() {
    var struct = struct()
        .int32()
        .int32Array(0)
        .build();

    assertThatThrownBy(() -> ((Int32Type) struct.getType(1)).set(struct, 0, Integer.valueOf(2)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("set(Pointer, long, Integer) not supported for Int32Type. Use set(Pointer, long, int) instead.");
  }

  @Test
  void setInt32Array() {
    var struct = struct()
        .int32()
        .int32Array(0)
        .int32()
        .fromBytes(new byte[] {
            0, 0, 0, 1,
            0, 0, 0, 2,
            0, 0, 0, 3
        })
        .build();

    struct.setInt32(1, 0, 5);

    assertThat(struct.getInt32(0)).isEqualTo(1);
    assertThat(struct.getInt32(1, 0)).isEqualTo(5);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {
        0, 0, 0, 1,
        0, 0, 0, 5,
        0, 0, 0, 3
    });
  }

  @Test
  void setInt32Array_negative() {
    var struct = struct()
        .int32()
        .int32Array(0)
        .int32()
        .fromBytes(new byte[] {
            0, 0, 0, 1,
            0, 0, 0, 2,
            0, 0, 0, 3
        })
        .build();

    assertThatThrownBy(() -> struct.setInt32(1, -1, 5))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Int32Type at position 1 index: -1 length: 1");
  }

  @Test
  void setInt32Array_greater_than_length() {
    var struct = struct()
        .int32()
        .int32Array(0)
        .int32()
        .fromBytes(new byte[] {
            0, 0, 0, 1,
            0, 0, 0, 2,
            0, 0, 0, 3
        })
        .build();

    assertThatThrownBy(() -> struct.setInt32(1, 2, 5))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int32Type at position 1 index: 2 length: 1");

    assertThat(struct.getInt32(0)).isEqualTo(1);
    assertThat(struct.getInt32(1, 0)).isEqualTo(2);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {
        0, 0, 0, 1,
        0, 0, 0, 2,
        0, 0, 0, 3
    });
  }

  @Test
  void setInt32Array_not_allocated() {
    var struct = struct()
        .allocated()
        .int32()
        .int32Array(0)
        .build();

    assertThatThrownBy(() -> struct.setInt32(1, 0, 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void setInt32Array_index_0_not_array() {
    var struct = struct()
        .int32()
        .build();

    struct.setInt32(0, 0, 2);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 0, 0, 2});
  }

  @Test
  void setInt32Array_index_1_not_array() {
    var struct = struct()
        .int32()
        .build();

    assertThatThrownBy(() -> struct.setInt32(0, 1, 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int32Type at position 0 index: 1 length: 1");
  }

  @Test
  void setInt32Array_constant_value_and_length() {
    var struct = struct()
        .primitive().constant(5).lengthExpression(constant(5)).int32()
        .build();

    assertThatThrownBy(() -> struct.setInt32(0, 3, 2))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Int32Type at position 0 is constant index: 3 value: 2 constant: 5");
  }

  @Test
  void setInt32Array_constant_value() {
    var struct = struct()
        .int32()
        .primitive().constant(5).lengthField(0).int32()
        .fromBytes(new byte[] {
            0, 0, 0, 2,
            0, 0, 0, 5,
            0, 0, 0, 5
        })
        .build();

    assertThatThrownBy(() -> struct.setInt32(1, 1, 2))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Int32Type at position 1 is constant index: 1 value: 2 constant: 5");
  }

  @Test
  void setInt32Array_constant_value_same() {
    var struct = struct()
        .int32()
        .primitive().constant(5).lengthField(0).int32()
        .fromBytes(new byte[] {
            0, 0, 0, 2,
            0, 0, 0, 5,
            0, 0, 0, 5
        })
        .build();

    struct.setInt32(1, 1, 5);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {
        0, 0, 0, 2,
        0, 0, 0, 5,
        0, 0, 0, 5
    });
  }

  @Test
  @Disabled
  void setInt32Array_set_length_field_without_adding_to_array() {
    var struct = struct()
        .int32()
        .int32Array(0)
        .build();

    assertThatThrownBy(() -> struct.setInt32(0, 2))
        .isInstanceOf(AssertionError.class)
        .hasMessage("Int32Type at position 0 is being set and will not match the array it is used as a length for.");
  }

  @Test
  void getWrapper() {
    var struct = struct()
        .int32()
        .build();

    assertThatThrownBy(() -> ((Int32Type) struct.getType(0)).get(struct))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("get(Pointer) not supported for Int32Type. Use getInt32(Pointer) instead.");
  }

  @Test
  void getInt32() {
    var struct = struct()
        .int32()
        .build();

    struct.setInt32(0, 2);

    assertThat(struct.getInt32(0)).isEqualTo(2);
  }

  @Test
  void getInt32_position_negative() {
    var struct = struct()
        .int32()
        .build();

    assertThatThrownBy(() -> struct.getInt32(-1))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 1");
  }

  @Test
  void getInt32_position_greater_than_length() {
    var struct = struct()
        .int32()
        .build();

    assertThatThrownBy(() -> struct.getInt32(2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 1");
  }

  @Test
  void getInt32Allocated() {
    var struct = struct()
        .int32()
        .build();

    assertThat(struct.getInt32(0)).isEqualTo(0);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 0, 0, 0});
  }

  @Test
  void getInt32NotAllocated() {
    var struct = struct()
        .allocated()
        .int32()
        .build();

    assertThatThrownBy(() -> struct.getInt32(0)).isInstanceOf(ArrayIndexOutOfBoundsException.class);
  }

  @Test
  void getInt32_constant() {
    var struct = struct()
        .primitive().constant(5).int32()
        .build();

    assertThat(struct.getInt32(0)).isEqualTo(5);
  }

  @Test
  void getInt32_index_0_not_array() {
    var struct = struct()
        .int32()
        .fromBytes(new byte[] {0, 0, 0, 1})
        .build();

    assertThat(struct.getInt32(0, 0)).isEqualTo(1);
  }

  @Test
  void getInt32_index_1_not_array() {
    var struct = struct()
        .int32()
        .fromBytes(new byte[] {0, 0, 0, 1})
        .build();

    assertThatThrownBy(() -> struct.getInt32(0, 1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int32Type at position 0 index: 1 length: 1");
  }

  @Test
  void getArrayWrapper() {
    var struct = struct()
        .int32()
        .int32Array(0)
        .build();

    assertThatThrownBy(() -> ((Int32Type) struct.getType(1)).get(struct, 0))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("get(Pointer, long) not supported for Int32Type. Use getInt32(Pointer, long) instead.");
  }

  @Test
  void getInt32Array() {
    var struct = struct()
        .int32()
        .int32Array(0)
        .fromBytes(new byte[] {
            0, 0, 0, 2,
            0, 0, 0, 1,
            0, 0, 0, 2
        })
        .build();

    assertThat(struct.getInt32(1, 0)).isEqualTo(1);
    assertThat(struct.getInt32(1, 1)).isEqualTo(2);
  }

  @Test
  void getInt32Array_negative() {
    var struct = struct()
    .int32()
    .int32Array(0)
    .build();

    assertThatThrownBy(() -> struct.getInt32(1, -1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int32Type at position 1 index: -1 length: 0");
  }

  @Test
  void getInt32Array_greater_than_length() {
    var struct = struct()
        .int32()
        .int32Array(0)
        .fromBytes(new byte[] {
            0, 0, 0, 2,
            0, 0, 0, 1,
            0, 0, 0, 2
        })
        .build();

    assertThatThrownBy(() -> struct.getInt32(1, 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int32Type at position 1 index: 2 length: 2");
  }

  @Test
  void getInt32Array_not_allocated() {
    var struct = struct()
        .allocated()
        .int32()
        .int32Array(0)
        .build();

    assertThatThrownBy(() -> struct.getInt32(1, 0))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void getInt32Array_constant() {
    var struct = struct()
        .primitive().constant(5).lengthExpression(constant(5)).int32()
        .build();

    assertThat(struct.getInt32(0, 3)).isEqualTo(5);
  }

  @Test
  void addInt32Wrapper() {
    var struct = struct()
        .int32()
        .int32Array(0)
        .build();

    assertThatThrownBy(() -> ((Int32Type) struct.getType(1)).add(struct, Integer.valueOf(1)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("add(Pointer, Integer) not supported for Int32Type. Use add(Pointer, int) instead.");
  }

  @Test
  void addInt32Wrapper_with_index() {
    var struct = struct()
        .int32()
        .int32Array(0)
        .build();

    assertThatThrownBy(() -> ((Int32Type) struct.getType(1)).add(struct, 0, Integer.valueOf(1)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("add(Pointer, long, Integer) not supported for Int32Type. Use add(Pointer, long, int) instead.");
  }

  @Test
  void addInt32() {
    var struct = struct()
        .int32()
        .int32Array(0)
        .fromBytes(new byte[] {
            0, 0, 0, 2,
            0, 0, 0, 1,
            0, 0, 0, 2
        })
        .build();

    struct.addInt32(1, 3);
    struct.addInt32(1, 4);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {
        0, 0, 0, 4,
        0, 0, 0, 1,
        0, 0, 0, 2,
        0, 0, 0, 3,
        0, 0, 0, 4
    } );
  }

  @Test
  void addInt32_position_negative() {
    var struct = struct()
        .int32()
        .int32Array(0)
        .fromBytes(new byte[] {
            0, 0, 0, 2,
            0, 0, 0, 1,
            0, 0, 0, 2
        })
        .build();

    assertThatThrownBy(() -> struct.addInt32(-1, 3))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 2");
  }

  @Test
  void addInt32_position_greater_than_length() {
    var struct = struct()
        .int32()
        .int32Array(0)
        .fromBytes(new byte[] {
            0, 0, 0, 2,
            0, 0, 0, 1,
            0, 0, 0, 2
        })
        .build();

    assertThatThrownBy(() -> struct.addInt32(3, 3))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 3 out of bounds for length 2");
  }

  @Test
  void addInt32_not_allocated() {
    var struct = struct()
        .allocated()
        .int32()
        .int32Array(0)
        .build();
    assertThatThrownBy(() -> struct.addInt32(1, 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void addInt32Array_constant() {
    var struct = struct()
        .primitive().constant(5).lengthExpression(constant(5)).int32()
        .build();

    assertThatThrownBy(() -> struct.addInt32(0, 3))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Int32Type at position 0 is constant index: 5 value: 3 constant: 5");
  }

  @Test
  void addInt32_not_array() {
    var struct = struct()
        .int32()
        .fromBytes(new byte[] {0, 0, 0, 1})
        .build();
    assertThatThrownBy(() -> struct.addInt32(0, 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int32Type cannot add to non-array type at position 0 index: 1 length: 1");
  }

  @Test
  void addInt32_with_index() {
    var struct = struct()
        .int32()
        .int32Array(0)
        .fromBytes(new byte[] {
            0, 0, 0, 2,
            0, 0, 0, 1,
            0, 0, 0, 2
        })
        .build();

    struct.addInt32(1, 0, 3);
    struct.addInt32(1, 1, 4);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {
        0, 0, 0, 4,
        0, 0, 0, 3,
        0, 0, 0, 4,
        0, 0, 0, 1,
        0, 0, 0, 2
    } );
  }

  @Test
  void addInt32_with_index_negative() {
    var struct = struct()
        .int32()
        .int32Array(0)
        .fromBytes(new byte[] {
            0, 0, 0, 2,
            0, 0, 0, 1,
            0, 0, 0, 2
        })
        .build();

    assertThatThrownBy(() -> struct.addInt32(1, -1, 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int32Type at position 1 index: -1 new length: 3");
  }

  @Test
  void addInt32_with_index_greater_than_length() {
    var struct = struct()
        .int32()
        .int32Array(0)
        .fromBytes(new byte[] {
            0, 0, 0, 2,
            0, 0, 0, 1,
            0, 0, 0, 2
        })
        .build();

    assertThatThrownBy(() -> struct.addInt32(1, 3, 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int32Type at position 1 index: 3 new length: 3");
  }

  @Test
  void addInt32_with_index_not_allocated() {
    var struct = struct()
        .allocated()
        .int32()
        .int32Array(0)
        .build();

    assertThatThrownBy(() -> struct.addInt32(1, 0, 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void addInt32_with_index_0_not_array() {
    var struct = struct()
        .int32()
        .fromBytes(new byte[] {0, 0, 0, 1})
        .build();

    assertThatThrownBy(() -> struct.addInt32(0, 0, 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int32Type cannot add to non-array type at position 0 index: 0 length: 1");
  }

  @Test
  void addInt32_with_index_1_not_array() {
    var struct = struct()
        .int32()
        .fromBytes(new byte[] {0, 0, 0, 1})
        .build();

    assertThatThrownBy(() -> struct.addInt32(0, 1, 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int32Type cannot add to non-array type at position 0 index: 1 length: 1");
  }

  @Test
  void addInt32Array_with_index_constant() {
    var struct = struct()
        .primitive().constant(5).lengthExpression(constant(5)).int32()
        .build();

    assertThatThrownBy(() -> struct.addInt32(0, 3, 3))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Int32Type at position 0 is constant index: 3 value: 3 constant: 5");
  }

  @Test
  void removeInt32() {
    var struct = struct()
        .int32()
        .int32Array(0)
        .fromBytes(new byte[] {
            0, 0, 0, 2,
            0, 0, 0, 1,
            0, 0, 0, 2
        })
        .build();

    struct.removeAll(1);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] { 0, 0, 0, 0 } );
  }

  @Test
  void removeInt32_position_negative() {
    var struct = struct()
        .int32()
        .int32Array(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(-1))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 2");
  }

  @Test
  void removeInt32_position_greater_than_length() {
    var struct = struct()
        .int32()
        .int32Array(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 2");
  }

  @Test
  void removeInt32_not_allocated() {
    var struct = struct()
        .allocated()
        .int32()
        .int32Array(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void removeInt32_not_array() {
    var struct = struct()
        .int32()
        .build();

    assertThatThrownBy(() -> struct.removeAll(0))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int32Type cannot remove from non-array type at position 0");
  }

  @Test
  void removeInt32_with_index_0() {
    var struct = struct()
        .int32()
        .int32Array(0)
        .build();

    struct.addInt32(1, 1);
    struct.addInt32(1, 2);
    struct.remove(1, 0);

    assertThat(((Int32Type) struct.getType(1)).getInt32(struct, 0)).isEqualTo(2);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 0, 0, 1, 0, 0, 0, 2} );
  }

  @Test
  void removeInt32_with_index_1() {
    var struct = struct()
        .int32()
        .int32Array(0)
        .build();
    struct.addInt32(1, 1);
    struct.addInt32(1, 2);

    struct.remove(1, 1);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 0, 0, 1, 0, 0, 0, 1} );
  }

  @Test
  void removeInt32_with_index_negative() {
    var struct = struct()
        .int32()
        .int32Array(0)
        .fromBytes(new byte[] {
            0, 0, 0, 2,
            0, 0, 0, 1,
            0, 0, 0, 2
        })
        .build();

    assertThatThrownBy(() -> struct.remove(1, -1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int32Type at position 1 index: -1 length: 2");
  }

  @Test
  void removeInt32_with_index_greater_than_length() {
    var struct = struct()
        .int32()
        .int32Array(0)
        .fromBytes(new byte[] {
            0, 0, 0, 2,
            0, 0, 0, 1,
            0, 0, 0, 2
        })
        .build();

    assertThatThrownBy(() -> struct.remove(1, 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int32Type at position 1 index: 2 length: 2");
  }

  @Test
  void removeInt32_with_index_not_array() {
    var struct = struct()
        .int32()
        .fromBytes(new byte[] {0, 0, 0, 1})
        .build();

    assertThatThrownBy(() -> struct.remove(0, 0))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int32Type cannot remove from non-array type at position 0");
  }

  @Test
  void removeInt32Array_fixed_length() {
    var struct = struct()
        .primitive().constant(5).lengthExpression(constant(5)).int32()
        .build();

    assertThatThrownBy(() -> struct.removeAll(0))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Cannot remove fixed length array Int32Type at position 0");
  }

  @Test
  void removeInt32Array_fixed_length_with_index() {
    var struct = struct()
        .primitive().constant(5).lengthExpression(constant(5)).int32()
        .build();

    assertThatThrownBy(() -> struct.remove(0, 3))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Cannot remove element from fixed length array Int32Type at position 0 index: 3");
  }
}
