package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11.struct.Builders.struct;
import static com.github.moaxcp.x11.struct.ByteLengthListener.align;
import static com.github.moaxcp.x11.struct.Expression.constant;
import static com.github.moaxcp.x11.struct.Expression.valueOf;
import static com.github.moaxcp.x11.struct.Primitive.UINT8;
import static com.github.moaxcp.x11.struct.PrimitiveBuilder.primitive;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Uint8TypeTest {

  @Test
  void constructor() {
    var type = new Uint8Type(-1);
    assertThat(type.getPosition()).isEqualTo(-1);
  }

  @Test
  void constructorPosition() {
    var type = new Uint8Type(15);
    assertThat(type.getPosition()).isEqualTo(15);
  }

  @Test
  void constructorEverything() {
    var struct = struct()
        .uint8()
        .primitive().constant((short) 5).lengthExpression(valueOf(0)).uint8()
        .align(2)
        .build();

    assertThat(struct.getByteLength()).isEqualTo(UINT8.size() + 2);
    assertThat(struct.<Uint8Type>getType(1))
        .isEqualTo(primitive().position(1).byteLengthListener(align(2)).constant((short) 5).lengthExpression(valueOf(0)).uint8());
  }

  @Test
  void copy() {
    var type = new Uint8Type(-1);
    var copy = type.copy(15);
    assertThat(copy.getPosition()).isEqualTo(15);
  }

  @Test
  void getByteLength() {
    var struct = struct()
        .uint8()
        .build();

    assertThat(struct.getByteLength()).isEqualTo(UINT8.size());
    assertThat(struct.getType(0).getByteLength(struct)).isEqualTo(UINT8.size());
  }

  @Test
  void isFixedLength() {
    var struct = struct()
        .uint8()
        .build();

    assertThat(struct.getType(0).isFixedLength(struct)).isTrue();
    assertThat(struct.isFixedLength()).isTrue();
  }

  @Test
  void isFixedLengthArray() {
    var struct = struct()
        .uint8Array(constant(5))
        .build();

    assertThat(struct.getType(0).isFixedLength(struct)).isTrue();
    assertThat(struct.isFixedLength()).isTrue();
  }

  @Test
  void allocate() {
    var struct = struct()
        .uint8()
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0});
  }

  @Test
  void allocate_with_constant() {
    var struct = struct()
        .primitive().constant((short) 5).uint8()
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {5});
  }

  @Test
  void allocate_empty_array() {
    var struct = struct()
        .uint8()
        .uint8Array(0)
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0});
  }

  @Test
  void allocate_array_length_with_constant() {
    var struct = struct()
        .primitive().constant((short) 5).uint8()
        .uint8Array(0)
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {5, 0, 0, 0, 0, 0});
  }

  @Test
  void setWrapper() {
    var struct = struct()
        .uint8()
        .build();

    assertThatThrownBy(() -> ((Uint8Type) struct.getType(0)).set(struct, Short.valueOf((short) 2)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("set(Pointer, Short) not supported for Uint8Type. Use set(Pointer, short) instead.");
  }

  @Test
  void setUint8() {
    var struct = struct()
        .uint8()
        .build();

    struct.setUint8(0, (byte) 2);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {2});
  }

  @Test
  void setUint8_position_negative() {
    var struct = struct()
        .uint8()
        .build();

    assertThatThrownBy(() -> struct.setUint8(-1, (byte) 2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 1");
  }

  @Test
  void setUint8_position_greater_than_length() {
    var struct = struct()
        .uint8()
        .build();

    assertThatThrownBy(() -> struct.setUint8(2, (byte) 2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 1");
  }

  @Test
  void setUint8_not_allocated() {
    var struct = struct()
        .allocated()
        .uint8()
        .build();

    assertThatThrownBy(() -> struct.setUint8(0, (byte) 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void setUint8_constant() {
    var struct = struct()
        .primitive().constant((short) 5).uint8()
        .build();

    assertThatThrownBy(() -> struct.setUint8(0, (short) 2))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Uint8Type at position 0 is constant index: 0 value: 2 constant: 5");
  }

  @Test
  void setArrayWrapper() {
    var struct = struct()
        .uint8()
        .uint8Array(0)
        .build();

    assertThatThrownBy(() -> ((Uint8Type) struct.getType(1)).set(struct, 0, Short.valueOf((short) 2)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("set(Pointer, long, Short) not supported for Uint8Type. Use set(Pointer, long, short) instead.");
  }

  @Test
  void setUint8Array() {
    var struct = struct()
        .uint8()
        .uint8Array(0)
        .uint8()
        .fromBytes(new byte[] {1, 2, 3})
        .build();

    struct.setUint8(1, 0, (byte) 5);

    assertThat(struct.getUint8(0)).isEqualTo((byte) 1);
    assertThat(struct.getUint8(1, 0)).isEqualTo((byte) 5);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {1, 5, 3});
  }

  @Test
  void setUint8Array_negative() {
    var struct = struct()
        .uint8()
        .uint8Array(0)
        .uint8()
        .fromBytes(new byte[] {1, 2, 3})
        .build();

    assertThatThrownBy(() -> struct.setUint8(1, -1, (byte) 5))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Uint8Type at position 1 index: -1 length: 1");
  }

  @Test
  void setUint8Array_greater_than_length() {
    var struct = struct()
        .uint8()
        .uint8Array(0)
        .uint8()
        .fromBytes(new byte[] {1, 2, 3})
        .build();

    assertThatThrownBy(() -> struct.setUint8(1, 2, (byte) 5))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint8Type at position 1 index: 2 length: 1");

    assertThat(struct.getUint8(0)).isEqualTo((byte) 1);
    assertThat(struct.getUint8(1, 0)).isEqualTo((byte) 2);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {1, 2, 3});
  }

  @Test
  void setUint8Array_not_allocated() {
    var struct = struct()
        .allocated()
        .uint8()
        .uint8Array(0)
        .build();

    assertThatThrownBy(() -> struct.setUint8(1, 0, (byte) 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void setUint8Array_index_0_not_array() {
    var struct = struct()
        .uint8()
        .build();

    struct.setUint8(0, 0, (byte) 2);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {2});
  }

  @Test
  void setUint8Array_index_1_not_array() {
    var struct = struct()
        .uint8()
        .build();

    assertThatThrownBy(() -> struct.setUint8(0, 1, (byte) 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint8Type at position 0 index: 1 length: 1");
  }

  @Test
  void setUint8Array_constant_value_and_length() {
    var struct = struct()
        .primitive().constant((short) 5).lengthExpression(constant(5)).uint8()
        .build();

    assertThatThrownBy(() -> struct.setUint8(0, 3, (short) 2))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Uint8Type at position 0 is constant index: 3 value: 2 constant: 5");
  }

  @Test
  void setUint8Array_constant_value() {
    var struct = struct()
        .uint8()
        .primitive().constant((short) 5).lengthField(0).uint8()
        .fromBytes(new byte[] {2, 5, 5})
        .build();

    assertThatThrownBy(() -> struct.setUint8(1, 1, (byte) 2))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Uint8Type at position 1 is constant index: 1 value: 2 constant: 5");
  }

  @Test
  void setUint8Array_constant_value_same() {
    var struct = struct()
        .uint8()
        .primitive().constant((short) 5).lengthField(0).uint8()
        .fromBytes(new byte[] {2, 5, 5})
        .build();

    struct.setUint8(1, 1, (short) 5);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {2, 5, 5});
  }

  @Test
  @Disabled
  void setUint8Array_set_length_field_without_adding_to_array() {
    var struct = struct()
        .uint8()
        .uint8Array(0)
        .build();

    assertThatThrownBy(() -> struct.setUint8(0, (short) 2))
        .isInstanceOf(AssertionError.class)
        .hasMessage("Uint8Type at position 0 is being set and will not match the array it is used as a length for.");
  }

  @Test
  void getWrapper() {
    var struct = struct()
        .uint8()
        .build();

    assertThatThrownBy(() -> ((Uint8Type) struct.getType(0)).get(struct))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("get(Pointer) not supported for Uint8Type. Use getUint8(Pointer) instead.");
  }

  @Test
  void getUint8() {
    var struct = struct()
        .uint8()
        .build();

    struct.setUint8(0, (byte) 2);

    assertThat(struct.getUint8(0)).isEqualTo((byte) 2);
  }

  @Test
  void getUint8_position_negative() {
    var struct = struct()
        .uint8()
        .build();

    assertThatThrownBy(() -> struct.getUint8(-1))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 1");
  }

  @Test
  void getUint8_position_greater_than_length() {
    var struct = struct()
        .uint8()
        .build();

    assertThatThrownBy(() -> struct.getUint8(2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 1");
  }

  @Test
  void getUint8Allocated() {
    var struct = struct()
        .uint8()
        .build();

    assertThat(struct.getUint8(0)).isEqualTo((byte) 0);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0});
  }

  @Test
  void getUint8NotAllocated() {
    var struct = struct()
        .allocated()
        .uint8()
        .build();

    assertThatThrownBy(() -> struct.getUint8(0)).isInstanceOf(ArrayIndexOutOfBoundsException.class);
  }

  @Test
  void getUint8_constant() {
    var struct = struct()
        .primitive().constant((short) 5).uint8()
        .build();

    assertThat(struct.getUint8(0)).isEqualTo((short) 5);
  }

  @Test
  void getUint8_index_0_not_array() {
    var struct = struct()
        .uint8()
        .fromBytes(new byte[] {1})
        .build();

    assertThat(struct.getUint8(0, 0)).isEqualTo((byte) 1);
  }

  @Test
  void getUint8_index_1_not_array() {
    var struct = struct()
        .uint8()
        .fromBytes(new byte[] {1})
        .build();

    assertThatThrownBy(() -> struct.getUint8(0, 1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint8Type at position 0 index: 1 length: 1");
  }

  @Test
  void getArrayWrapper() {
    var struct = struct()
        .uint8()
        .uint8Array(0)
        .build();

    assertThatThrownBy(() -> ((Uint8Type) struct.getType(1)).get(struct, 0))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("get(Pointer, long) not supported for Uint8Type. Use getUint8(Pointer, long) instead.");
  }

  @Test
  void getUint8Array() {
    var struct = struct()
        .uint8()
        .uint8Array(0)
        .fromBytes(new byte[] {2, 1, 2})
        .build();

    assertThat(struct.getUint8(1, 0)).isEqualTo((byte) 1);
    assertThat(struct.getUint8(1, 1)).isEqualTo((byte) 2);
  }

  @Test
  void getUint8Array_negative() {
    var struct = struct()
        .uint8()
        .uint8Array(0)
        .build();

    assertThatThrownBy(() -> struct.getUint8(1, -1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint8Type at position 1 index: -1 length: 0");
  }

  @Test
  void getUint8Array_greater_than_length() {
    var struct = struct()
        .uint8()
        .uint8Array(0)
        .fromBytes(new byte[] {2, 1, 2})
        .build();

    assertThatThrownBy(() -> struct.getUint8(1, 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint8Type at position 1 index: 2 length: 2");
  }

  @Test
  void getUint8Array_not_allocated() {
    var struct = struct()
        .allocated()
        .uint8()
        .uint8Array(0)
        .build();

    assertThatThrownBy(() -> struct.getUint8(1, 0))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void getUint8Array_constant() {
    var struct = struct()
        .primitive().constant((short) 5).lengthExpression(constant(5)).uint8()
        .build();

    assertThat(struct.getUint8(0, 3)).isEqualTo((byte) 5);
  }

  @Test
  void addUint8Wrapper() {
    var struct = struct()
        .uint8()
        .uint8Array(0)
        .build();

    assertThatThrownBy(() -> ((Uint8Type) struct.getType(1)).add(struct, Short.valueOf((short) 1)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("add(Pointer, Short) not supported for Uint8Type. Use add(Pointer, short) instead.");
  }

  @Test
  void addUint8Wrapper_with_index() {
    var struct = struct()
        .uint8()
        .uint8Array(0)
        .build();

    assertThatThrownBy(() -> ((Uint8Type) struct.getType(1)).add(struct, 0, Short.valueOf((short) 1)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("add(Pointer, long, Short) not supported for Uint8Type. Use add(Pointer, long, short) instead.");
  }

  @Test
  void addUint8() {
    var struct = struct()
        .uint8()
        .uint8Array(0)
        .fromBytes(new byte[] {2, 1, 2})
        .build();

    struct.addUint8(1, (byte) 3);
    struct.addUint8(1, (byte) 4);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {4, 1, 2, 3, 4} );
  }

  @Test
  void addUint8_position_negative() {
    var struct = struct()
        .uint8()
        .uint8Array(0)
        .fromBytes(new byte[] {2, 1, 2})
        .build();

    assertThatThrownBy(() -> struct.addUint8(-1, (byte) 3))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 2");
  }

  @Test
  void addUint8_position_greater_than_length() {
    var struct = struct()
        .uint8()
        .uint8Array(0)
        .fromBytes(new byte[] {2, 1, 2})
        .build();

    assertThatThrownBy(() -> struct.addUint8(3, (byte) 3))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 3 out of bounds for length 2");
  }

  @Test
  void addUint8_not_allocated() {
    var struct = struct()
        .allocated()
        .uint8()
        .uint8Array(0)
        .build();
    assertThatThrownBy(() -> struct.addUint8(1, (byte) 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void addUint8Array_constant() {
    var struct = struct()
        .primitive().constant((short) 5).lengthExpression(constant(5)).uint8()
        .build();

    assertThatThrownBy(() -> struct.addUint8(0, (byte) 3))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Uint8Type at position 0 is constant index: 5 value: 3 constant: 5");
  }

  @Test
  void addUint8_not_array() {
    var struct = struct()
        .uint8()
        .fromBytes(new byte[] {1})
        .build();
    assertThatThrownBy(() -> struct.addUint8(0, (byte) 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint8Type cannot add to non-array type at position 0 index: 1 length: 1");
  }

  @Test
  void addUint8_with_index() {
    var struct = struct()
        .uint8()
        .uint8Array(0)
        .fromBytes(new byte[] {2, 1, 2})
        .build();

    struct.addUint8(1, 0, (byte) 3);
    struct.addUint8(1, 1, (byte) 4);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {4, 3, 4, 1, 2} );
  }

  @Test
  void addUint8_with_index_negative() {
    var struct = struct()
        .uint8()
        .uint8Array(0)
        .fromBytes(new byte[] {2, 1, 2})
        .build();

    assertThatThrownBy(() -> struct.addUint8(1, -1, (byte) 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint8Type at position 1 index: -1 new length: 3");
  }

  @Test
  void addUint8_with_index_greater_than_length() {
    var struct = struct()
        .uint8()
        .uint8Array(0)
        .fromBytes(new byte[] {2, 1, 2})
        .build();

    assertThatThrownBy(() -> struct.addUint8(1, 3, (byte) 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint8Type at position 1 index: 3 new length: 3");
  }

  @Test
  void addUint8_with_index_not_allocated() {
    var struct = struct()
        .allocated()
        .uint8()
        .uint8Array(0)
        .build();

    assertThatThrownBy(() -> struct.addUint8(1, 0, (byte) 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void addUint8_with_index_0_not_array() {
    var struct = struct()
        .uint8()
        .fromBytes(new byte[] {1})
        .build();

    assertThatThrownBy(() -> struct.addUint8(0, 0, (byte) 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint8Type cannot add to non-array type at position 0 index: 0 length: 1");
  }

  @Test
  void addUint8_with_index_1_not_array() {
    var struct = struct()
        .uint8()
        .fromBytes(new byte[] {1})
        .build();

    assertThatThrownBy(() -> struct.addUint8(0, 1, (byte) 3))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint8Type cannot add to non-array type at position 0 index: 1 length: 1");
  }

  @Test
  void addUint8Array_with_index_constant() {
    var struct = struct()
        .primitive().constant((short) 5).lengthExpression(constant(5)).uint8()
        .build();

    assertThatThrownBy(() -> struct.addUint8(0, 3, (short) 3))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Uint8Type at position 0 is constant index: 3 value: 3 constant: 5");
  }

  @Test
  void removeUint8() {
    var struct = struct()
        .uint8()
        .uint8Array(0)
        .fromBytes(new byte[] {2, 1, 2})
        .build();

    struct.removeAll(1);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] { 0 } );
  }

  @Test
  void removeUint8_position_negative() {
    var struct = struct()
        .uint8()
        .uint8Array(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(-1))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 2");
  }

  @Test
  void removeUint8_position_greater_than_length() {
    var struct = struct()
        .uint8()
        .uint8Array(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 2");
  }

  @Test
  void removeUint8_not_allocated() {
    var struct = struct()
        .allocated()
        .uint8()
        .uint8Array(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void removeUint8_not_array() {
    var struct = struct()
        .uint8()
        .build();

    assertThatThrownBy(() -> struct.removeAll(0))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint8Type cannot remove from non-array type at position 0");
  }

  @Test
  void removeUint8_with_index_0() {
    var struct = struct()
        .uint8()
        .uint8Array(0)
        .build();

    struct.addUint8(1, (byte) 1);
    struct.addUint8(1, (byte) 2);
    struct.remove(1, 0);

    assertThat(((Uint8Type) struct.getType(1)).getUint8(struct, 0)).isEqualTo((byte) 2);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {1, 2} );
  }

  @Test
  void removeUint8_with_index_1() {
    var struct = struct()
        .uint8()
        .uint8Array(0)
        .build();
    struct.addUint8(1, (byte) 1);
    struct.addUint8(1, (byte) 2);

    struct.remove(1, 1);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {1, 1} );
  }

  @Test
  void removeUint8_with_index_negative() {
    var struct = struct()
        .uint8()
        .uint8Array(0)
        .fromBytes(new byte[] {2, 1, 2})
        .build();

    assertThatThrownBy(() -> struct.remove(1, -1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint8Type at position 1 index: -1 length: 2");
  }

  @Test
  void removeUint8_with_index_greater_than_length() {
    var struct = struct()
        .uint8()
        .uint8Array(0)
        .fromBytes(new byte[] {2, 1, 2})
        .build();

    assertThatThrownBy(() -> struct.remove(1, 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint8Type at position 1 index: 2 length: 2");
  }

  @Test
  void removeUint8_with_index_not_array() {
    var struct = struct()
        .uint8()
        .fromBytes(new byte[] {1})
        .build();

    assertThatThrownBy(() -> struct.remove(0, 0))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Uint8Type cannot remove from non-array type at position 0");
  }

  @Test
  void removeUint8Array_fixed_length() {
    var struct = struct()
        .primitive().constant((short) 5).lengthExpression(constant(5)).uint8()
        .build();

    assertThatThrownBy(() -> struct.removeAll(0))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Cannot remove fixed length array Uint8Type at position 0");
  }

  @Test
  void removeUint8Array_fixed_length_with_index() {
    var struct = struct()
        .primitive().constant((short) 5).lengthExpression(constant(5)).uint8()
        .build();

    assertThatThrownBy(() -> struct.remove(0, 3))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Cannot remove element from fixed length array Uint8Type at position 0 index: 3");
  }
}
