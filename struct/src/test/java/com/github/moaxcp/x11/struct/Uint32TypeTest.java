package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11.struct.Builders.struct;
import static com.github.moaxcp.x11.struct.ByteLengthListener.align;
import static com.github.moaxcp.x11.struct.Expression.constant;
import static com.github.moaxcp.x11.struct.Expression.valueOf;
import static com.github.moaxcp.x11.struct.Primitive.UINT32;
import static com.github.moaxcp.x11.struct.PrimitiveBuilder.primitive;
import static com.github.moaxcp.x11.struct.Uint32Type.uint32Type;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Uint32TypeTest {

  @Test
  void constructor() {
    var type = uint32Type();
    assertThat(type.getPosition()).isEqualTo(-1);
  }

  @Test
  void constructorPosition() {
    var type = uint32Type(15);
    assertThat(type.getPosition()).isEqualTo(15);
  }

  @Test
  void constructorEverything() {
    var struct = struct()
        .uint32()
        .primitive().constant(5L).lengthExpression(valueOf(0)).uint32()
        .align(2)
        .build();

    assertThat(struct.getByteLength()).isEqualTo(UINT32.size() + 2);
    assertThat(struct.<Uint32Type>getType(1))
        .isEqualTo(primitive().position(1).byteLengthListener(align(2)).constant(5L).lengthExpression(valueOf(0)).uint32());
  }

  @Test
  void copy() {
    var type = uint32Type();
    var copy = type.copy(15);
    assertThat(copy.getPosition()).isEqualTo(15);
  }

  @Test
  void getByteLength() {
    var struct = struct()
        .uint32()
        .build();

    assertThat(struct.getByteLength()).isEqualTo(UINT32.size());
    assertThat(struct.getType(0).getByteLength(struct)).isEqualTo(UINT32.size());
  }

  @Test
  void isFixedLength() {
    var struct = struct()
        .uint32()
        .build();

    assertThat(struct.getType(0).isFixedLength(struct)).isTrue();
    assertThat(struct.isFixedLength()).isTrue();
  }

  @Test
  void isFixedLengthArray() {
    var struct = struct()
        .uint32Array(constant(5))
        .build();

    assertThat(struct.getType(0).isFixedLength(struct)).isTrue();
    assertThat(struct.isFixedLength()).isTrue();
  }

  @Test
  void allocate() {
    var struct = struct()
        .uint32()
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 0, 0, 0});
  }

  @Test
  void allocate_with_constant() {
    var struct = struct()
        .primitive().constant(5L).uint32()
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 0, 0, 5});
  }

  @Test
  void allocate_empty_array() {
    var struct = struct()
        .uint32()
        .uint32Array(0)
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 0, 0, 0});
  }

  @Test
  void allocate_array_length_with_constant() {
    var struct = struct()
        .primitive().constant(5L).uint32()
        .uint32Array(0)
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
  void setWrapper() {
    var struct = struct()
        .uint32()
        .build();

    assertThatThrownBy(() -> ((Uint32Type) struct.getType(0)).set(struct, Long.valueOf(2)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("set(Pointer, Long) not supported for Uint32Type. Use set(Pointer, long) instead.");
  }

  @Test
  void setUint32() {
    var struct = struct()
        .uint32()
        .build();

    struct.setUint32(0, 2L);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 0, 0, 2});
  }

  @Test
  void setUint32_position_negative() {
    var struct = struct()
        .uint32()
        .build();

    assertThatThrownBy(() -> struct.setUint32(-1, 2L))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 1");
  }

  @Test
  void setUint32_position_greater_than_length() {
    var struct = struct()
        .uint32()
        .build();

    assertThatThrownBy(() -> struct.setUint32(2, 2L))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 1");
  }

  @Test
  void setUint32_not_allocated() {
    var struct = struct()
        .allocated()
        .uint32()
        .build();

    assertThatThrownBy(() -> struct.setUint32(0, 2L))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void setUint32_constant() {
    var struct = struct()
        .primitive().constant(5L).uint32()
        .build();

    assertThatThrownBy(() -> struct.setUint32(0, 2L))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Uint32Type at position 0 is constant index: 0 value: 2 constant: 5");
  }

  @Test
  void setArrayWrapper() {
    var struct = struct()
        .uint32()
        .uint32Array(0)
        .build();

    assertThatThrownBy(() -> ((Uint32Type) struct.getType(1)).set(struct, 0, Long.valueOf(2)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("set(Pointer, long, Long) not supported for Uint32Type. Use set(Pointer, long, long) instead.");
  }

  @Test
  void setUint32Array() {
    var struct = struct()
        .uint32()
        .uint32Array(0)
        .uint32()
        .fromBytes(new byte[] {0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 3})
        .build();

    struct.setUint32(1, 0, 5L);

    assertThat(struct.getUint32(0)).isEqualTo(1L);
    assertThat(struct.getUint32(1, 0)).isEqualTo(5L);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 0, 0, 1, 0, 0, 0, 5, 0, 0, 0, 3});
  }

  @Test
  void setUint32Array_negative() {
    var struct = struct()
        .uint32()
        .uint32Array(0)
        .uint32()
        .fromBytes(new byte[] {0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 3})
        .build();

    assertThatThrownBy(() -> struct.setUint32(1, -1, 5L))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Uint32Type at position 1 index: -1 length: 1");
  }

  @Test
  void setUint32Array_greater_than_length() {
    var struct = struct()
        .uint32()
        .uint32Array(0)
        .uint32()
        .fromBytes(new byte[] {0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 3})
        .build();

    assertThatThrownBy(() -> struct.setUint32(1, 2, 5L))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint32Type at position 1 index: 2 length: 1");

    assertThat(struct.getUint32(0)).isEqualTo(1L);
    assertThat(struct.getUint32(1, 0)).isEqualTo(2L);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 3});
  }

  @Test
  void setUint32Array_not_allocated() {
    var struct = struct()
        .allocated()
        .uint32()
        .uint32Array(0)
        .build();

    assertThatThrownBy(() -> struct.setUint32(1, 0, 2L))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void setUint32Array_index_0_not_array() {
    var struct = struct()
        .uint32()
        .build();

    struct.setUint32(0, 0, 2L);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 0, 0, 2});
  }

  @Test
  void setUint32Array_index_1_not_array() {
    var struct = struct()
        .uint32()
        .build();

    assertThatThrownBy(() -> struct.setUint32(0, 1, 2L))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint32Type at position 0 index: 1 length: 1");
  }

  @Test
  void setUint32Array_constant_value_and_length() {
    var struct = struct()
        .primitive().constant(5L).lengthExpression(constant(5)).uint32()
        .build();

    assertThatThrownBy(() -> struct.setUint32(0, 3, 2L))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Uint32Type at position 0 is constant index: 3 value: 2 constant: 5");
  }

  @Test
  void setUint32Array_constant_value() {
    var struct = struct()
        .uint32()
        .primitive().constant(5L).lengthField(0).uint32()
        .fromBytes(new byte[] {0, 0, 0, 2, 0, 0, 0, 5, 0, 0, 0, 5})
        .build();

    assertThatThrownBy(() -> struct.setUint32(1, 1, 2L))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Uint32Type at position 1 is constant index: 1 value: 2 constant: 5");
  }

  @Test
  void setUint32Array_constant_value_same() {
    var struct = struct()
        .uint32()
        .primitive().constant(5L).lengthField(0).uint32()
        .fromBytes(new byte[] {0, 0, 0, 2, 0, 0, 0, 5, 0, 0, 0, 5})
        .build();

    struct.setUint32(1, 1, 5L);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 0, 0, 2, 0, 0, 0, 5, 0, 0, 0, 5});
  }

  @Test
  @Disabled
  void setUint32Array_set_length_field_without_adding_to_array() {
    var struct = struct()
        .uint32()
        .uint32Array(0)
        .build();

    assertThatThrownBy(() -> struct.setUint32(0, 2L))
        .isInstanceOf(AssertionError.class)
        .hasMessage("Uint32Type at position 0 is being set and will not match the array it is used as a length for.");
  }

  @Test
  void getWrapper() {
    var struct = struct()
        .uint32()
        .build();

    assertThatThrownBy(() -> ((Uint32Type) struct.getType(0)).get(struct))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("get(Pointer) not supported for Uint32Type. Use getUint32(Pointer) instead.");
  }

  @Test
  void getUint32() {
    var struct = struct()
        .uint32()
        .build();

    struct.setUint32(0, 2L);

    assertThat(struct.getUint32(0)).isEqualTo(2L);
  }

  @Test
  void getUint32_position_negative() {
    var struct = struct()
        .uint32()
        .build();

    assertThatThrownBy(() -> struct.getUint32(-1))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 1");
  }

  @Test
  void getUint32_position_greater_than_length() {
    var struct = struct()
        .uint32()
        .build();

    assertThatThrownBy(() -> struct.getUint32(2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 1");
  }

  @Test
  void getUint32Allocated() {
    var struct = struct()
        .uint32()
        .build();

    assertThat(struct.getUint32(0)).isEqualTo(0L);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 0, 0, 0});
  }

  @Test
  void getUint32NotAllocated() {
    var struct = struct()
        .allocated()
        .uint32()
        .build();

    assertThatThrownBy(() -> struct.getUint32(0)).isInstanceOf(ArrayIndexOutOfBoundsException.class);
  }

  @Test
  void getUint32_constant() {
    var struct = struct()
        .primitive().constant(5L).uint32()
        .build();

    assertThat(struct.getUint32(0)).isEqualTo(5L);
  }

  @Test
  void getUint32_index_0_not_array() {
    var struct = struct()
        .uint32()
        .fromBytes(new byte[] {0, 0, 0, 1})
        .build();

    assertThat(struct.getUint32(0, 0)).isEqualTo(1L);
  }

  @Test
  void getUint32_index_1_not_array() {
    var struct = struct()
        .uint32()
        .fromBytes(new byte[] {0, 0, 0, 1})
        .build();

    assertThatThrownBy(() -> struct.getUint32(0, 1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint32Type at position 0 index: 1 length: 1");
  }

  @Test
  void getArrayWrapper() {
    var struct = struct()
        .uint32()
        .uint32Array(0)
        .build();

    assertThatThrownBy(() -> ((Uint32Type) struct.getType(1)).get(struct, 0))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("get(Pointer, long) not supported for Uint32Type. Use getUint32(Pointer, long) instead.");
  }

  @Test
  void getUint32Array() {
    var struct = struct()
        .uint32()
        .uint32Array(0)
        .fromBytes(new byte[] {0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 2})
        .build();

    assertThat(struct.getUint32(1, 0)).isEqualTo(1L);
    assertThat(struct.getUint32(1, 1)).isEqualTo(2L);
  }

  @Test
  void getUint32Array_negative() {
    var struct = struct()
        .uint32()
        .uint32Array(0)
        .build();

    assertThatThrownBy(() -> struct.getUint32(1, -1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint32Type at position 1 index: -1 length: 0");
  }

  @Test
  void getUint32Array_greater_than_length() {
    var struct = struct()
        .uint32()
        .uint32Array(0)
        .fromBytes(new byte[] {0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 2})
        .build();

    assertThatThrownBy(() -> struct.getUint32(1, 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint32Type at position 1 index: 2 length: 2");
  }

  @Test
  void getUint32Array_not_allocated() {
    var struct = struct()
        .allocated()
        .uint32()
        .uint32Array(0)
        .build();

    assertThatThrownBy(() -> struct.getUint32(1, 0))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void getUint32Array_constant() {
    var struct = struct()
        .primitive().constant(5L).lengthExpression(constant(5)).uint32()
        .build();

    assertThat(struct.getUint32(0, 3)).isEqualTo(5L);
  }

  @Test
  void addUint32Wrapper() {
    var struct = struct()
        .uint32()
        .uint32Array(0)
        .build();

    assertThatThrownBy(() -> ((Uint32Type) struct.getType(1)).add(struct, Long.valueOf(1)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("add(Pointer, Long) not supported for Uint32Type. Use add(Pointer, long) instead.");
  }

  @Test
  void addUint32Wrapper_with_index() {
    var struct = struct()
        .uint32()
        .uint32Array(0)
        .build();

    assertThatThrownBy(() -> ((Uint32Type) struct.getType(1)).add(struct, 0, Long.valueOf(1)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("add(Pointer, long, Long) not supported for Uint32Type. Use add(Pointer, long, long) instead.");
  }

  @Test
  void addUint32() {
    var struct = struct()
        .uint32()
        .uint32Array(0)
        .fromBytes(new byte[] {0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 2})
        .build();

    struct.addUint32(1, 3L);
    struct.addUint32(1, 4L);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {
        0, 0, 0, 4,
        0, 0, 0, 1,
        0, 0, 0, 2,
        0, 0, 0, 3,
        0, 0, 0, 4
    });
  }

  @Test
  void addUint32_position_negative() {
    var struct = struct()
        .uint32()
        .uint32Array(0)
        .fromBytes(new byte[] {0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 2})
        .build();

    assertThatThrownBy(() -> struct.addUint32(-1, 3L))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 2");
  }

  @Test
  void addUint32_position_greater_than_length() {
    var struct = struct()
        .uint32()
        .uint32Array(0)
        .fromBytes(new byte[] {0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 2})
        .build();

    assertThatThrownBy(() -> struct.addUint32(3, 3L))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 3 out of bounds for length 2");
  }

  @Test
  void addUint32_not_allocated() {
    var struct = struct()
        .allocated()
        .uint32()
        .uint32Array(0)
        .build();
    assertThatThrownBy(() -> struct.addUint32(1, 3L))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void addUint32Array_constant() {
    var struct = struct()
        .primitive().constant(5L).lengthExpression(constant(5)).uint32()
        .build();

    assertThatThrownBy(() -> struct.addUint32(0, 3L))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Uint32Type at position 0 is constant index: 5 value: 3 constant: 5");
  }

  @Test
  void addUint32_not_array() {
    var struct = struct()
        .uint32()
        .fromBytes(new byte[] {0, 0, 0, 1})
        .build();
    assertThatThrownBy(() -> struct.addUint32(0, 3L))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint32Type cannot add to non-array type at position 0 index: 1 length: 1");
  }

  @Test
  void addUint32_with_index() {
    var struct = struct()
        .uint32()
        .uint32Array(0)
        .fromBytes(new byte[] {0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 2})
        .build();

    struct.addUint32(1, 0, 3L);
    struct.addUint32(1, 1, 4L);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {
        0, 0, 0, 4,
        0, 0, 0, 3,
        0, 0, 0, 4,
        0, 0, 0, 1,
        0, 0, 0, 2
    });
  }

  @Test
  void addUint32_with_index_negative() {
    var struct = struct()
        .uint32()
        .uint32Array(0)
        .fromBytes(new byte[] {0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 2})
        .build();

    assertThatThrownBy(() -> struct.addUint32(1, -1, 3L))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint32Type at position 1 index: -1 new length: 3");
  }

  @Test
  void addUint32_with_index_greater_than_length() {
    var struct = struct()
        .uint32()
        .uint32Array(0)
        .fromBytes(new byte[] {0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 2})
        .build();

    assertThatThrownBy(() -> struct.addUint32(1, 3, 3L))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint32Type at position 1 index: 3 new length: 3");
  }

  @Test
  void addUint32_with_index_not_allocated() {
    var struct = struct()
        .allocated()
        .uint32()
        .uint32Array(0)
        .build();

    assertThatThrownBy(() -> struct.addUint32(1, 0, 3L))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void addUint32_with_index_0_not_array() {
    var struct = struct()
        .uint32()
        .fromBytes(new byte[] {0, 0, 0, 1})
        .build();

    assertThatThrownBy(() -> struct.addUint32(0, 0, 3L))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint32Type cannot add to non-array type at position 0 index: 0 length: 1");
  }

  @Test
  void addUint32_with_index_1_not_array() {
    var struct = struct()
        .uint32()
        .fromBytes(new byte[] {0, 0, 0, 1})
        .build();

    assertThatThrownBy(() -> struct.addUint32(0, 1, 3L))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint32Type cannot add to non-array type at position 0 index: 1 length: 1");
  }

  @Test
  void addUint32Array_with_index_constant() {
    var struct = struct()
        .primitive().constant(5L).lengthExpression(constant(5)).uint32()
        .build();

    assertThatThrownBy(() -> struct.addUint32(0, 3, 3L))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Uint32Type at position 0 is constant index: 3 value: 3 constant: 5");
  }

  @Test
  void removeUint32() {
    var struct = struct()
        .uint32()
        .uint32Array(0)
        .fromBytes(new byte[] {0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 2})
        .build();

    struct.removeAll(1);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] { 0, 0, 0, 0 } );
  }

  @Test
  void removeUint32_position_negative() {
    var struct = struct()
        .uint32()
        .uint32Array(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(-1))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 2");
  }

  @Test
  void removeUint32_position_greater_than_length() {
    var struct = struct()
        .uint32()
        .uint32Array(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 2");
  }

  @Test
  void removeUint32_not_allocated() {
    var struct = struct()
        .allocated()
        .uint32()
        .uint32Array(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void removeUint32_not_array() {
    var struct = struct()
        .uint32()
        .build();

    assertThatThrownBy(() -> struct.removeAll(0))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint32Type cannot remove from non-array type at position 0");
  }

  @Test
  void removeUint32_with_index_0() {
    var struct = struct()
        .uint32()
        .uint32Array(0)
        .build();

    struct.addUint32(1, 1L);
    struct.addUint32(1, 2L);
    struct.remove(1, 0);

    assertThat(((Uint32Type) struct.getType(1)).getUint32(struct, 0)).isEqualTo(2L);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 0, 0, 1, 0, 0, 0, 2} );
  }

  @Test
  void removeUint32_with_index_1() {
    var struct = struct()
        .uint32()
        .uint32Array(0)
        .build();
    struct.addUint32(1, 1L);
    struct.addUint32(1, 2L);

    struct.remove(1, 1);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 0, 0, 1, 0, 0, 0, 1} );
  }

  @Test
  void removeUint32_with_index_negative() {
    var struct = struct()
        .uint32()
        .uint32Array(0)
        .fromBytes(new byte[] {0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 2})
        .build();

    assertThatThrownBy(() -> struct.remove(1, -1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint32Type at position 1 index: -1 length: 2");
  }

  @Test
  void removeUint32_with_index_greater_than_length() {
    var struct = struct()
        .uint32()
        .uint32Array(0)
        .fromBytes(new byte[] {0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 2})
        .build();

    assertThatThrownBy(() -> struct.remove(1, 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint32Type at position 1 index: 2 length: 2");
  }

  @Test
  void removeUint32_with_index_not_array() {
    var struct = struct()
    .uint32()
    .fromBytes(new byte[] {0, 0, 0, 1})
    .build();

    assertThatThrownBy(() -> struct.remove(0, 0))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint32Type cannot remove from non-array type at position 0");
  }

  @Test
  void removeUint32Array_fixed_length() {
    var struct = struct()
        .primitive().constant(5L).lengthExpression(constant(5)).uint32()
        .build();

    assertThatThrownBy(() -> struct.removeAll(0))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Cannot remove fixed length array Uint32Type at position 0");
  }

  @Test
  void removeUint32Array_fixed_length_with_index() {
    var struct = struct()
        .primitive().constant(5L).lengthExpression(constant(5)).uint32()
        .build();

    assertThatThrownBy(() -> struct.remove(0, 3))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Cannot remove element from fixed length array Uint32Type at position 0 index: 3");
  }
}
