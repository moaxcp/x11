package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11.struct.Builders.struct;
import static com.github.moaxcp.x11.struct.ByteLengthListener.align;
import static com.github.moaxcp.x11.struct.Expression.constant;
import static com.github.moaxcp.x11.struct.Expression.valueOf;
import static com.github.moaxcp.x11.struct.Int16Type.int16;
import static com.github.moaxcp.x11.struct.Primitive.INT16;
import static com.github.moaxcp.x11.struct.PrimitiveBuilder.primitive;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Int16TypeTest {

  @Test
  void constructor() {
    var type = int16();
    assertThat(type.getPosition()).isEqualTo(-1);
  }

  @Test
  void constructorPosition() {
    var type = int16(15);
    assertThat(type.getPosition()).isEqualTo(15);
  }

  @Test
  void constructorEverything() {
    var struct = struct()
        .int16()
        .primitive().constant((short) 5).lengthExpression(valueOf(0)).int16()
        .align(2)
        .build();

    assertThat(struct.getByteLength()).isEqualTo(INT16.size() + 2);
    assertThat(struct.<Int16Type>getType(1))
        .isEqualTo(primitive().position(1).byteLengthListener(align(2)).constant((short) 5).lengthExpression(valueOf(0)).int16());
  }

  @Test
  void copy() {
    var type = int16();
    var copy = type.copy(15);
    assertThat(copy.getPosition()).isEqualTo(15);
  }

  @Test
  void getByteLength() {
    var struct = struct()
        .int16()
        .build();

    assertThat(struct.getType(0).getByteLength(struct)).isEqualTo(INT16.size());
  }

  @Test
  void isFixedLength() {
    var struct = struct()
        .int16()
        .build();

    assertThat(struct.getType(0).isFixedLength(struct)).isTrue();
    assertThat(struct.isFixedLength()).isTrue();
  }

  @Test
  void isFixedLengthArray() {
    var struct = struct()
        .int16Array(constant(5))
        .build();

    assertThat(struct.getType(0).isFixedLength(struct)).isTrue();
    assertThat(struct.isFixedLength()).isTrue();
  }

  @Test
  void allocate() {
    var struct = struct()
        .int16()
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 0});
  }

  @Test
  void allocate_with_constant() {
    var struct = struct()
        .primitive().constant((short) 5).int16()
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 5});
  }

  @Test
  void allocate_empty_array() {
    var struct = struct()
        .int16()
        .int16Array(0)
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 0});
  }

  @Test
  void allocate_array_length_with_constant() {
    var struct = struct()
        .primitive().constant((short) 5).int16()
        .int16Array(0)
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
  }

  @Test
  void allocate_array_length_and_array_with_constant() {
    var struct = struct()
        .primitive().constant((short) 5).int16()
        .primitive().constant((short) 6).lengthField(0).int16()
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 5, 0, 6, 0, 6, 0, 6, 0, 6, 0, 6});
  }

  @Test
  void setWrapper() {
    var struct = struct()
        .int16()
        .build();

    assertThatThrownBy(() -> ((Int16Type) struct.getType(0)).set(struct, Short.valueOf((short) 2)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("set(Pointer, Short) not supported for Int16Type. Use set(Pointer, short) instead.");
  }

  @Test
  void setInt16() {
    var struct = struct()
        .int16()
        .build();

    struct.setInt16(0, (short) 2);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 2});
  }

  @Test
  void setInt16_position_negative() {
    var struct = struct()
        .int16()
        .build();

    assertThatThrownBy(() -> struct.setInt16(-1, (short) 2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 1");
  }

  @Test
  void setInt16_position_greater_than_length() {
    var struct = struct()
        .int16()
        .build();

    assertThatThrownBy(() -> struct.setInt16(2, (short) 2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 1");
  }

  @Test
  void setInt16_not_allocated() {
    var struct = struct()
        .allocated()
        .int16()
        .build();

    assertThatThrownBy(() -> struct.setInt16(0, (short) 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void setInt16_constant() {
    var struct = struct()
        .primitive().constant((short) 5).int16()
        .build();

    assertThatThrownBy(() -> struct.setInt16(0, (short) 2))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Int16Type at position 0 is constant index: 0 value: 2 constant: 5");
  }

  @Test
  void setArrayWrapper() {
    var struct = struct()
        .int16()
        .int16Array(0)
        .build();

    assertThatThrownBy(() -> ((Int16Type) struct.getType(1)).set(struct, 0, Short.valueOf((short) 2)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("set(Pointer, long, Short) not supported for Int16Type. Use set(Pointer, long, short) instead.");
  }

  @Test
  void setInt16Array() {
    var struct = struct()
        .int16()
        .int16Array(0)
        .int16()
        .fromBytes(new byte[] {0, 1, 0, 2, 0, 3})
        .build();

    struct.setInt16(1, 0, (short) 5);

    assertThat(struct.getInt16(0)).isEqualTo((short) 1);
    assertThat(struct.getInt16(1, 0)).isEqualTo((short) 5);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 1, 0, 5, 0, 3});
  }

  @Test
  void setInt16Array_negative() {
    var struct = struct()
        .int16()
        .int16Array(0)
        .int16()
        .fromBytes(new byte[] {0, 1, 0, 2, 0, 3})
        .build();

    assertThatThrownBy(() -> struct.setInt16(1, -1, (short) 5))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Int16Type at position 1 index: -1 length: 1");
  }

  @Test
  void setInt16Array_greater_than_length() {
    var struct = struct()
        .int16()
        .int16Array(0)
        .int16()
        .fromBytes(new byte[] {0, 1, 0, 2, 0, 3})
        .build();

    assertThatThrownBy(() -> struct.setInt16(1, 2, (short) 5))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int16Type at position 1 index: 2 length: 1");

    assertThat(struct.getInt16(0)).isEqualTo((short) 1);
    assertThat(struct.getInt16(1, 0)).isEqualTo((short) 2);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 1, 0, 2, 0, 3});
  }

  @Test
  void setInt16Array_not_allocated() {
    var struct = struct()
        .allocated()
        .int16()
        .int16Array(0)
        .build();

    assertThatThrownBy(() -> struct.setInt16(1, 0, (short) 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void setInt16Array_index_0_not_array() {
    var struct = struct()
        .int16()
        .build();

    struct.setInt16(0, 0, (short) 2);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 2});
  }

  @Test
  void setInt16Array_index_1_not_array() {
    var struct = struct()
        .int16()
        .build();

    assertThatThrownBy(() -> struct.setInt16(0, 1, (short) 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int16Type at position 0 index: 1 length: 1");
  }

  @Test
  void setInt16Array_constant_value_and_length() {
    var struct = struct()
        .primitive().constant((short) 5).lengthExpression(constant(5)).int16()
        .build();

    assertThatThrownBy(() -> struct.setInt16(0, 3, (short) 2))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Int16Type at position 0 is constant index: 3 value: 2 constant: 5");
  }

  @Test
  void setInt16Array_constant_value() {
    var struct = struct()
        .int16()
        .primitive().constant((short) 5).lengthField(0).int16()
        .fromBytes(new byte[] {2, 5, 5})
        .build();

    assertThatThrownBy(() -> struct.setInt16(1, 1, (short) 2))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Int16Type at position 1 is constant index: 1 value: 2 constant: 5");
  }

  @Test
  void setInt16Array_constant_value_same() {
    var struct = struct()
        .int16()
        .primitive().constant((short) 5).lengthField(0).int16()
        .fromBytes(new byte[] {0, 2, 0, 5, 0, 5})
        .build();

    struct.setInt16(1, 1, (short) 5);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 2, 0, 5, 0, 5});
  }

  @Test
  @Disabled
  void setInt16Array_set_length_field_without_adding_to_array() {
    var struct = struct()
        .int16()
        .int16Array(0)
        .build();

    assertThatThrownBy(() -> struct.setInt16(0, (short) 2))
        .isInstanceOf(AssertionError.class)
        .hasMessage("Int16Type at position 0 is being set and will not match the array it is used as a length for.");
  }

  @Test
  void getWrapper() {
    var struct = struct()
        .int16()
        .build();

    assertThatThrownBy(() -> ((Int16Type) struct.getType(0)).get(struct))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("get(Pointer) not supported for Int16Type. Use getInt16(Pointer) instead.");
  }

  @Test
  void getInt16() {
    var struct = struct()
        .int16()
        .build();

    struct.setInt16(0, (short) 2);

    assertThat(struct.getInt16(0)).isEqualTo((short) 2);
  }

  @Test
  void getInt16_position_negative() {
    var struct = struct()
        .int16()
        .build();

    assertThatThrownBy(() -> struct.getInt16(-1))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 1");
  }

  @Test
  void getInt16_position_greater_than_length() {
    var struct = struct()
        .int16()
        .build();

    assertThatThrownBy(() -> struct.getInt16(2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 1");
  }

  @Test
  void getInt16Allocated() {
    var struct = struct()
        .int16()
        .build();

    assertThat(struct.getInt16(0)).isEqualTo((short) 0);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 0});
  }

  @Test
  void getInt16NotAllocated() {
    var struct = struct()
        .allocated()
        .int16()
        .build();

    assertThatThrownBy(() -> struct.getInt16(0)).isInstanceOf(ArrayIndexOutOfBoundsException.class);
  }

  @Test
  void getInt16_constant() {
    var struct = struct()
        .primitive().constant((short) 5).int16()
        .build();

    assertThat(struct.getInt16(0)).isEqualTo((short) 5);
  }

  @Test
  void getInt16_index_0_not_array() {
    var struct = struct()
        .int16()
        .fromBytes(new byte[] {0, 1})
        .build();

    assertThat(struct.getInt16(0, 0)).isEqualTo((short) 1);
  }

  @Test
  void getInt16_index_1_not_array() {
    var struct = struct()
        .int16()
        .fromBytes(new byte[] {1})
        .build();

    assertThatThrownBy(() -> struct.getInt16(0, 1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int16Type at position 0 index: 1 length: 1");
  }

  @Test
  void getArrayWrapper() {
    var struct = struct()
        .int16()
        .int16Array(0)
        .build();

    assertThatThrownBy(() -> ((Int16Type) struct.getType(1)).get(struct, 0))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("get(Pointer, long) not supported for Int16Type. Use getInt16(Pointer, long) instead.");
  }

  @Test
  void getInt16Array() {
    var struct = struct()
        .int16()
        .int16Array(0)
        .fromBytes(new byte[] {0, 2, 0, 1, 0, 2})
        .build();

    assertThat(struct.getInt16(1, 0)).isEqualTo((short) 1);
    assertThat(struct.getInt16(1, 1)).isEqualTo((short) 2);
  }

  @Test
  void getInt16Array_negative() {
    var struct = struct()
        .int16()
        .int16Array(0)
        .build();

    assertThatThrownBy(() -> struct.getInt16(1, -1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int16Type at position 1 index: -1 length: 0");
  }

  @Test
  void getInt16Array_greater_than_length() {
    var struct = struct()
        .int16()
        .int16Array(0)
        .fromBytes(new byte[] {0, 2, 0, 1, 0, 2})
        .build();

    assertThatThrownBy(() -> struct.getInt16(1, 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int16Type at position 1 index: 2 length: 2");
  }

  @Test
  void getInt16Array_not_allocated() {
    var struct = struct()
        .allocated()
        .int16()
        .int16Array(0)
        .build();

    assertThatThrownBy(() -> struct.getInt16(1, 0))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void getInt16Array_constant() {
    var struct = struct()
        .primitive().constant((short) 5).lengthExpression(constant(5)).int16()
        .build();

    assertThat(struct.getInt16(0, 3)).isEqualTo((short) 5);
  }

  @Test
  void addInt16Wrapper() {
    var struct = struct()
        .int16()
        .int16Array(0)
        .build();

    assertThatThrownBy(() -> ((Int16Type) struct.getType(1)).add(struct, Short.valueOf((short) 1)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("add(Pointer, Short) not supported for Int16Type. Use add(Pointer, short) instead.");
  }

  @Test
  void addInt16Wrapper_with_index() {
    var struct = struct()
        .int16()
        .int16Array(0)
        .build();

    assertThatThrownBy(() -> ((Int16Type) struct.getType(1)).add(struct, 0, Short.valueOf((short) 1)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("add(Pointer, long, Short) not supported for Int16Type. Use add(Pointer, long, short) instead.");
  }

  @Test
  void addInt16() {
    var struct = struct()
        .int16()
        .int16Array(0)
        .fromBytes(new byte[] {0, 2, 0, 1, 0, 2})
        .build();

    struct.addInt16(1, (short) 3);
    struct.addInt16(1, (short) 4);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 4, 0, 1, 0, 2, 0, 3, 0, 4} );
  }

  @Test
  void addInt16_position_negative() {
    var struct = struct()
        .int16()
        .int16Array(0)
        .fromBytes(new byte[] {0, 2, 0, 1, 0, 2})
        .build();

    assertThatThrownBy(() -> struct.addInt16(-1, (short) 3))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 2");
  }

  @Test
  void addInt16_position_greater_than_length() {
    var struct = struct()
        .int16()
        .int16Array(0)
        .fromBytes(new byte[] {0, 2, 0, 1, 0, 2})
        .build();

    assertThatThrownBy(() -> struct.addInt16(3, (short) 3))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 3 out of bounds for length 2");
  }

  @Test
  void addInt16_not_allocated() {
    var struct = struct()
        .allocated()
        .int16()
        .int16Array(0)
        .build();
    assertThatThrownBy(() -> struct.addInt16(1, (short) 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void addInt16Array_constant() {
    var struct = struct()
        .primitive().constant((short) 5).lengthExpression(constant(5)).int16()
        .build();

    assertThatThrownBy(() -> struct.addInt16(0, (short) 3))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Int16Type at position 0 is constant index: 5 value: 3 constant: 5");
  }

  @Test
  void addInt16_not_array() {
    var struct = struct()
        .int16()
        .fromBytes(new byte[] {0, 1})
        .build();
    assertThatThrownBy(() -> struct.addInt16(0, (short) 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int16Type cannot add to non-array type at position 0 index: 1 length: 1");
  }

  @Test
  void addInt16_with_index() {
    var struct = struct()
        .int16()
        .int16Array(0)
        .fromBytes(new byte[] {0, 2, 0, 1, 0, 2})
        .build();

    struct.addInt16(1, 0, (short) 3);
    struct.addInt16(1, 1, (short) 4);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 4, 0, 3, 0, 4, 0, 1, 0, 2} );
  }

  @Test
  void addInt16_with_index_negative() {
    var struct = struct()
        .int16()
        .int16Array(0)
        .fromBytes(new byte[] {0, 2, 0, 1, 0, 2})
        .build();

    assertThatThrownBy(() -> struct.addInt16(1, -1, (short) 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int16Type at position 1 index: -1 new length: 3");
  }

  @Test
  void addInt16_with_index_greater_than_length() {
    var struct = struct()
        .int16()
        .int16Array(0)
        .fromBytes(new byte[] {0, 2, 0, 1, 0, 2})
        .build();

    assertThatThrownBy(() -> struct.addInt16(1, 3, (short) 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int16Type at position 1 index: 3 new length: 3");
  }

  @Test
  void addInt16_with_index_not_allocated() {
    var struct = struct()
        .allocated()
        .int16()
        .int16Array(0)
        .build();

    assertThatThrownBy(() -> struct.addInt16(1, 0, (short) 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void addInt16_with_index_0_not_array() {
    var struct = struct()
        .int16()
        .fromBytes(new byte[] {0, 1})
        .build();

    assertThatThrownBy(() -> struct.addInt16(0, 0, (short) 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int16Type cannot add to non-array type at position 0 index: 0 length: 1");
  }

  @Test
  void addInt16_with_index_1_not_array() {
    var struct = struct()
        .int16()
        .fromBytes(new byte[] {0, 1})
        .build();

    assertThatThrownBy(() -> struct.addInt16(0, 1, (short) 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int16Type cannot add to non-array type at position 0 index: 1 length: 1");
  }

  @Test
  void addInt16Array_with_index_constant() {
    var struct = struct()
        .primitive().constant((short) 5).lengthExpression(constant(5)).int16()
        .build();

    assertThatThrownBy(() -> struct.addInt16(0, 3, (short) 3))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Int16Type at position 0 is constant index: 3 value: 3 constant: 5");
  }

  @Test
  void removeInt16() {
    var struct = struct()
        .int16()
        .int16Array(0)
        .fromBytes(new byte[] {0, 2, 0, 1, 0, 2})
        .build();

    struct.removeAll(1);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] { 0, 0 } );
  }

  @Test
  void removeInt16_position_negative() {
    var struct = struct()
        .int16()
        .int16Array(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(-1))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 2");
  }

  @Test
  void removeInt16_position_greater_than_length() {
    var struct = struct()
        .int16()
        .int16Array(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 2");
  }

  @Test
  void removeInt16_not_allocated() {
    var struct = struct()
        .allocated()
        .int16()
        .int16Array(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void removeInt16_not_array() {
    var struct = struct()
        .int16()
        .build();

    assertThatThrownBy(() -> struct.removeAll(0))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int16Type cannot remove from non-array type at position 0");
  }

  @Test
  void removeInt16_with_index_0() {
    var struct = struct()
        .int16()
        .int16Array(0)
        .build();

    struct.addInt16(1, (short) 1);
    struct.addInt16(1, (short) 2);
    struct.remove(1, 0);

    assertThat(((Int16Type) struct.getType(1)).getInt16(struct, 0)).isEqualTo((short) 2);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 1, 0, 2} );
  }

  @Test
  void removeInt16_with_index_1() {
    var struct = struct()
        .int16()
        .int16Array(0)
        .build();
    struct.addInt16(1, (short) 1);
    struct.addInt16(1, (short) 2);

    struct.remove(1, 1);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 1, 0, 1} );
  }

  @Test
  void removeInt16_with_index_negative() {
    var struct = struct()
        .int16()
        .int16Array(0)
        .fromBytes(new byte[] {0, 2, 0, 1, 0, 2})
        .build();

    assertThatThrownBy(() -> struct.remove(1, -1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int16Type at position 1 index: -1 length: 2");
  }

  @Test
  void removeInt16_with_index_greater_than_length() {
    var struct = struct()
        .int16()
        .int16Array(0)
        .fromBytes(new byte[] {0, 2, 0, 1, 0, 2})
        .build();

    assertThatThrownBy(() -> struct.remove(1, 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int16Type at position 1 index: 2 length: 2");
  }

  @Test
  void removeInt16_with_index_not_array() {
    var struct = struct()
        .int16()
        .fromBytes(new byte[] {0, 1})
        .build();

    assertThatThrownBy(() -> struct.remove(0, 0))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int16Type cannot remove from non-array type at position 0");
  }

  @Test
  void removeInt16Array_fixed_length() {
    var struct = struct()
        .primitive().constant((short) 5).lengthExpression(constant(5)).int16()
        .build();

    assertThatThrownBy(() -> struct.removeAll(0))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Cannot remove fixed length array Int16Type at position 0");
  }

  @Test
  void removeInt16Array_fixed_length_with_index() {
    var struct = struct()
        .primitive().constant((short) 5).lengthExpression(constant(5)).int16()
        .build();

    assertThatThrownBy(() -> struct.remove(0, 3))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Cannot remove element from fixed length array Int16Type at position 0 index: 3");
  }
}
