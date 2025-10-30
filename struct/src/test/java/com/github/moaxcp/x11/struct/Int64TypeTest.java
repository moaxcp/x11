package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11.struct.Builders.struct;
import static com.github.moaxcp.x11.struct.ByteLengthListener.align;
import static com.github.moaxcp.x11.struct.Expression.constant;
import static com.github.moaxcp.x11.struct.Expression.valueOf;
import static com.github.moaxcp.x11.struct.Int64Type.int64Type;
import static com.github.moaxcp.x11.struct.Primitive.INT64;
import static com.github.moaxcp.x11.struct.PrimitiveBuilder.primitive;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Int64TypeTest {

  @Test
  void constructor() {
    var type = int64Type();
    assertThat(type.getPosition()).isEqualTo(-1);
  }

  @Test
  void constructorPosition() {
    var type = int64Type(15);
    assertThat(type.getPosition()).isEqualTo(15);
  }

  @Test
  void constructorEverything() {
    var struct = struct()
        .int64()
        .primitive().constant(5L).lengthExpression(valueOf(0)).int64()
        .align(2)
        .build();

    assertThat(struct.getByteLength()).isEqualTo(INT64.size() + 2);
    assertThat(struct.<Int64Type>getType(1))
        .isEqualTo(primitive().position(1).byteLengthListener(align(2)).constant(5L).lengthExpression(valueOf(0)).int64());
  }

  @Test
  void copy() {
    var type = int64Type();
    var copy = type.copy(15);
    assertThat(copy.getPosition()).isEqualTo(15);
  }

  @Test
  void getByteLength() {
    var struct = struct()
        .int64()
        .build();

    assertThat(struct.getByteLength()).isEqualTo(INT64.size());
    assertThat(struct.getType(0).getByteLength(struct)).isEqualTo(INT64.size());
  }

  @Test
  void isFixedLength() {
    var struct = struct()
        .int64()
        .build();

    assertThat(struct.getType(0).isFixedLength(struct)).isTrue();
    assertThat(struct.isFixedLength()).isTrue();
  }

  @Test
  void isFixedLengthArray() {
    var struct = struct()
        .int64Array(constant(5))
        .build();

    assertThat(struct.getType(0).isFixedLength(struct)).isTrue();
    assertThat(struct.isFixedLength()).isTrue();
  }

  @Test
  void allocate() {
    var struct = struct()
        .int64()
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0,0,0,0,0,0,0,0});
  }

  @Test
  void allocate_with_constant() {
    var struct = struct()
        .primitive().constant(5L).int64()
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0,0,0,0,0,0,0,5});
  }

  @Test
  void allocate_empty_array() {
    var struct = struct()
        .int64()
        .int64Array(0)
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0,0,0,0,0,0,0,0});
  }

  @Test
  void allocate_array_length_with_constant() {
    var struct = struct()
        .primitive().constant(5L).int64()
        .int64Array(0)
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {
        0,0,0,0,0,0,0,5,
        0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0
    });
  }

  @Test
  void allocate_array_length_and_array_with_constant() {
    var struct = struct()
        .primitive().constant(5L).int64()
        .primitive().constant(6L).lengthField(0).int64()
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {
        0,0,0,0,0,0,0,5,
        0,0,0,0,0,0,0,6,
        0,0,0,0,0,0,0,6,
        0,0,0,0,0,0,0,6,
        0,0,0,0,0,0,0,6,
        0,0,0,0,0,0,0,6
    });
  }

  @Test
  void setWrapper() {
    var struct = struct()
        .int64()
        .build();

    assertThatThrownBy(() -> ((Int64Type) struct.getType(0)).set(struct, Long.valueOf(2L)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("set(Pointer, Long) not supported for Int64Type. Use set(Pointer, long) instead.");
  }

  @Test
  void setInt64() {
    var struct = struct()
        .int64()
        .build();

    struct.setInt64(0, 2L);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0,0,0,0,0,0,0,2});
  }

  @Test
  void setInt64_position_negative() {
    var struct = struct()
        .int64()
        .build();

    assertThatThrownBy(() -> struct.setInt64(-1, 2L))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 1");
  }

  @Test
  void setInt64_position_greater_than_length() {
    var struct = struct()
        .int64()
        .build();

    assertThatThrownBy(() -> struct.setInt64(2, 2L))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 1");
  }

  @Test
  void setInt64_not_allocated() {
    var struct = struct()
        .allocated()
        .int64()
        .build();

    assertThatThrownBy(() -> struct.setInt64(0, 2L))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void setInt64_constant() {
    var struct = struct()
        .primitive().constant(5L).int64()
        .build();

    assertThatThrownBy(() -> struct.setInt64(0, 2L))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Int64Type at position 0 is constant index: 0 value: 2 constant: 5");
  }

  @Test
  void setArrayWrapper() {
    var struct = struct()
        .int64()
        .int64Array(0)
        .build();

    assertThatThrownBy(() -> ((Int64Type) struct.getType(1)).set(struct, 0, Long.valueOf(2L)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("set(Pointer, long, Long) not supported for Int64Type. Use set(Pointer, long, long) instead.");
  }

  @Test
  void setInt64Array() {
    var struct = struct()
        .int64()
        .int64Array(0)
        .int64()
        .fromBytes(new byte[] {
            0,0,0,0,0,0,0,1,
            0,0,0,0,0,0,0,2,
            0,0,0,0,0,0,0,3
        })
        .build();

    struct.setInt64(1, 0, 5L);

    assertThat(struct.getInt64(0)).isEqualTo(1L);
    assertThat(struct.getInt64(1, 0)).isEqualTo(5L);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {
        0,0,0,0,0,0,0,1,
        0,0,0,0,0,0,0,5,
        0,0,0,0,0,0,0,3
    });
  }

  @Test
  void setInt64Array_negative() {
    var struct = struct()
        .int64()
        .int64Array(0)
        .int64()
        .fromBytes(new byte[] {
            0,0,0,0,0,0,0,1,
            0,0,0,0,0,0,0,2,
            0,0,0,0,0,0,0,3
        })
        .build();

    assertThatThrownBy(() -> struct.setInt64(1, -1, 5L))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Int64Type at position 1 index: -1 length: 1");
  }

  @Test
  void setInt64Array_greater_than_length() {
    var struct = struct()
        .int64()
        .int64Array(0)
        .int64()
        .fromBytes(new byte[] {
            0,0,0,0,0,0,0,1,
            0,0,0,0,0,0,0,2,
            0,0,0,0,0,0,0,3
        })
        .build();

    assertThatThrownBy(() -> struct.setInt64(1, 2, 5L))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int64Type at position 1 index: 2 length: 1");

    assertThat(struct.getInt64(0)).isEqualTo(1L);
    assertThat(struct.getInt64(1, 0)).isEqualTo(2L);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {
        0,0,0,0,0,0,0,1,
        0,0,0,0,0,0,0,2,
        0,0,0,0,0,0,0,3
    });
  }

  @Test
  void setInt64Array_not_allocated() {
    var struct = struct()
        .allocated()
        .int64()
        .int64Array(0)
        .build();

    assertThatThrownBy(() -> struct.setInt64(1, 0, 2L))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void setInt64Array_index_0_not_array() {
    var struct = struct()
        .int64()
        .build();

    struct.setInt64(0, 0, 2L);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0,0,0,0,0,0,0,2});
  }

  @Test
  void setInt64Array_index_1_not_array() {
    var struct = struct()
        .int64()
        .build();

    assertThatThrownBy(() -> struct.setInt64(0, 1, 2L))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int64Type at position 0 index: 1 length: 1");
  }

  @Test
  void setInt64Array_constant_value_and_length() {
    var struct = struct()
        .primitive().constant(5L).lengthExpression(constant(5)).int64()
        .build();

    assertThatThrownBy(() -> struct.setInt64(0, 3, 2L))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Int64Type at position 0 is constant index: 3 value: 2 constant: 5");
  }

  @Test
  void setInt64Array_constant_value() {
    var struct = struct()
        .int64()
        .primitive().constant(5L).lengthField(0).int64()
        .fromBytes(new byte[] {
            0,0,0,0,0,0,0,2,
            0,0,0,0,0,0,0,5,
            0,0,0,0,0,0,0,5
        })
        .build();

    assertThatThrownBy(() -> struct.setInt64(1, 1, 2L))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Int64Type at position 1 is constant index: 1 value: 2 constant: 5");
  }

  @Test
  void setInt64Array_constant_value_same() {
    var struct = struct()
        .int64()
        .primitive().constant(5L).lengthField(0).int64()
        .fromBytes(new byte[] {
            0,0,0,0,0,0,0,2,
            0,0,0,0,0,0,0,5,
            0,0,0,0,0,0,0,5
        })
        .build();

    struct.setInt64(1, 1, 5L);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {
        0,0,0,0,0,0,0,2,
        0,0,0,0,0,0,0,5,
        0,0,0,0,0,0,0,5
    });
  }

  @Test
  @Disabled
  void setInt64Array_set_length_field_without_adding_to_array() {
    var struct = struct()
        .int64()
        .int64Array(0)
        .build();

    assertThatThrownBy(() -> struct.setInt64(0, 2L))
        .isInstanceOf(AssertionError.class)
        .hasMessage("Int64Type at position 0 is being set and will not match the array it is used as a length for.");
  }

  @Test
  void getWrapper() {
    var struct = struct()
        .int64()
        .build();

    assertThatThrownBy(() -> ((Int64Type) struct.getType(0)).get(struct))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("get(Pointer) not supported for Int64Type. Use getInt64(Pointer) instead.");
  }

  @Test
  void getInt64() {
    var struct = struct()
        .int64()
        .build();

    struct.setInt64(0, 2L);

    assertThat(struct.getInt64(0)).isEqualTo(2L);
  }

  @Test
  void getInt64_position_negative() {
    var struct = struct()
        .int64()
        .build();

    assertThatThrownBy(() -> struct.getInt64(-1))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 1");
  }

  @Test
  void getInt64_position_greater_than_length() {
    var struct = struct()
        .int64()
        .build();

    assertThatThrownBy(() -> struct.getInt64(2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 1");
  }

  @Test
  void getInt64Allocated() {
    var struct = struct()
        .int64()
        .build();

    assertThat(struct.getInt64(0)).isEqualTo(0L);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0,0,0,0,0,0,0,0});
  }

  @Test
  void getInt64NotAllocated() {
    var struct = struct()
        .allocated()
        .int64()
        .build();

    assertThatThrownBy(() -> struct.getInt64(0)).isInstanceOf(ArrayIndexOutOfBoundsException.class);
  }

  @Test
  void getInt64_constant() {
    var struct = struct()
        .primitive().constant(5L).int64()
        .build();

    assertThat(struct.getInt64(0)).isEqualTo(5L);
  }

  @Test
  void getInt64_index_0_not_array() {
    var struct = struct()
        .int64()
        .fromBytes(new byte[] {0,0,0,0,0,0,0,1})
        .build();

    assertThat(struct.getInt64(0, 0)).isEqualTo(1L);
  }

  @Test
  void getInt64_index_1_not_array() {
    var struct = struct()
        .int64()
        .fromBytes(new byte[] {0,0,0,0,0,0,0,1})
        .build();

    assertThatThrownBy(() -> struct.getInt64(0, 1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int64Type at position 0 index: 1 length: 1");
  }

  @Test
  void getArrayWrapper() {
    var struct = struct()
        .int64()
        .int64Array(0)
        .build();

    assertThatThrownBy(() -> ((Int64Type) struct.getType(1)).get(struct, 0))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("get(Pointer, long) not supported for Int64Type. Use getInt64(Pointer, long) instead.");
  }

  @Test
  void getInt64Array() {
    var struct = struct()
        .int64()
        .int64Array(0)
        .fromBytes(new byte[] {
            0,0,0,0,0,0,0,2,
            0,0,0,0,0,0,0,1,
            0,0,0,0,0,0,0,2
        })
        .build();

    assertThat(struct.getInt64(1, 0)).isEqualTo(1L);
    assertThat(struct.getInt64(1, 1)).isEqualTo(2L);
  }

  @Test
  void getInt64Array_negative() {
    var struct = struct()
        .int64()
        .int64Array(0)
        .build();

    assertThatThrownBy(() -> struct.getInt64(1, -1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int64Type at position 1 index: -1 length: 0");
  }

  @Test
  void getInt64Array_greater_than_length() {
    var struct = struct()
        .int64()
        .int64Array(0)
        .fromBytes(new byte[] {
            0,0,0,0,0,0,0,2,
            0,0,0,0,0,0,0,1,
            0,0,0,0,0,0,0,2
        })
        .build();

    assertThatThrownBy(() -> struct.getInt64(1, 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int64Type at position 1 index: 2 length: 2");
  }

  @Test
  void getInt64Array_not_allocated() {
    var struct = struct()
        .allocated()
        .int64()
        .int64Array(0)
        .build();

    assertThatThrownBy(() -> struct.getInt64(1, 0))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void getInt64Array_constant() {
    var struct = struct()
        .primitive().constant(5L).lengthExpression(constant(5)).int64()
        .build();

    assertThat(struct.getInt64(0, 3)).isEqualTo(5L);
  }

  @Test
  void addInt64Wrapper() {
    var struct = struct()
        .int64()
        .int64Array(0)
        .build();

    assertThatThrownBy(() -> ((Int64Type) struct.getType(1)).add(struct, Long.valueOf(1L)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("add(Pointer, Long) not supported for Int64Type. Use add(Pointer, long) instead.");
  }

  @Test
  void addInt64Wrapper_with_index() {
    var struct = struct()
        .int64()
        .int64Array(0)
        .build();

    assertThatThrownBy(() -> ((Int64Type) struct.getType(1)).add(struct, 0, Long.valueOf(1L)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("add(Pointer, long, Long) not supported for Int64Type. Use add(Pointer, long, long) instead.");
  }

  @Test
  void addInt64() {
    var struct = struct()
        .int64()
        .int64Array(0)
        .fromBytes(new byte[] {
            0,0,0,0,0,0,0,2,
            0,0,0,0,0,0,0,1,
            0,0,0,0,0,0,0,2
        })
        .build();

    struct.addInt64(1, 3L);
    struct.addInt64(1, 4L);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {
        0,0,0,0,0,0,0,4,
        0,0,0,0,0,0,0,1,
        0,0,0,0,0,0,0,2,
        0,0,0,0,0,0,0,3,
        0,0,0,0,0,0,0,4
    } );
  }

  @Test
  void addInt64_position_negative() {
    var struct = struct()
        .int64()
        .int64Array(0)
        .fromBytes(new byte[] {
            0,0,0,0,0,0,0,2,
            0,0,0,0,0,0,0,1,
            0,0,0,0,0,0,0,2
        })
        .build();

    assertThatThrownBy(() -> struct.addInt64(-1, 3L))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 2");
  }

  @Test
  void addInt64_position_greater_than_length() {
    var struct = struct()
        .int64()
        .int64Array(0)
        .fromBytes(new byte[] {
            0,0,0,0,0,0,0,2,
            0,0,0,0,0,0,0,1,
            0,0,0,0,0,0,0,2
        })
        .build();

    assertThatThrownBy(() -> struct.addInt64(3, 3L))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 3 out of bounds for length 2");
  }

  @Test
  void addInt64_not_allocated() {
    var struct = struct()
        .allocated()
        .int64()
        .int64Array(0)
        .build();
    assertThatThrownBy(() -> struct.addInt64(1, 3L))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void addInt64Array_constant() {
    var struct = struct()
        .primitive().constant(5L).lengthExpression(constant(5)).int64()
        .build();

    assertThatThrownBy(() -> struct.addInt64(0, 3L))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Int64Type at position 0 is constant index: 5 value: 3 constant: 5");
  }

  @Test
  void addInt64_not_array() {
    var struct = struct()
        .int64()
        .fromBytes(new byte[] {0,0,0,0,0,0,0,1})
        .build();
    assertThatThrownBy(() -> struct.addInt64(0, 3L))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int64Type cannot add to non-array type at position 0 index: 1 length: 1");
  }

  @Test
  void addInt64_with_index() {
    var struct = struct()
        .int64()
        .int64Array(0)
        .fromBytes(new byte[] {
            0,0,0,0,0,0,0,2,
            0,0,0,0,0,0,0,1,
            0,0,0,0,0,0,0,2
        })
        .build();

    struct.addInt64(1, 0, 3L);
    struct.addInt64(1, 1, 4L);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {
        0,0,0,0,0,0,0,4,
        0,0,0,0,0,0,0,3,
        0,0,0,0,0,0,0,4,
        0,0,0,0,0,0,0,1,
        0,0,0,0,0,0,0,2
    } );
  }

  @Test
  void addInt64_with_index_negative() {
    var struct = struct()
        .int64()
        .int64Array(0)
        .fromBytes(new byte[] {
            0,0,0,0,0,0,0,2,
            0,0,0,0,0,0,0,1,
            0,0,0,0,0,0,0,2
        })
        .build();

    assertThatThrownBy(() -> struct.addInt64(1, -1, 3L))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int64Type at position 1 index: -1 new length: 3");
  }

  @Test
  void addInt64_with_index_greater_than_length() {
    var struct = struct()
        .int64()
        .int64Array(0)
        .fromBytes(new byte[] {
            0,0,0,0,0,0,0,2,
            0,0,0,0,0,0,0,1,
            0,0,0,0,0,0,0,2
        })
        .build();

    assertThatThrownBy(() -> struct.addInt64(1, 3, 3L))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int64Type at position 1 index: 3 new length: 3");
  }

  @Test
  void addInt64_with_index_not_allocated() {
    var struct = struct()
        .allocated()
        .int64()
        .int64Array(0)
        .build();

    assertThatThrownBy(() -> struct.addInt64(1, 0, 3L))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void addInt64_with_index_0_not_array() {
    var struct = struct()
        .int64()
        .fromBytes(new byte[] {0,0,0,0,0,0,0,1})
        .build();

    assertThatThrownBy(() -> struct.addInt64(0, 0, 3L))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int64Type cannot add to non-array type at position 0 index: 0 length: 1");
  }

  @Test
  void addInt64_with_index_1_not_array() {
    var struct = struct()
        .int64()
        .fromBytes(new byte[] {0,0,0,0,0,0,0,1})
        .build();

    assertThatThrownBy(() -> struct.addInt64(0, 1, 3L))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int64Type cannot add to non-array type at position 0 index: 1 length: 1");
  }

  @Test
  void addInt64Array_with_index_constant() {
    var struct = struct()
        .primitive().constant(5L).lengthExpression(constant(5)).int64()
        .build();

    assertThatThrownBy(() -> struct.addInt64(0, 3, 3L))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Int64Type at position 0 is constant index: 3 value: 3 constant: 5");
  }

  @Test
  void removeInt64() {
    var struct = struct()
        .int64()
        .int64Array(0)
        .fromBytes(new byte[] {
            0,0,0,0,0,0,0,2,
            0,0,0,0,0,0,0,1,
            0,0,0,0,0,0,0,2
        })
        .build();

    struct.removeAll(1);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] { 0,0,0,0,0,0,0,0 } );
  }

  @Test
  void removeInt64_position_negative() {
    var struct = struct()
        .int64()
        .int64Array(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(-1))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 2");
  }

  @Test
  void removeInt64_position_greater_than_length() {
    var struct = struct()
        .int64()
        .int64Array(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 2");
  }

  @Test
  void removeInt64_not_allocated() {
    var struct = struct()
        .allocated()
        .int64()
        .int64Array(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void removeInt64_not_array() {
    var struct = struct()
        .int64()
        .build();

    assertThatThrownBy(() -> struct.removeAll(0))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int64Type cannot remove from non-array type at position 0");
  }

  @Test
  void removeInt64_with_index_0() {
    var struct = struct()
        .int64()
        .int64Array(0)
        .build();

    struct.addInt64(1, 1L);
    struct.addInt64(1, 2L);
    struct.remove(1, 0);

    assertThat(((Int64Type) struct.getType(1)).getInt64(struct, 0)).isEqualTo(2L);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0,0,0,0,0,0,0,1, 0,0,0,0,0,0,0,2} );
  }

  @Test
  void removeInt64_with_index_1() {
    var struct = struct()
        .int64()
        .int64Array(0)
        .build();
    struct.addInt64(1, 1L);
    struct.addInt64(1, 2L);

    struct.remove(1, 1);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0,0,0,0,0,0,0,1, 0,0,0,0,0,0,0,1} );
  }

  @Test
  void removeInt64_with_index_negative() {
    var struct = struct()
        .int64()
        .int64Array(0)
        .fromBytes(new byte[] {
            0,0,0,0,0,0,0,2,
            0,0,0,0,0,0,0,1,
            0,0,0,0,0,0,0,2
        })
        .build();

    assertThatThrownBy(() -> struct.remove(1, -1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int64Type at position 1 index: -1 length: 2");
  }

  @Test
  void removeInt64_with_index_greater_than_length() {
    var struct = struct()
        .int64()
        .int64Array(0)
        .fromBytes(new byte[] {
            0,0,0,0,0,0,0,2,
            0,0,0,0,0,0,0,1,
            0,0,0,0,0,0,0,2
        })
        .build();

    assertThatThrownBy(() -> struct.remove(1, 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int64Type at position 1 index: 2 length: 2");
  }

  @Test
  void removeInt64_with_index_not_array() {
    var struct = struct()
        .int64()
        .fromBytes(new byte[] {0,0,0,0,0,0,0,1})
        .build();

    assertThatThrownBy(() -> struct.remove(0, 0))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int64Type cannot remove from non-array type at position 0");
  }

  @Test
  void removeInt64Array_fixed_length() {
    var struct = struct()
        .primitive().constant(5L).lengthExpression(constant(5)).int64()
        .build();

    assertThatThrownBy(() -> struct.removeAll(0))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Cannot remove fixed length array Int64Type at position 0");
  }

  @Test
  void removeInt64Array_fixed_length_with_index() {
    var struct = struct()
        .primitive().constant(5L).lengthExpression(constant(5)).int64()
        .build();

    assertThatThrownBy(() -> struct.remove(0, 3))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Cannot remove element from fixed length array Int64Type at position 0 index: 3");
  }
}
