package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11.struct.Builders.struct;
import static com.github.moaxcp.x11.struct.ByteArray.ba;
import static com.github.moaxcp.x11.struct.ByteLengthListener.align;
import static com.github.moaxcp.x11.struct.Expression.constant;
import static com.github.moaxcp.x11.struct.Expression.valueOf;
import static com.github.moaxcp.x11.struct.Int8Type.int8;
import static com.github.moaxcp.x11.struct.Primitive.INT8;
import static com.github.moaxcp.x11.struct.PrimitiveBuilder.primitive;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Int8TypeTest {

  @Test
  void constructor() {
    var type = Int8Type.int8();
    assertThat(type.getPosition()).isEqualTo(-1);
  }

  @Test
  void constructorPosition() {
    var type = int8(15);
    assertThat(type.getPosition()).isEqualTo(15);
  }

  @Test
  void constructorEverything() {
    var struct = struct()
        .int8()
        .primitive().constant((byte) 5).lengthExpression(valueOf(0)).int8()
        .align(2)
        .build();

    assertThat(struct.getByteLength()).isEqualTo(INT8.size() + 2);
    assertThat(struct.<Int8Type>getType(1))
        .isEqualTo(primitive().position(1).byteLengthListener(align(2)).constant((byte) 5).lengthExpression(valueOf(0)).int8());
  }

  @Test
  void copy() {
    var type = Int8Type.int8();
    var copy = type.copy(15);
    assertThat(copy.getPosition()).isEqualTo(15);
  }

  @Test
  void getByteLength() {
    var struct = struct()
        .int8()
        .build();

    assertThat(struct.getByteLength()).isEqualTo(INT8.size());
    assertThat(struct.getType(0).getByteLength(struct)).isEqualTo(INT8.size());
  }

  @Test
  void isFixedLength() {
    var struct = struct()
        .int8()
        .build();

    assertThat(struct.getType(0).isFixedLength(struct)).isTrue();
    assertThat(struct.isFixedLength()).isTrue();
  }

  @Test
  void isFixedLengthArray() {
    var struct = struct()
        .int8Array(constant(5))
        .build();

    assertThat(struct.getType(0).isFixedLength(struct)).isTrue();
    assertThat(struct.isFixedLength()).isTrue();
  }

  @Test
  void allocate() {
    var struct = struct()
        .int8()
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0});
  }

  @Test
  void allocate_with_constant() {
    var struct = struct()
        .primitive().constant((byte) 5).int8()
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {5});
  }

  @Test
  void allocate_empty_array() {
    var struct = struct()
        .int8()
        .int8Array(0)
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0});
  }

  @Test
  void allocate_array_length_with_constant() {
    var struct = struct()
        .primitive().constant((byte) 5).int8()
        .int8Array(0)
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {5, 0, 0, 0, 0, 0});
  }

  @Test
  void allocate_array_length_and_array_with_constant() {
    var struct = struct()
        .primitive().constant((byte) 5).int8()
        .primitive().constant((byte) 6).lengthField(0).int8()
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {5, 6, 6, 6, 6, 6});
  }

  @Test
  void setWrapper() {
    var struct = struct()
        .int8()
        .build();

    assertThatThrownBy(() -> ((Int8Type) struct.getType(0)).set(struct, Byte.valueOf((byte) 2)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("set(Pointer, Byte) not supported for Int8Type. Use set(Pointer, byte) instead.");
  }

  @Test
  void setInt8() {
    var struct = struct()
        .int8()
        .build();

    struct.setInt8(0, (byte) 2);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {2});
  }

  @Test
  void setInt8_position_negative() {
    var struct = struct()
        .int8()
        .build();

    assertThatThrownBy(() -> struct.setInt8(-1, (byte) 2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 1");
  }

  @Test
  void setInt8_position_greater_than_length() {
    var struct = struct()
        .int8()
        .build();

    assertThatThrownBy(() -> struct.setInt8(2, (byte) 2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 1");
  }

  @Test
  void setInt8_not_allocated() {
    var struct = struct()
        .allocated()
        .int8()
        .build();

    assertThatThrownBy(() -> struct.setInt8(0, (byte) 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void setInt8_constant() {
    var struct = struct()
        .primitive().constant((byte) 5).int8()
        .build();

    assertThatThrownBy(() -> struct.setInt8(0, (byte) 2))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Int8Type at position 0 is constant index: 0 value: 2 constant: 5");
  }

  @Test
  void setArrayWrapper() {
    var struct = struct()
        .int8()
        .int8Array(0)
        .build();

    assertThatThrownBy(() -> ((Int8Type) struct.getType(1)).set(struct, 0, Byte.valueOf((byte) 2)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("set(Pointer, long, Byte) not supported for Int8Type. Use set(Pointer, long, byte) instead.");
  }

  @Test
  void setInt8Array() {
    var struct = struct()
        .int8()
        .int8Array(0)
        .int8()
        .fromBytes(new byte[] {1, 2, 3})
        .build();

    struct.setInt8(1, 0, (byte) 5);

    assertThat(struct.getInt8(0)).isEqualTo((byte) 1);
    assertThat(struct.getInt8(1, 0)).isEqualTo((byte) 5);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {1, 5, 3});
  }

  @Test
  void setInt8Array_negative() {
    var struct = struct()
        .int8()
        .int8Array(0)
        .int8()
        .fromBytes(new byte[] {1, 2, 3})
        .build();

    assertThatThrownBy(() -> struct.setInt8(1, -1, (byte) 5))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Int8Type at position 1 index: -1 length: 1");
  }

  @Test
  void setInt8Array_greater_than_length() {
    var struct = struct()
        .int8()
        .int8Array(0)
        .int8()
        .fromBytes(new byte[] {1, 2, 3})
        .build();

    assertThatThrownBy(() -> struct.setInt8(1, 2, (byte) 5))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int8Type at position 1 index: 2 length: 1");

    assertThat(struct.getInt8(0)).isEqualTo((byte) 1);
    assertThat(struct.getInt8(1, 0)).isEqualTo((byte) 2);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {1, 2, 3});
  }

  @Test
  void setInt8Array_not_allocated() {
    var struct = struct()
        .allocated()
        .int8()
        .int8Array(0)
        .build();

    assertThatThrownBy(() -> struct.setInt8(1, 0, (byte) 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void setInt8Array_index_0_not_array() {
    var struct = struct()
        .int8()
        .build();

    struct.setInt8(0, 0, (byte) 2);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {2});
  }

  @Test
  void setInt8Array_index_1_not_array() {
    var struct = struct()
        .int8()
        .build();

    assertThatThrownBy(() -> struct.setInt8(0, 1, (byte) 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int8Type at position 0 index: 1 length: 1");
  }

  @Test
  void setInt8Array_constant_value_and_length() {
    var struct = struct()
        .primitive().constant((byte) 5).lengthExpression(constant(5)).int8()
        .build();

    assertThatThrownBy(() -> struct.setInt8(0, 3, (byte) 2))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Int8Type at position 0 is constant index: 3 value: 2 constant: 5");
  }

  @Test
  void setInt8Array_constant_value() {
    var struct = struct()
        .int8()
        .primitive().constant((byte) 5).lengthField(0).int8()
        .fromBytes(new byte[] {2, 5, 5})
        .build();

    assertThatThrownBy(() -> struct.setInt8(1, 1, (byte) 2))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Int8Type at position 1 is constant index: 1 value: 2 constant: 5");
  }

  @Test
  void setInt8Array_constant_value_same() {
    var struct = struct()
        .int8()
        .primitive().constant((byte) 5).lengthField(0).int8()
        .fromBytes(new byte[] {2, 5, 5})
        .build();

    struct.setInt8(1, 1, (byte) 5);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {2, 5, 5});
  }

  @Test
  void setInt8Array_set_length_field_adds_to_array() {
    var struct = struct()
        .int8()
        .int8Array(0)
        .build();

    struct.setInt8(0, (byte) 2);

    assertThat(struct.getArrayLength(1)).isEqualTo(2);
    assertThat(struct.getByteArray()).isEqualTo(ba().int8(2).int8(0).int8(0));
  }

  @Test
  void setInt8Array_set_length_field_removes_from_array() {
    var struct = struct()
        .int8()
        .int8Array(0)
        .fromBytes(ba().int8(3).int8(1).int8(2).int8(3))
        .build();

    struct.setInt8(0, (byte) 2);

    assertThat(struct.getArrayLength(1)).isEqualTo(2);
    assertThat(struct.getByteArray()).isEqualTo(ba().int8(2).int8(1).int8(2));
  }

  @Test
  void getWrapper() {
    var struct = struct()
        .int8()
        .build();

    assertThatThrownBy(() -> ((Int8Type) struct.getType(0)).get(struct))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("get(Pointer) not supported for Int8Type. Use getInt8(Pointer) instead.");
  }

  @Test
  void getInt8() {
    var struct = struct()
        .int8()
        .build();

    struct.setInt8(0, (byte) 2);

    assertThat(struct.getInt8(0)).isEqualTo((byte) 2);
  }

  @Test
  void getInt8_position_negative() {
    var struct = struct()
        .int8()
        .build();

    assertThatThrownBy(() -> struct.getInt8(-1))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 1");
  }

  @Test
  void getInt8_position_greater_than_length() {
    var struct = struct()
        .int8()
        .build();

    assertThatThrownBy(() -> struct.getInt8(2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 1");
  }

  @Test
  void getInt8Allocated() {
    var struct = struct()
        .int8()
        .build();

    assertThat(struct.getInt8(0)).isEqualTo((byte) 0);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0});
  }

  @Test
  void getInt8NotAllocated() {
    var struct = struct()
        .allocated()
        .int8()
        .build();

    assertThatThrownBy(() -> struct.getInt8(0)).isInstanceOf(ArrayIndexOutOfBoundsException.class);
  }

  @Test
  void getInt8_constant() {
    var struct = struct()
        .primitive().constant((byte) 5).int8()
        .build();

    assertThat(struct.getInt8(0)).isEqualTo((byte) 5);
  }

  @Test
  void getInt8_index_0_not_array() {
    var struct = struct()
        .int8()
        .fromBytes(new byte[] {1})
        .build();

    assertThat(struct.getInt8(0, 0)).isEqualTo((byte) 1);
  }

  @Test
  void getInt8_index_1_not_array() {
    var struct = struct()
        .int8()
        .fromBytes(new byte[] {1})
        .build();

    assertThatThrownBy(() -> struct.getInt8(0, 1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int8Type at position 0 index: 1 length: 1");
  }

  @Test
  void getArrayWrapper() {
    var struct = struct()
        .int8()
        .int8Array(0)
        .build();

    assertThatThrownBy(() -> ((Int8Type) struct.getType(1)).get(struct, 0))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("get(Pointer, long) not supported for Int8Type. Use getInt8(Pointer, long) instead.");
  }

  @Test
  void getInt8Array() {
    var struct = struct()
        .int8()
        .int8Array(0)
        .fromBytes(new byte[] {2, 1, 2})
        .build();

    assertThat(struct.getInt8(1, 0)).isEqualTo((byte) 1);
    assertThat(struct.getInt8(1, 1)).isEqualTo((byte) 2);
  }

  @Test
  void getInt8Array_negative() {
    var struct = struct()
        .int8()
        .int8Array(0)
        .build();

    assertThatThrownBy(() -> struct.getInt8(1, -1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int8Type at position 1 index: -1 length: 0");
  }

  @Test
  void getInt8Array_greater_than_length() {
    var struct = struct()
        .int8()
        .int8Array(0)
        .fromBytes(new byte[] {2, 1, 2})
        .build();

    assertThatThrownBy(() -> struct.getInt8(1, 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int8Type at position 1 index: 2 length: 2");
  }

  @Test
  void getInt8Array_not_allocated() {
    var struct = struct()
        .allocated()
        .int8()
        .int8Array(0)
        .build();

    assertThatThrownBy(() -> struct.getInt8(1, 0))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void getInt8Array_constant() {
    var struct = struct()
        .primitive().constant((byte) 5).lengthExpression(constant(5)).int8()
        .build();

    assertThat(struct.getInt8(0, 3)).isEqualTo((byte) 5);
  }

  @Test
  void addInt8Wrapper() {
    var struct = struct()
        .int8()
        .int8Array(0)
        .build();

    assertThatThrownBy(() -> ((Int8Type) struct.getType(1)).add(struct, Byte.valueOf((byte) 1)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("add(Pointer, Byte) not supported for Int8Type. Use add(Pointer, byte) instead.");
  }

  @Test
  void addInt8Wrapper_with_index() {
    var struct = struct()
        .int8()
        .int8Array(0)
        .build();

    assertThatThrownBy(() -> ((Int8Type) struct.getType(1)).add(struct, 0, Byte.valueOf((byte) 1)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("add(Pointer, long, Byte) not supported for Int8Type. Use add(Pointer, long, byte) instead.");
  }

  @Test
  void addInt8() {
    var struct = struct()
        .int8()
        .int8Array(0)
        .fromBytes(new byte[] {2, 1, 2})
        .build();

    struct.addInt8(1, (byte) 3);
    struct.addInt8(1, (byte) 4);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {4, 1, 2, 3, 4} );
  }

  @Test
  void addInt8_position_negative() {
    var struct = struct()
        .int8()
        .int8Array(0)
        .fromBytes(new byte[] {2, 1, 2})
        .build();

    assertThatThrownBy(() -> struct.addInt8(-1, (byte) 3))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 2");
  }

  @Test
  void addInt8_position_greater_than_length() {
    var struct = struct()
        .int8()
        .int8Array(0)
        .fromBytes(new byte[] {2, 1, 2})
        .build();

    assertThatThrownBy(() -> struct.addInt8(3, (byte) 3))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 3 out of bounds for length 2");
  }

  @Test
  void addInt8_not_allocated() {
    var struct = struct()
        .allocated()
        .int8()
        .int8Array(0)
        .build();
    assertThatThrownBy(() -> struct.addInt8(1, (byte) 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void addInt8Array_constant() {
    var struct = struct()
        .primitive().constant((byte) 5).lengthExpression(constant(5)).int8()
        .build();

    assertThatThrownBy(() -> struct.addInt8(0, (byte) 3))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Int8Type at position 0 is constant index: 5 value: 3 constant: 5");
  }

  @Test
  void addInt8_not_array() {
    var struct = struct()
        .int8()
        .fromBytes(new byte[] {1})
        .build();
    assertThatThrownBy(() -> struct.addInt8(0, (byte) 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int8Type cannot add to non-array type at position 0 index: 1 length: 1");
  }

  @Test
  void addInt8_with_index() {
    var struct = struct()
        .int8()
        .int8Array(0)
        .fromBytes(new byte[] {2, 1, 2})
        .build();

    struct.addInt8(1, 0, (byte) 3);
    struct.addInt8(1, 1, (byte) 4);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {4, 3, 4, 1, 2} );
  }

  @Test
  void addInt8_with_index_negative() {
    var struct = struct()
        .int8()
        .int8Array(0)
        .fromBytes(new byte[] {2, 1, 2})
        .build();

    assertThatThrownBy(() -> struct.addInt8(1, -1, (byte) 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int8Type at position 1 index: -1 new length: 3");
  }

  @Test
  void addInt8_with_index_greater_than_length() {
    var struct = struct()
        .int8()
        .int8Array(0)
        .fromBytes(new byte[] {2, 1, 2})
        .build();

    assertThatThrownBy(() -> struct.addInt8(1, 3, (byte) 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int8Type at position 1 index: 3 new length: 3");
  }

  @Test
  void addInt8_with_index_not_allocated() {
    var struct = struct()
        .allocated()
        .int8()
        .int8Array(0)
        .build();

    assertThatThrownBy(() -> struct.addInt8(1, 0, (byte) 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void addInt8_with_index_0_not_array() {
    var struct = struct()
        .int8()
        .fromBytes(new byte[] {1})
        .build();

    assertThatThrownBy(() -> struct.addInt8(0, 0, (byte) 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int8Type cannot add to non-array type at position 0 index: 0 length: 1");
  }

  @Test
  void addInt8_with_index_1_not_array() {
    var struct = struct()
        .int8()
        .fromBytes(new byte[] {1})
        .build();

    assertThatThrownBy(() -> struct.addInt8(0, 1, (byte) 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int8Type cannot add to non-array type at position 0 index: 1 length: 1");
  }

  @Test
  void addInt8Array_with_index_constant() {
    var struct = struct()
        .primitive().constant((byte) 5).lengthExpression(constant(5)).int8()
        .build();

    assertThatThrownBy(() -> struct.addInt8(0, 3, (byte) 3))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Int8Type at position 0 is constant index: 3 value: 3 constant: 5");
  }

  @Test
  void removeInt8() {
    var struct = struct()
        .int8()
        .int8Array(0)
        .fromBytes(new byte[] {2, 1, 2})
        .build();

    struct.removeAll(1);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] { 0 } );
  }

  @Test
  void removeInt8_position_negative() {
    var struct = struct()
        .int8()
        .int8Array(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(-1))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 2");
  }

  @Test
  void removeInt8_position_greater_than_length() {
    var struct = struct()
        .int8()
        .int8Array(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 2");
  }

  @Test
  void removeInt8_not_allocated() {
    var struct = struct()
        .allocated()
        .int8()
        .int8Array(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void removeInt8_not_array() {
    var struct = struct()
        .int8()
        .build();

    assertThatThrownBy(() -> struct.removeAll(0))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int8Type cannot remove from non-array type at position 0");
  }

  @Test
  void removeInt8_with_index_0() {
    var struct = struct()
        .int8()
        .int8Array(0)
        .build();

    struct.addInt8(1, (byte) 1);
    struct.addInt8(1, (byte) 2);
    struct.remove(1, 0);

    assertThat(((Int8Type) struct.getType(1)).getInt8(struct, 0)).isEqualTo((byte) 2);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {1, 2} );
  }

  @Test
  void removeInt8_with_index_1() {
    var struct = struct()
        .int8()
        .int8Array(0)
        .build();
    struct.addInt8(1, (byte) 1);
    struct.addInt8(1, (byte) 2);

    struct.remove(1, 1);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {1, 1} );
  }

  @Test
  void removeInt8_with_index_negative() {
    var struct = struct()
        .int8()
        .int8Array(0)
        .fromBytes(new byte[] {2, 1, 2})
        .build();

    assertThatThrownBy(() -> struct.remove(1, -1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int8Type at position 1 index: -1 length: 2");
  }

  @Test
  void removeInt8_with_index_greater_than_length() {
    var struct = struct()
        .int8()
        .int8Array(0)
        .fromBytes(new byte[] {2, 1, 2})
        .build();

    assertThatThrownBy(() -> struct.remove(1, 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int8Type at position 1 index: 2 length: 2");
  }

  @Test
  void removeInt8_with_index_not_array() {
    var struct = struct()
        .int8()
        .fromBytes(new byte[] {1})
        .build();

    assertThatThrownBy(() -> struct.remove(0, 0))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Int8Type cannot remove from non-array type at position 0");
  }

  @Test
  void removeInt8Array_fixed_length() {
    var struct = struct()
        .primitive().constant((byte) 5).lengthExpression(constant(5)).int8()
        .build();

    assertThatThrownBy(() -> struct.removeAll(0))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Cannot remove fixed length array Int8Type at position 0");
  }

  @Test
  void removeInt8Array_fixed_length_with_index() {
    var struct = struct()
        .primitive().constant((byte) 5).lengthExpression(constant(5)).int8()
        .build();

    assertThatThrownBy(() -> struct.remove(0, 3))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Cannot remove element from fixed length array Int8Type at position 0 index: 3");
  }
}
