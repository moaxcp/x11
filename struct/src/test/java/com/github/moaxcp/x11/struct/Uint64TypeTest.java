package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static com.github.moaxcp.x11.struct.Builders.struct;
import static com.github.moaxcp.x11.struct.ByteLengthListener.align;
import static com.github.moaxcp.x11.struct.Expression.constant;
import static com.github.moaxcp.x11.struct.Expression.valueOf;
import static com.github.moaxcp.x11.struct.Primitive.UINT64;
import static com.github.moaxcp.x11.struct.PrimitiveBuilder.primitive;
import static com.github.moaxcp.x11.struct.Uint64Type.uint64Type;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Uint64TypeTest {

  @Test
  void constructor() {
    var type = uint64Type();
    assertThat(type.getPosition()).isEqualTo(-1);
  }

  @Test
  void constructorPosition() {
    var type = uint64Type(15);
    assertThat(type.getPosition()).isEqualTo(15);
  }

  @Test
  void constructorEverything() {
    var struct = struct()
        .uint64()
        .primitive().constant(BigInteger.valueOf(5)).lengthExpression(valueOf(0)).uint64()
        .align(2)
        .build();

    assertThat(struct.getByteLength()).isEqualTo(UINT64.size() + 2);
    assertThat(struct.<Uint64Type>getType(1))
        .isEqualTo(primitive().position(1).byteLengthListener(align(2)).constant(BigInteger.valueOf(5)).lengthExpression(valueOf(0)).uint64());
  }

  @Test
  void copy() {
    var type = uint64Type();
    var copy = type.copy(15);
    assertThat(copy.getPosition()).isEqualTo(15);
  }

  @Test
  void getByteLength() {
    var struct = struct()
        .uint64()
        .build();

    assertThat(struct.getByteLength()).isEqualTo(UINT64.size());
    assertThat(struct.getType(0).getByteLength(struct)).isEqualTo(UINT64.size());
  }

  @Test
  void isFixedLength() {
    var struct = struct()
        .uint64()
        .build();

    assertThat(struct.getType(0).isFixedLength(struct)).isTrue();
    assertThat(struct.isFixedLength()).isTrue();
  }

  @Test
  void isFixedLengthArray() {
    var struct = struct()
        .uint64Array(constant(5))
        .build();

    assertThat(struct.getType(0).isFixedLength(struct)).isTrue();
    assertThat(struct.isFixedLength()).isTrue();
  }

  @Test
  void allocate() {
    var struct = struct()
        .uint64()
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0,0,0,0,0,0,0,0});
  }

  @Test
  void allocate_with_constant() {
    var struct = struct()
        .primitive().constant(BigInteger.valueOf(5)).uint64()
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0,0,0,0,0,0,0,5});
  }

  @Test
  void allocate_empty_array() {
    var struct = struct()
        .uint64()
        .uint64Array(0)
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0,0,0,0,0,0,0,0});
  }

  @Test
  void allocate_array_length_with_constant() {
    var struct = struct()
        .primitive().constant(BigInteger.valueOf(5)).uint64()
        .uint64Array(0)
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
        .primitive().constant(BigInteger.valueOf(5)).uint64()
        .primitive().constant(BigInteger.valueOf(6)).lengthField(0).uint64()
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
  void setUint64() {
    var struct = struct()
        .uint64()
        .build();

    struct.setUint64(0, BigInteger.valueOf(2));

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0,0,0,0,0,0,0,2});
  }

  @Test
  void setUint64_position_negative() {
    var struct = struct()
        .uint64()
        .build();

    assertThatThrownBy(() -> struct.setUint64(-1, BigInteger.valueOf(2)))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 1");
  }

  @Test
  void setUint64_position_greater_than_length() {
    var struct = struct()
        .uint64()
        .build();

    assertThatThrownBy(() -> struct.setUint64(2, BigInteger.valueOf(2)))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 1");
  }

  @Test
  void setUint64_not_allocated() {
    var struct = struct()
        .allocated()
        .uint64()
        .build();

    assertThatThrownBy(() -> struct.setUint64(0, BigInteger.valueOf(2)))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void setUint64_constant() {
    var struct = struct()
        .primitive().constant(BigInteger.valueOf(5)).uint64()
        .build();

    assertThatThrownBy(() -> struct.setUint64(0, BigInteger.valueOf(2)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Uint64Type at position 0 is constant index: 0 value: 2 constant: 5");
  }

  @Test
  void setUint64Array() {
    var struct = struct()
        .uint64()
        .uint64Array(0)
        .uint64()
        .fromBytes(new byte[] {
            0,0,0,0,0,0,0,1,
            0,0,0,0,0,0,0,2,
            0,0,0,0,0,0,0,3
        })
        .build();

    struct.setUint64(1, 0, BigInteger.valueOf(5));

    assertThat(struct.getUint64(0)).isEqualTo(BigInteger.ONE);
    assertThat(struct.getUint64(1, 0)).isEqualTo(BigInteger.valueOf(5));
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {
        0,0,0,0,0,0,0,1,
        0,0,0,0,0,0,0,5,
        0,0,0,0,0,0,0,3
    });
  }

  @Test
  void setUint64Array_negative() {
    var struct = struct()
        .uint64()
        .uint64Array(0)
        .uint64()
        .fromBytes(new byte[] {
            0,0,0,0,0,0,0,1,
            0,0,0,0,0,0,0,2,
            0,0,0,0,0,0,0,3
        })
        .build();

    assertThatThrownBy(() -> struct.setUint64(1, -1, BigInteger.valueOf(5)))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Uint64Type at position 1 index: -1 length: 1");
  }

  @Test
  void setUint64Array_greater_than_length() {
    var struct = struct()
        .uint64()
        .uint64Array(0)
        .uint64()
        .fromBytes(new byte[] {
            0,0,0,0,0,0,0,1,
            0,0,0,0,0,0,0,2,
            0,0,0,0,0,0,0,3
        })
        .build();

    assertThatThrownBy(() -> struct.setUint64(1, 2, BigInteger.valueOf(5)))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint64Type at position 1 index: 2 length: 1");

    assertThat(struct.getUint64(0)).isEqualTo(BigInteger.ONE);
    assertThat(struct.getUint64(1, 0)).isEqualTo(BigInteger.valueOf(2));
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {
        0,0,0,0,0,0,0,1,
        0,0,0,0,0,0,0,2,
        0,0,0,0,0,0,0,3
    });
  }

  @Test
  void setUint64Array_not_allocated() {
    var struct = struct()
        .allocated()
        .uint64()
        .uint64Array(0)
        .build();

    assertThatThrownBy(() -> struct.setUint64(1, 0, BigInteger.valueOf(2)))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("arraycopy: last source index 8 out of bounds for byte[0]");
  }

  @Test
  void setUint64Array_index_0_not_array() {
    var struct = struct()
        .uint64()
        .build();

    struct.setUint64(0, 0, BigInteger.valueOf(2));

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0,0,0,0,0,0,0,2});
  }

  @Test
  void setUint64Array_index_1_not_array() {
    var struct = struct()
        .uint64()
        .build();

    assertThatThrownBy(() -> struct.setUint64(0, 1, BigInteger.valueOf(2)))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint64Type at position 0 index: 1 length: 1");
  }

  @Test
  void setUint64Array_constant_value_and_length() {
    var struct = struct()
        .primitive().constant(BigInteger.valueOf(5)).lengthExpression(constant(5)).uint64()
        .build();

    assertThatThrownBy(() -> struct.setUint64(0, 3, BigInteger.valueOf(2)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Uint64Type at position 0 is constant index: 3 value: 2 constant: 5");
  }

  @Test
  void setUint64Array_constant_value() {
    var struct = struct()
        .uint64()
        .primitive().constant(BigInteger.valueOf(5)).lengthField(0).uint64()
        .fromBytes(new byte[] {
            0,0,0,0,0,0,0,2,
            0,0,0,0,0,0,0,5,
            0,0,0,0,0,0,0,5
        })
        .build();

    assertThatThrownBy(() -> struct.setUint64(1, 1, BigInteger.valueOf(2)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Uint64Type at position 1 is constant index: 1 value: 2 constant: 5");
  }

  @Test
  void setUint64Array_constant_value_same() {
    var struct = struct()
        .uint64()
        .primitive().constant(BigInteger.valueOf(5)).lengthField(0).uint64()
        .fromBytes(new byte[] {
            0,0,0,0,0,0,0,2,
            0,0,0,0,0,0,0,5,
            0,0,0,0,0,0,0,5
        })
        .build();

    struct.setUint64(1, 1, BigInteger.valueOf(5));

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {
        0,0,0,0,0,0,0,2,
        0,0,0,0,0,0,0,5,
        0,0,0,0,0,0,0,5
    });
  }

  @Test
  @Disabled
  void setUint64Array_set_length_field_without_adding_to_array() {
    var struct = struct()
        .uint64()
        .uint64Array(0)
        .build();

    assertThatThrownBy(() -> struct.setUint64(0, BigInteger.valueOf(2)))
        .isInstanceOf(AssertionError.class)
        .hasMessage("Int16Type at position 0 is being set and will not match the array it is used as a length for.");
  }

  @Test
  void getUint64() {
    var struct = struct()
        .uint64()
        .build();

    struct.setUint64(0, BigInteger.valueOf(2));

    assertThat(struct.getUint64(0)).isEqualTo(BigInteger.valueOf(2));
  }

  @Test
  void getUint64_position_negative() {
    var struct = struct()
        .uint64()
        .build();

    assertThatThrownBy(() -> struct.getUint64(-1))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 1");
  }

  @Test
  void getUint64_position_greater_than_length() {
    var struct = struct()
        .uint64()
        .build();

    assertThatThrownBy(() -> struct.getUint64(2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 1");
  }

  @Test
  void getUint64Allocated() {
    var struct = struct()
        .uint64()
        .build();

    assertThat(struct.getUint64(0)).isEqualTo(BigInteger.ZERO);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0,0,0,0,0,0,0,0});
  }

  @Test
  void getUint64NotAllocated() {
    var struct = struct()
        .allocated()
        .uint64()
        .build();

    assertThatThrownBy(() -> struct.getUint64(0)).isInstanceOf(ArrayIndexOutOfBoundsException.class);
  }

  @Test
  void getUint64_constant() {
    var struct = struct()
        .primitive().constant(BigInteger.valueOf(5)).uint64()
        .build();

    assertThat(struct.getUint64(0)).isEqualTo(BigInteger.valueOf(5));
  }

  @Test
  void getUint64_index_0_not_array() {
    var struct = struct()
        .uint64()
        .fromBytes(new byte[] {0,0,0,0,0,0,0,1})
        .build();

    assertThat(struct.getUint64(0, 0)).isEqualTo(BigInteger.ONE);
  }

  @Test
  void getUint64_index_1_not_array() {
    var struct = struct()
        .uint64()
        .fromBytes(new byte[] {0,0,0,0,0,0,0,1})
        .build();

    assertThatThrownBy(() -> struct.getUint64(0, 1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint64Type at position 0 index: 1 length: 1");
  }

  @Test
  void getUint64Array() {
    var struct = struct()
        .uint64()
        .uint64Array(0)
        .fromBytes(new byte[] {
            0,0,0,0,0,0,0,2,
            0,0,0,0,0,0,0,1,
            0,0,0,0,0,0,0,2
        })
        .build();

    assertThat(struct.getUint64(1, 0)).isEqualTo(BigInteger.ONE);
    assertThat(struct.getUint64(1, 1)).isEqualTo(BigInteger.valueOf(2));
  }

  @Test
  void getUint64Array_negative() {
    var struct = struct()
        .uint64()
        .uint64Array(0)
        .build();

    assertThatThrownBy(() -> struct.getUint64(1, -1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint64Type at position 1 index: -1 length: 0");
  }

  @Test
  void getUint64Array_greater_than_length() {
    var struct = struct()
        .uint64()
        .uint64Array(0)
        .fromBytes(new byte[] {
            0,0,0,0,0,0,0,2,
            0,0,0,0,0,0,0,1,
            0,0,0,0,0,0,0,2
        })
        .build();

    assertThatThrownBy(() -> struct.getUint64(1, 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint64Type at position 1 index: 2 length: 2");
  }

  @Test
  void getUint64Array_not_allocated() {
    var struct = struct()
    .allocated()
    .uint64()
    .uint64Array(0)
    .build();

    assertThatThrownBy(() -> struct.getUint64(1, 0))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("arraycopy: last source index 8 out of bounds for byte[0]");
  }

  @Test
  void getUint64Array_constant() {
    var struct = struct()
        .primitive().constant(BigInteger.valueOf(5)).lengthExpression(constant(5)).uint64()
        .build();

    assertThat(struct.getUint64(0, 3)).isEqualTo(BigInteger.valueOf(5));
  }

  @Test
  void addUint64() {
    var struct = struct()
        .uint64()
        .uint64Array(0)
        .fromBytes(new byte[] {
            0,0,0,0,0,0,0,2,
            0,0,0,0,0,0,0,1,
            0,0,0,0,0,0,0,2
        })
        .build();

    struct.addUint64(1, BigInteger.valueOf(3));
    struct.addUint64(1, BigInteger.valueOf(4));

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {
        0,0,0,0,0,0,0,4,
        0,0,0,0,0,0,0,1,
        0,0,0,0,0,0,0,2,
        0,0,0,0,0,0,0,3,
        0,0,0,0,0,0,0,4
    } );
  }

  @Test
  void addUint64_position_negative() {
    var struct = struct()
        .uint64()
        .uint64Array(0)
        .fromBytes(new byte[] {
            0,0,0,0,0,0,0,2,
            0,0,0,0,0,0,0,1,
            0,0,0,0,0,0,0,2
        })
        .build();

    assertThatThrownBy(() -> struct.addUint64(-1, BigInteger.valueOf(3)))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 2");
  }

  @Test
  void addUint64_position_greater_than_length() {
    var struct = struct()
        .uint64()
        .uint64Array(0)
        .fromBytes(new byte[] {
            0,0,0,0,0,0,0,2,
            0,0,0,0,0,0,0,1,
            0,0,0,0,0,0,0,2
        })
        .build();

    assertThatThrownBy(() -> struct.addUint64(3, BigInteger.valueOf(3)))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 3 out of bounds for length 2");
  }

  @Test
  void addUint64_not_allocated() {
    var struct = struct()
        .allocated()
        .uint64()
        .uint64Array(0)
        .build();
    assertThatThrownBy(() -> struct.addUint64(1, BigInteger.valueOf(3)))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("arraycopy: last source index 8 out of bounds for byte[0]");
  }

  @Test
  void addUint64Array_constant() {
    var struct = struct()
        .primitive().constant(BigInteger.valueOf(5)).lengthExpression(constant(5)).uint64()
        .build();

    assertThatThrownBy(() -> struct.addUint64(0, BigInteger.valueOf(3)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Uint64Type at position 0 is constant index: 5 value: 3 constant: 5");
  }

  @Test
  void addUint64_not_array() {
    var struct = struct()
        .uint64()
        .fromBytes(new byte[] {0,0,0,0,0,0,0,1})
        .build();
    assertThatThrownBy(() -> struct.addUint64(0, BigInteger.valueOf(3)))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint64Type cannot add to non-array type at position 0 index: 1 length: 1");
  }

  @Test
  void addUint64_with_index() {
    var struct = struct()
        .uint64()
        .uint64Array(0)
        .fromBytes(new byte[] {
            0,0,0,0,0,0,0,2,
            0,0,0,0,0,0,0,1,
            0,0,0,0,0,0,0,2
        })
        .build();

    struct.addUint64(1, 0, BigInteger.valueOf(3));
    struct.addUint64(1, 1, BigInteger.valueOf(4));

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {
        0,0,0,0,0,0,0,4,
        0,0,0,0,0,0,0,3,
        0,0,0,0,0,0,0,4,
        0,0,0,0,0,0,0,1,
        0,0,0,0,0,0,0,2
    } );
  }

  @Test
  void addUint64_with_index_negative() {
    var struct = struct()
        .uint64()
        .uint64Array(0)
        .fromBytes(new byte[] {
            0,0,0,0,0,0,0,2,
            0,0,0,0,0,0,0,1,
            0,0,0,0,0,0,0,2
        })
        .build();

    assertThatThrownBy(() -> struct.addUint64(1, -1, BigInteger.valueOf(3)))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint64Type at position 1 index: -1 new length: 3");
  }

  @Test
  void addUint64_with_index_greater_than_length() {
    var struct = struct()
        .uint64()
        .uint64Array(0)
        .fromBytes(new byte[] {
            0,0,0,0,0,0,0,2,
            0,0,0,0,0,0,0,1,
            0,0,0,0,0,0,0,2
        })
        .build();

    assertThatThrownBy(() -> struct.addUint64(1, 3, BigInteger.valueOf(3)))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint64Type at position 1 index: 3 new length: 3");
  }

  @Test
  void addUint64_with_index_not_allocated() {
    var struct = struct()
        .allocated()
        .uint64()
        .uint64Array(0)
        .build();

    assertThatThrownBy(() -> struct.addUint64(1, 0, BigInteger.valueOf(3)))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("arraycopy: last source index 8 out of bounds for byte[0]");
  }

  @Test
  void addUint64_with_index_0_not_array() {
    var struct = struct()
        .uint64()
        .fromBytes(new byte[] {0,0,0,0,0,0,0,1})
        .build();

    assertThatThrownBy(() -> struct.addUint64(0, 0, BigInteger.valueOf(3)))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint64Type cannot add to non-array type at position 0 index: 0 length: 1");
  }

  @Test
  void addUint64_with_index_1_not_array() {
    var struct = struct()
        .uint64()
        .fromBytes(new byte[] {0,0,0,0,0,0,0,1})
        .build();

    assertThatThrownBy(() -> struct.addUint64(0, 1, BigInteger.valueOf(3)))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint64Type cannot add to non-array type at position 0 index: 1 length: 1");
  }

  @Test
  void addUint64Array_with_index_constant() {
    var struct = struct()
        .primitive().constant(BigInteger.valueOf(5)).lengthExpression(constant(5)).uint64()
        .build();

    assertThatThrownBy(() -> struct.addUint64(0, 3, BigInteger.valueOf(3)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Uint64Type at position 0 is constant index: 3 value: 3 constant: 5");
  }

  @Test
  void removeUint64() {
    var struct = struct()
        .uint64()
        .uint64Array(0)
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
  void removeUint64_position_negative() {
    var struct = struct()
        .uint64()
        .uint64Array(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(-1))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 2");
  }

  @Test
  void removeUint64_position_greater_than_length() {
    var struct = struct()
        .uint64()
        .uint64Array(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 2");
  }

  @Test
  void removeUint64_not_allocated() {
    var struct = struct()
        .allocated()
        .uint64()
        .uint64Array(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("arraycopy: last source index 8 out of bounds for byte[0]");
  }

  @Test
  void removeUint64_not_array() {
    var struct = struct()
        .uint64()
        .build();

    assertThatThrownBy(() -> struct.removeAll(0))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint64Type cannot remove from non-array type at position 0");
  }

  @Test
  void removeUint64_with_index_0() {
    var struct = struct()
        .uint64()
        .uint64Array(0)
        .build();

    struct.addUint64(1, BigInteger.ONE);
    struct.addUint64(1, BigInteger.valueOf(2));
    struct.remove(1, 0);

    assertThat(((Uint64Type) struct.getType(1)).getUint64(struct, 0)).isEqualTo(BigInteger.valueOf(2));

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0,0,0,0,0,0,0,1, 0,0,0,0,0,0,0,2} );
  }

  @Test
  void removeUint64_with_index_1() {
    var struct = struct()
        .uint64()
        .uint64Array(0)
        .build();
    struct.addUint64(1, BigInteger.ONE);
    struct.addUint64(1, BigInteger.valueOf(2));

    struct.remove(1, 1);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0,0,0,0,0,0,0,1, 0,0,0,0,0,0,0,1} );
  }

  @Test
  void removeUint64_with_index_negative() {
    var struct = struct()
        .uint64()
        .uint64Array(0)
        .fromBytes(new byte[] {
            0,0,0,0,0,0,0,2,
            0,0,0,0,0,0,0,1,
            0,0,0,0,0,0,0,2
        })
        .build();

    assertThatThrownBy(() -> struct.remove(1, -1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint64Type at position 1 index: -1 length: 2");
  }

  @Test
  void removeUint64_with_index_greater_than_length() {
    var struct = struct()
        .uint64()
        .uint64Array(0)
        .fromBytes(new byte[] {
            0,0,0,0,0,0,0,2,
            0,0,0,0,0,0,0,1,
            0,0,0,0,0,0,0,2
        })
        .build();

    assertThatThrownBy(() -> struct.remove(1, 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint64Type at position 1 index: 2 length: 2");
  }

  @Test
  void removeUint64_with_index_not_array() {
    var struct = struct()
        .uint64()
        .fromBytes(new byte[] {0,0,0,0,0,0,0,1})
        .build();

    assertThatThrownBy(() -> struct.remove(0, 0))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint64Type cannot remove from non-array type at position 0");
  }

  @Test
  void removeUint64Array_fixed_length() {
    var struct = struct()
        .primitive().constant(BigInteger.valueOf(5)).lengthExpression(constant(5)).uint64()
        .build();

    assertThatThrownBy(() -> struct.removeAll(0))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Cannot remove fixed length array Uint64Type at position 0");
  }

  @Test
  void removeUint64Array_fixed_length_with_index() {
    var struct = struct()
        .primitive().constant(BigInteger.valueOf(5)).lengthExpression(constant(5)).uint64()
        .build();

    assertThatThrownBy(() -> struct.remove(0, 3))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Cannot remove element from fixed length array Uint64Type at position 0 index: 3");
  }
}
