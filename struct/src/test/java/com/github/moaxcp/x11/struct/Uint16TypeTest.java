package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11.struct.Builders.struct;
import static com.github.moaxcp.x11.struct.ByteLengthListener.align;
import static com.github.moaxcp.x11.struct.Expression.constant;
import static com.github.moaxcp.x11.struct.Expression.valueOf;
import static com.github.moaxcp.x11.struct.Primitive.UINT16;
import static com.github.moaxcp.x11.struct.PrimitiveBuilder.primitive;
import static com.github.moaxcp.x11.struct.Uint16Type.uint16;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Uint16TypeTest {

  @Test
  void constructor() {
    var type = Uint16Type.uint16();
    assertThat(type.getPosition()).isEqualTo(-1);
  }

  @Test
  void constructorPosition() {
    var type = uint16(15);
    assertThat(type.getPosition()).isEqualTo(15);
  }

  @Test
  void constructorEverything() {
    var struct = struct()
        .uint16()
        .primitive().constant(5).lengthExpression(valueOf(0)).uint16()
        .align(2)
        .build();

    assertThat(struct.getByteLength()).isEqualTo(UINT16.size() + 2);
    assertThat(struct.<Uint16Type>getType(1))
        .isEqualTo(primitive().position(1).byteLengthListener(align(2)).constant(5).lengthExpression(valueOf(0)).uint16());
  }

  @Test
  void copy() {
    var type = Uint16Type.uint16();
    var copy = type.copy(15);
    assertThat(copy.getPosition()).isEqualTo(15);
  }

  @Test
  void getByteLength() {
    var struct = struct()
        .uint16()
        .build();

    assertThat(struct.getByteLength()).isEqualTo(UINT16.size());
    assertThat(struct.getType(0).getByteLength(struct)).isEqualTo(UINT16.size());
  }

  @Test
  void isFixedLength() {
    var struct = struct()
        .uint16()
        .build();

    assertThat(struct.getType(0).isFixedLength(struct)).isTrue();
    assertThat(struct.isFixedLength()).isTrue();
  }

  @Test
  void isFixedLengthArray() {
    var struct = struct()
        .primitive().lengthExpression(constant(5)).uint16()
        .uint16Array(constant(5))
        .build();

    assertThat(struct.getType(0).isFixedLength(struct)).isTrue();
    assertThat(struct.isFixedLength()).isTrue();
  }

  @Test
  void allocate() {
    var struct = struct()
        .uint16()
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 0});
  }

  @Test
  void allocate_with_constant() {
    var struct = struct()
        .primitive().constant(5).uint16()
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 5});
  }

  @Test
  void allocate_empty_array() {
    var struct = struct()
        .uint16()
        .uint16Array(0)
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 0});
  }

  @Test
  void allocate_array_length_with_constant() {
    var struct = struct()
        .primitive().constant(5).uint16()
        .uint16Array(0)
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
  }

  @Test
  void setWrapper() {
    var struct = struct()
        .uint16()
        .build();

    assertThatThrownBy(() -> ((Uint16Type) struct.getType(0)).set(struct, Integer.valueOf(2)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("set(Pointer, Integer) not supported for Uint16Type. Use set(Pointer, int) instead.");
  }

  @Test
  void setUint16() {
    var struct = struct()
        .uint16()
        .build();

    struct.setUint16(0, 2);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 2});
  }

  @Test
  void setUint16_position_negative() {
    var struct = struct()
        .uint16()
        .build();

    assertThatThrownBy(() -> struct.setUint16(-1, 2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 1");
  }

  @Test
  void setUint16_position_greater_than_length() {
    var struct = struct()
        .uint16()
        .build();

    assertThatThrownBy(() -> struct.setUint16(2, 2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 1");
  }

  @Test
  void setUint16_not_allocated() {
    var struct = struct()
        .allocated()
        .uint16()
        .build();

    assertThatThrownBy(() -> struct.setUint16(0, 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void setUint16_constant() {
    var struct = struct()
        .primitive().constant(5).uint16()
        .build();

    assertThatThrownBy(() -> struct.setUint16(0, 2))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Uint16Type at position 0 is constant index: 0 value: 2 constant: 5");
  }

  @Test
  void setArrayWrapper() {
    var struct = struct()
        .uint16()
        .uint16Array(0)
        .build();

    assertThatThrownBy(() -> ((Uint16Type) struct.getType(1)).set(struct, 0, Integer.valueOf(2)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("set(Pointer, long, Integer) not supported for Uint16Type. Use set(Pointer, long, int) instead.");
  }

  @Test
  void setUint16Array() {
    var struct = struct()
        .uint16()
        .uint16Array(0)
        .uint16()
        .fromBytes(new byte[] {0, 1, 0, 2, 0, 3})
        .build();

    struct.setUint16(1, 0, 5);

    assertThat(struct.getUint16(0)).isEqualTo(1);
    assertThat(struct.getUint16(1, 0)).isEqualTo(5);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 1, 0, 5, 0, 3});
  }

  @Test
  void setUint16Array_negative() {
    var struct = struct()
        .uint16()
        .uint16Array(0)
        .uint16()
        .fromBytes(new byte[] {0, 1, 0, 2, 0, 3})
        .build();

    assertThatThrownBy(() -> struct.setUint16(1, -1, 5))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Uint16Type at position 1 index: -1 length: 1");
  }

  @Test
  void setUint16Array_greater_than_length() {
    var struct = struct()
        .uint16()
        .uint16Array(0)
        .uint16()
        .fromBytes(new byte[] {0, 1, 0, 2, 0, 3})
        .build();

    assertThatThrownBy(() -> struct.setUint16(1, 2, 5))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint16Type at position 1 index: 2 length: 1");

    assertThat(struct.getUint16(0)).isEqualTo(1);
    assertThat(struct.getUint16(1, 0)).isEqualTo(2);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 1, 0, 2, 0, 3});
  }

  @Test
  void setUint16Array_not_allocated() {
    var struct = struct()
        .allocated()
        .uint16()
        .uint16Array(0)
        .build();

    assertThatThrownBy(() -> struct.setUint16(1, 0, 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void setUint16Array_index_0_not_array() {
    var struct = struct()
        .uint16()
        .build();

    struct.setUint16(0, 0, 2);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 2});
  }

  @Test
  void setUint16Array_index_1_not_array() {
    var struct = struct()
        .uint16()
        .build();

    assertThatThrownBy(() -> struct.setUint16(0, 1, 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint16Type at position 0 index: 1 length: 1");
  }

  @Test
  void setUint16Array_constant_value_and_length() {
    var struct = struct()
        .primitive().constant(5).lengthExpression(constant(5)).uint16()
        .build();

    assertThatThrownBy(() -> struct.setUint16(0, 3, 2))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Uint16Type at position 0 is constant index: 3 value: 2 constant: 5");
  }

  @Test
  void setUint16Array_constant_value() {
    var struct = struct()
        .uint16()
        .primitive().constant(5).lengthField(0).uint16()
        .fromBytes(new byte[] {0, 2, 0, 5, 0, 5})
        .build();

    assertThatThrownBy(() -> struct.setUint16(1, 1, 2))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Uint16Type at position 1 is constant index: 1 value: 2 constant: 5");
  }

  @Test
  void setUint16Array_constant_value_same() {
    var struct = struct()
        .uint16()
        .primitive().constant(5).lengthField(0).uint16()
        .fromBytes(new byte[] {0, 2, 0, 5, 0, 5})
        .build();

    struct.setUint16(1, 1, 5);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 2, 0, 5, 0, 5});
  }

  @Test
  @Disabled
  void setUint16Array_set_length_field_without_adding_to_array() {
    var struct = struct()
        .uint16()
        .uint16Array(0)
        .build();

    assertThatThrownBy(() -> struct.setUint16(0, 2))
        .isInstanceOf(AssertionError.class)
        .hasMessage("Uint16Type at position 0 is being set and will not match the array it is used as a length for.");
  }

  @Test
  void getWrapper() {
    var struct = struct()
        .uint16()
        .build();

    assertThatThrownBy(() -> ((Uint16Type) struct.getType(0)).get(struct))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("get(Pointer) not supported for Uint16Type. Use getUint16(Pointer) instead.");
  }

  @Test
  void getUint16() {
    var struct = struct()
        .uint16()
        .build();

    struct.setUint16(0, 2);

    assertThat(struct.getUint16(0)).isEqualTo(2);
  }

  @Test
  void getUint16_position_negative() {
    var struct = struct()
        .uint16()
        .build();

    assertThatThrownBy(() -> struct.getUint16(-1))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 1");
  }

  @Test
  void getUint16_position_greater_than_length() {
    var struct = struct()
        .uint16()
        .build();

    assertThatThrownBy(() -> struct.getUint16(2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 1");
  }

  @Test
  void getUint16Allocated() {
    var struct = struct()
        .uint16()
        .build();

    assertThat(struct.getUint16(0)).isEqualTo(0);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 0});
  }

  @Test
  void getUint16NotAllocated() {
    var struct = struct()
        .allocated()
        .uint16()
        .build();

    assertThatThrownBy(() -> struct.getUint16(0)).isInstanceOf(ArrayIndexOutOfBoundsException.class);
  }

  @Test
  void getUint16_constant() {
    var struct = struct()
        .primitive().constant(5).uint16()
        .build();

    assertThat(struct.getUint16(0)).isEqualTo(5);
  }

  @Test
  void getUint16_index_0_not_array() {
    var struct = struct()
        .uint16()
        .fromBytes(new byte[] {0, 1})
        .build();

    assertThat(struct.getUint16(0, 0)).isEqualTo(1);
  }

  @Test
  void getUint16_index_1_not_array() {
    var struct = struct()
        .uint16()
        .fromBytes(new byte[] {0, 1})
        .build();

    assertThatThrownBy(() -> struct.getUint16(0, 1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint16Type at position 0 index: 1 length: 1");
  }

  @Test
  void getArrayWrapper() {
    var struct = struct()
        .uint16()
        .uint16Array(0)
        .build();

    assertThatThrownBy(() -> ((Uint16Type) struct.getType(1)).get(struct, 0))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("get(Pointer, long) not supported for Uint16Type. Use getUint16(Pointer, long) instead.");
  }

  @Test
  void getUint16Array() {
    var struct = struct()
        .uint16()
        .uint16Array(0)
        .fromBytes(new byte[] {0, 2, 0, 1, 0, 2})
        .build();

    assertThat(struct.getUint16(1, 0)).isEqualTo(1);
    assertThat(struct.getUint16(1, 1)).isEqualTo(2);
  }

  @Test
  void getUint16Array_negative() {
    var struct = struct()
        .uint16()
        .uint16Array(0)
        .build();

    assertThatThrownBy(() -> struct.getUint16(1, -1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint16Type at position 1 index: -1 length: 0");
  }

  @Test
  void getUint16Array_greater_than_length() {
    var struct = struct()
        .uint16()
        .uint16Array(0)
        .fromBytes(new byte[] {0, 2, 0, 1, 0, 2})
        .build();

    assertThatThrownBy(() -> struct.getUint16(1, 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint16Type at position 1 index: 2 length: 2");
  }

  @Test
  void getUint16Array_not_allocated() {
    var struct = struct()
        .allocated()
        .uint16()
        .uint16Array(0)
        .build();

    assertThatThrownBy(() -> struct.getUint16(1, 0))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void getUint16Array_constant() {
    var struct = struct()
        .primitive().constant(5).lengthExpression(constant(5)).uint16()
        .build();

    assertThat(struct.getUint16(0, 3)).isEqualTo(5);
  }

  @Test
  void addUint16Wrapper() {
    var struct = struct()
        .uint16()
        .uint16Array(0)
        .build();

    assertThatThrownBy(() -> ((Uint16Type) struct.getType(1)).add(struct, Integer.valueOf(1)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("add(Pointer, Integer) not supported for Uint16Type. Use add(Pointer, int) instead.");
  }

  @Test
  void addUint16Wrapper_with_index() {
    var struct = struct()
        .uint16()
        .uint16Array(0)
        .build();

    assertThatThrownBy(() -> ((Uint16Type) struct.getType(1)).add(struct, 0, Integer.valueOf(1)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("add(Pointer, long, Integer) not supported for Uint16Type. Use add(Pointer, long, int) instead.");
  }

  @Test
  void addUint16() {
    var struct = struct()
        .uint16()
        .uint16Array(0)
        .fromBytes(new byte[] {0, 2, 0, 1, 0, 2})
        .build();

    struct.addUint16(1, 3);
    struct.addUint16(1, 4);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 4, 0, 1, 0, 2, 0, 3, 0, 4} );
  }

  @Test
  void addUint16_position_negative() {
    var struct = struct()
    .uint16()
    .uint16Array(0)
    .fromBytes(new byte[] {0, 2, 0, 1, 0, 2})
    .build();

    assertThatThrownBy(() -> struct.addUint16(-1, 3))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 2");
  }

  @Test
  void addUint16_position_greater_than_length() {
    var struct = struct()
        .uint16()
        .uint16Array(0)
        .fromBytes(new byte[] {0, 2, 0, 1, 0, 2})
        .build();

    assertThatThrownBy(() -> struct.addUint16(3, 3))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 3 out of bounds for length 2");
  }

  @Test
  void addUint16_not_allocated() {
    var struct = struct()
        .allocated()
        .uint16()
        .uint16Array(0)
        .build();
    assertThatThrownBy(() -> struct.addUint16(1, 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void addUint16Array_constant() {
    var struct = struct()
        .primitive().constant(5).lengthExpression(constant(5)).uint16()
        .build();

    assertThatThrownBy(() -> struct.addUint16(0, 3))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Uint16Type at position 0 is constant index: 5 value: 3 constant: 5");
  }

  @Test
  void addUint16_not_array() {
    var struct = struct()
        .uint16()
        .fromBytes(new byte[] {0, 1})
        .build();
    assertThatThrownBy(() -> struct.addUint16(0, 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint16Type cannot add to non-array type at position 0 index: 1 length: 1");
  }

  @Test
  void addUint16_with_index() {
    var struct = struct()
        .uint16()
        .uint16Array(0)
        .fromBytes(new byte[] {0, 2, 0, 1, 0, 2})
        .build();

    struct.addUint16(1, 0, 3);
    struct.addUint16(1, 1, 4);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 4, 0, 3, 0, 4, 0, 1, 0, 2} );
  }

  @Test
  void addUint16_with_index_negative() {
    var struct = struct()
        .uint16()
        .uint16Array(0)
        .fromBytes(new byte[] {0, 2, 0, 1, 0, 2})
        .build();

    assertThatThrownBy(() -> struct.addUint16(1, -1, 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint16Type at position 1 index: -1 new length: 3");
  }

  @Test
  void addUint16_with_index_greater_than_length() {
    var struct = struct()
        .uint16()
        .uint16Array(0)
        .fromBytes(new byte[] {0, 2, 0, 1, 0, 2})
        .build();

    assertThatThrownBy(() -> struct.addUint16(1, 3, 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint16Type at position 1 index: 3 new length: 3");
  }

  @Test
  void addUint16_with_index_not_allocated() {
    var struct = struct()
        .allocated()
        .uint16()
        .uint16Array(0)
        .build();

    assertThatThrownBy(() -> struct.addUint16(1, 0, 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void addUint16_with_index_0_not_array() {
    var struct = struct()
        .uint16()
        .fromBytes(new byte[] {0, 1})
        .build();

    assertThatThrownBy(() -> struct.addUint16(0, 0, 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint16Type cannot add to non-array type at position 0 index: 0 length: 1");
  }

  @Test
  void addUint16_with_index_1_not_array() {
    var struct = struct()
        .uint16()
        .fromBytes(new byte[] {0, 1})
        .build();

    assertThatThrownBy(() -> struct.addUint16(0, 1, 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint16Type cannot add to non-array type at position 0 index: 1 length: 1");
  }

  @Test
  void addUint16Array_with_index_constant() {
    var struct = struct()
        .primitive().constant(5).lengthExpression(constant(5)).uint16()
        .build();

    assertThatThrownBy(() -> struct.addUint16(0, 3, 3))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Uint16Type at position 0 is constant index: 3 value: 3 constant: 5");
  }

  @Test
  void removeUint16() {
    var struct = struct()
        .uint16()
        .uint16Array(0)
        .fromBytes(new byte[] {0, 2, 0, 1, 0, 2})
        .build();

    struct.removeAll(1);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 0});
  }

  @Test
  void removeUint16_position_negative() {
    var struct = struct()
        .uint16()
        .uint16Array(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(-1))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 2");
  }

  @Test
  void removeUint16_position_greater_than_length() {
    var struct = struct()
        .uint16()
        .uint16Array(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 2");
  }

  @Test
  void removeUint16_not_allocated() {
    var struct = struct()
        .allocated()
        .uint16()
        .uint16Array(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void removeUint16_not_array() {
    var struct = struct()
        .uint16()
        .build();

    assertThatThrownBy(() -> struct.removeAll(0))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint16Type cannot remove from non-array type at position 0");
  }

  @Test
  void removeUint16_with_index_0() {
    var struct = struct()
        .uint16()
        .uint16Array(0)
        .build();

    struct.addUint16(1, 1);
    struct.addUint16(1, 2);
    struct.remove(1, 0);

    assertThat(((Uint16Type) struct.getType(1)).getUint16(struct, 0)).isEqualTo(2);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 1, 0, 2});
  }

  @Test
  void removeUint16_with_index_1() {
    var struct = struct()
        .uint16()
        .uint16Array(0)
        .build();
    struct.addUint16(1, 1);
    struct.addUint16(1, 2);

    struct.remove(1, 1);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 1, 0, 1});
  }

  @Test
  void removeUint16_with_index_negative() {
    var struct = struct()
        .uint16()
        .uint16Array(0)
        .fromBytes(new byte[] {0, 2, 0, 1, 0, 2})
        .build();

    assertThatThrownBy(() -> struct.remove(1, -1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint16Type at position 1 index: -1 length: 2");
  }

  @Test
  void removeUint16_with_index_greater_than_length() {
    var struct = struct()
        .uint16()
        .uint16Array(0)
        .fromBytes(new byte[] {0, 2, 0, 1, 0, 2})
        .build();

    assertThatThrownBy(() -> struct.remove(1, 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint16Type at position 1 index: 2 length: 2");
  }

  @Test
  void removeUint16_with_index_not_array() {
    var struct = struct()
        .uint16()
        .fromBytes(new byte[] {0, 1})
        .build();

    assertThatThrownBy(() -> struct.remove(0, 0))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint16Type cannot remove from non-array type at position 0");
  }

  @Test
  void removeUint16Array_fixed_length() {
    var struct = struct()
        .primitive().constant(5).lengthExpression(constant(5)).uint16()
        .build();

    assertThatThrownBy(() -> struct.removeAll(0))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Cannot remove fixed length array Uint16Type at position 0");
  }

  @Test
  void removeUint16Array_fixed_length_with_index() {
    var struct = struct()
        .primitive().constant(5).lengthExpression(constant(5)).uint16()
        .build();

    assertThatThrownBy(() -> struct.remove(0, 3))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Cannot remove element from fixed length array Uint16Type at position 0 index: 3");
  }
}
