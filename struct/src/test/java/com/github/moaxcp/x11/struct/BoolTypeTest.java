package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11.struct.Builders.struct;
import static com.github.moaxcp.x11.struct.ByteArray.ba;
import static com.github.moaxcp.x11.struct.ByteLengthListener.align;
import static com.github.moaxcp.x11.struct.Expression.constant;
import static com.github.moaxcp.x11.struct.Expression.valueOf;
import static com.github.moaxcp.x11.struct.Primitive.BOOL;
import static com.github.moaxcp.x11.struct.PrimitiveBuilder.primitive;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BoolTypeTest {

  @Test
  void constructor() {
    var type = BoolType.bool();
    assertThat(type).isEqualTo(new BoolType(-1));
  }

  @Test
  void constructorPosition() {
    var type = BoolType.bool(15);
    assertThat(type).isEqualTo(new BoolType(15));
  }

  @Test
  void constructorEverything() {
    var struct = struct()
        .int8()
        .primitive().constant(true).lengthExpression(valueOf(0)).bool()
        .align(2)
        .build();

    assertThat(struct.getByteLength()).isEqualTo(BOOL.size() + 2);
    assertThat(struct.<BoolType>getType(1))
        .isEqualTo(primitive().position(1).byteLengthListener(align(2)).constant(true).lengthExpression(valueOf(0)).bool());
  }

  @Test
  void copy() {
    var type = BoolType.bool();
    var copy = type.copy(-1);
    assertThat(copy).isEqualTo(type);
  }

  @Test
  void getByteLength() {
    var struct = struct()
        .bool()
        .build();

    assertThat(struct.getByteLength()).isEqualTo(BOOL.size());
    assertThat(struct.getType(0).getByteLength(struct)).isEqualTo(BOOL.size());
  }

  @Test
  void isFixedLength() {
    var struct = struct()
        .bool()
        .build();

    assertThat(struct.getType(0).isFixedLength(struct)).isTrue();
    assertThat(struct.isFixedLength()).isTrue();
  }

  @Test
  void isFixedLengthArray() {
    var struct = struct()
        .boolArray(constant(5))
        .build();

    assertThat(struct.getType(0).isFixedLength(struct)).isTrue();
    assertThat(struct.isFixedLength()).isTrue();
  }

  @Test
  void allocate() {
    var struct = struct()
        .bool()
        .build();

    assertThat(struct.getByteArray()).isEqualTo(ba().bool(false));
  }

  @Test
  void allocate_with_constant_true() {
    var struct = struct()
        .primitive().constant(true).bool()
        .build();

    assertThat(struct.getByteArray()).isEqualTo(ba().bool(true));
  }

  @Test
  void allocate_with_constant_false() {
    var struct = struct()
        .primitive().constant(false).bool()
        .build();

    assertThat(struct.getByteArray()).isEqualTo(ba().bool(false));
  }

  @Test
  void allocate_empty_array() {
    var struct = struct()
        .int8()
        .boolArray(0)
        .build();

    assertThat(struct.getByteArray()).isEqualTo(ba().int8(0));
  }

  @Test
  void allocate_array_length_with_constant() {
    var struct = struct()
        .primitive().constant(5).int8()
        .boolArray(0)
        .build();

    assertThat(struct.getByteArray()).isEqualTo(ba().int8(5).bool(false, false, false, false, false));
  }

  @Test
  void setWrapper() {
    var struct = struct()
        .bool()
        .build();

    assertThatThrownBy(() -> ((BoolType) struct.getType(0)).set(struct, Boolean.TRUE))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("set(Pointer, Boolean) not supported for BoolType. Use set(Pointer, boolean) instead.");
  }

  @Test
  void setBool() {
    var struct = struct()
        .bool()
        .build();

    struct.setBool(0, true);

    assertThat(struct.getByteArray()).isEqualTo(ba().bool(true));
  }

  @Test
  void setBool_position_negative() {
    var struct = struct()
        .bool()
        .build();

    assertThatThrownBy(() -> struct.setBool(-1, true))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 1");
  }

  @Test
  void setBool_position_greater_than_length() {
    var struct = struct()
        .bool()
        .build();

    assertThatThrownBy(() -> struct.setBool(2, true))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 1");
  }

  @Test
  void setBool_not_allocated() {
    var struct = struct()
        .allocated()
        .bool()
        .build();

    assertThatThrownBy(() -> struct.setBool(0, true))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("cannot allocate more bytes allocated: 0, index: 0, length: 1");
  }

  @Test
  void setBool_constant() {
    var struct = struct()
        .primitive().constant(true).bool()
        .build();

    assertThatThrownBy(() -> struct.setBool(0, false))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("BoolType at position 0 is constant index: 0 value: false constant: true");
  }

  @Test
  void setArrayWrapper() {
    var struct = struct()
        .int8()
        .boolArray(0)
        .build();

    assertThatThrownBy(() -> ((BoolType) struct.getType(1)).set(struct, 0, Boolean.TRUE))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("set(Pointer, long, Boolean) not supported for BoolType. Use set(Pointer, long, boolean) instead.");
  }

  @Test
  void setBoolArray() {
    var struct = struct()
        .int8()
        .boolArray(0)
        .bool()
        .fromBytes(new byte[] {1, 1, 0})
        .build();

    struct.setBool(1, 0, false);

    assertThat(struct.getInt8(0)).isEqualTo((byte) 1);
    assertThat(struct.getBool(1, 0)).isFalse();
    assertThat(struct.getByteArray()).isEqualTo(ba().int8(1).bool(false, false));
  }

  @Test
  void setBoolArray_negative() {
    var struct = struct()
        .int8()
        .boolArray(0)
        .bool()
        .fromBytes(new byte[] {1, 1, 0})
        .build();

    assertThatThrownBy(() -> struct.setBool(1, -1, true))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("BoolType at position 1 index: -1 length: 1");
  }

  @Test
  void setBoolArray_greater_than_length() {
    var struct = struct()
        .int8()
        .boolArray(0)
        .bool()
        .fromBytes(new byte[] {1, 1, 0})
        .build();

    assertThatThrownBy(() -> struct.setBool(1, 2, true))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("BoolType at position 1 index: 2 length: 1");

    assertThat(struct.getInt8(0)).isEqualTo((byte) 1);
    assertThat(struct.getBool(1, 0)).isTrue();
    assertThat(struct.getByteArray()).isEqualTo(ba().int8(1).bool(true, false));
  }

  @Test
  void setBoolArray_not_allocated() {
    var struct = struct()
        .allocated()
        .int8()
        .boolArray(0)
        .build();

    assertThatThrownBy(() -> struct.setBool(1, 0, true))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void setBoolArray_index_0_not_array() {
    var struct = struct()
        .bool()
        .build();

    struct.setBool(0, 0, true);

    assertThat(struct.getByteArray()).isEqualTo(ba().bool(true));
  }

  @Test
  void setBoolArray_index_1_not_array() {
    var struct = struct()
        .bool()
        .build();

    assertThatThrownBy(() -> struct.setBool(0, 1, true))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("BoolType at position 0 index: 1 length: 1");
  }

  @Test
  void setBoolArray_constant_value_and_length() {
    var struct = struct()
        .primitive().constant(true).lengthExpression(constant(5)).bool()
        .build();

    assertThatThrownBy(() -> struct.setBool(0, 3, false))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("BoolType at position 0 is constant index: 3 value: false constant: true");
  }

  @Test
  void setBoolArray_constant_value() {
    var struct = struct()
        .int8()
        .primitive().constant(true).lengthField(0).bool()
        .fromBytes(new byte[] {2, 1, 1})
        .build();

    assertThatThrownBy(() -> struct.setBool(1, 1, false))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("BoolType at position 1 is constant index: 1 value: false constant: true");
  }

  @Test
  void setBoolArray_constant_value_same() {
    var struct = struct()
        .int8()
        .primitive().constant(true).lengthField(0).bool()
        .fromBytes(new byte[] {2, 1, 1})
        .build();

    struct.setBool(1, 1, true);

    assertThat(struct.getByteArray()).isEqualTo(ba().int8(2).bool(true, true));
  }

  @Test
  void setBoolArray_set_length_field_extends_array() {
    var struct = struct()
        .int8()
        .boolArray(0)
        .build();

    struct.setInt8(0, (byte) 1);
    assertThat(struct.getByteArray()).isEqualTo(ba().int8((byte) 1).bool(false));
  }

  @Test
  void setBoolArray_set_length_field_extends_array_with_constant() {
    var struct = struct()
        .int8()
        .primitive().lengthField(0).constant(true).bool()
        .build();

    struct.setInt8(0, (byte) 1);
    assertThat(struct.getByteArray()).isEqualTo(ba().int8((byte) 1).bool(true));
  }

  @Test
  void getWrapper() {
    var struct = struct()
        .bool()
        .build();

    assertThatThrownBy(() -> ((BoolType) struct.getType(0)).get(struct))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("get(Pointer) not supported for BoolType. Use getBool(Pointer) instead.");
  }

  @Test
  void getBoolean() {
    var struct = struct()
        .bool()
        .build();

    struct.setBool(0, true);

    assertThat(struct.getBool(0)).isTrue();
  }

  @Test
  void getBoolean_position_negative() {
    var struct = struct()
        .bool()
        .build();

    assertThatThrownBy(() -> struct.getBool(-1))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 1");
  }

  @Test
  void getBoolean_position_greater_than_length() {
    var struct = struct()
        .bool()
        .build();

    assertThatThrownBy(() -> struct.getBool(2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 1");
  }

  @Test
  void getBooleanAllocated() {
    var struct = struct()
        .bool()
        .build();

    assertThat(struct.getBool(0)).isFalse();
    assertThat(struct.getByteArray()).isEqualTo(ba().bool(false));
  }

  @Test
  void getBooleanNotAllocated() {
    var struct = struct()
        .allocated()
        .bool()
        .build();

    assertThatThrownBy(() -> struct.getBool(0)).isInstanceOf(ArrayIndexOutOfBoundsException.class);
  }

  @Test
  void getBoolean_constant() {
    var struct = struct()
        .primitive().constant(true).bool()
        .build();

    assertThat(struct.getBool(0)).isTrue();
  }

  @Test
  void getBoolean_index_0_not_array() {
    var struct = struct()
        .bool()
        .fromBytes(new byte[] {1})
        .build();

    assertThat(struct.getBool(0, 0)).isTrue();
  }

  @Test
  void getBoolean_index_1_not_array() {
    var struct = struct()
        .bool()
        .fromBytes(new byte[] {1})
        .build();

    assertThatThrownBy(() -> struct.getBool(0, 1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("BoolType at position 0 index: 1 length: 1");
  }

  @Test
  void getArrayWrapper() {
    var struct = struct()
        .int8()
        .boolArray(0)
        .build();

    assertThatThrownBy(() -> ((BoolType) struct.getType(1)).get(struct, 0))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("get(Pointer, long) not supported for BoolType. Use getBool(Pointer, long) instead.");
  }

  @Test
  void getBooleanArray() {
    var struct = struct()
        .int8()
        .boolArray(0)
        .fromBytes(new byte[] {2, 1, 0})
        .build();

    assertThat(struct.getBool(1, 0)).isTrue();
    assertThat(struct.getBool(1, 1)).isFalse();
  }

  @Test
  void getBooleanArray_negative() {
    var struct = struct()
        .int8()
        .boolArray(0)
        .build();

    assertThatThrownBy(() -> struct.getBool(1, -1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("BoolType at position 1 index: -1 length: 0");
  }

  @Test
  void getBooleanArray_greater_than_length() {
    var struct = struct()
        .int8()
        .boolArray(0)
        .fromBytes(new byte[] {2, 1, 0})
        .build();

    assertThatThrownBy(() -> struct.getBool(1, 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("BoolType at position 1 index: 2 length: 2");
  }

  @Test
  void getBooleanArray_not_allocated() {
    var struct = struct()
        .allocated()
        .int8()
        .boolArray(0)
        .build();

    assertThatThrownBy(() -> struct.getBool(1, 0))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void getBooleanArray_constant() {
    var struct = struct()
        .primitive().constant(true).lengthExpression(constant(5)).bool()
        .build();

    assertThat(struct.getBool(0, 3)).isTrue();
  }

  @Test
  void addBoolWrapper() {
    var struct = struct()
        .int8()
        .boolArray(0)
        .build();

    assertThatThrownBy(() -> ((BoolType) struct.getType(1)).add(struct, Boolean.TRUE))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("add(Pointer, Boolean) not supported for BoolType. Use add(Pointer, boolean) instead.");
  }

  @Test
  void addBoolWrapper_with_index() {
    var struct = struct()
        .int8()
        .boolArray(0)
        .build();

    assertThatThrownBy(() -> ((BoolType) struct.getType(1)).add(struct, 0, Boolean.TRUE))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("add(Pointer, long, Boolean) not supported for BoolType. Use add(Pointer, long, boolean) instead.");
  }

  @Test
  void addBool() {
    var struct = struct()
        .int8()
        .boolArray(0)
        .fromBytes(new byte[] {2, 1, 0})
        .build();

    struct.addBool(1, true);
    struct.addBool(1, false);

    assertThat(struct.getByteArray()).isEqualTo(ba().int8(4).bool(true, false, true, false));
  }

  @Test
  void addBool_position_negative() {
    var struct = struct()
        .bool()
        .boolArray(0)
        .fromBytes(new byte[] {2, 1, 0})
        .build();

    assertThatThrownBy(() -> struct.addBool(-1, true))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 2");
  }

  @Test
  void addBool_position_greater_than_length() {
    var struct = struct()
        .bool()
        .boolArray(0)
        .fromBytes(new byte[] {2, 1, 0})
        .build();

    assertThatThrownBy(() -> struct.addBool(3, true))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 3 out of bounds for length 2");
  }

  @Test
  void addBool_not_allocated() {
    var struct = struct()
        .allocated()
        .int8()
        .boolArray(0)
        .build();

    assertThatThrownBy(() -> struct.addBool(1, true))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void addBoolArray_constant() {
    var struct = struct()
        .primitive().constant(true).lengthExpression(constant(5)).bool()
        .build();

    assertThatThrownBy(() -> struct.addBool(0, false))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("BoolType at position 0 is constant index: 5 value: false constant: true");
  }

  @Test
  void addBool_not_array() {
    var struct = struct()
        .bool()
        .fromBytes(new byte[] {1})
        .build();
    assertThatThrownBy(() -> struct.addBool(0, true))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("BoolType cannot add to non-array type at position 0 index: 1 length: 1");
  }

  @Test
  void addBool_with_index() {
    var struct = struct()
        .int8()
        .boolArray(0)
        .fromBytes(new byte[] {2, 1, 0})
        .build();

    struct.addBool(1, 0, true);
    struct.addBool(1, 1, false);

    assertThat(struct.getByteArray()).isEqualTo(ba().int8(4).bool(true, false, true, false));
  }

  @Test
  void addBool_with_index_negative() {
    var struct = struct()
    .int8()
    .boolArray(0)
    .fromBytes(new byte[] {2, 1, 0})
    .build();

    assertThatThrownBy(() -> struct.addBool(1, -1, true))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("BoolType at position 1 index: -1 new length: 3");
  }

  @Test
  void addBool_with_index_greater_than_length() {
    var struct = struct()
        .int8()
        .boolArray(0)
        .fromBytes(new byte[] {2, 1, 0})
        .build();

    assertThatThrownBy(() -> struct.addBool(1, 3, true))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("BoolType at position 1 index: 3 new length: 3");
  }

  @Test
  void addBool_with_index_not_allocated() {
    var struct = struct()
        .allocated()
        .int8()
        .boolArray(0)
        .build();

    assertThatThrownBy(() -> struct.addBool(1, 0, true))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void addBool_with_index_0_not_array() {
    var struct = struct()
        .bool()
        .fromBytes(new byte[] {1})
        .build();

    assertThatThrownBy(() -> struct.addBool(0, 0, true))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("BoolType cannot add to non-array type at position 0 index: 0 length: 1");
  }

  @Test
  void addBool_with_index_1_not_array() {
    var struct = struct()
        .bool()
        .fromBytes(new byte[] {1})
        .build();

    assertThatThrownBy(() -> struct.addBool(0, 1, true))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("BoolType cannot add to non-array type at position 0 index: 1 length: 1");
  }

  @Test
  void addBoolArray_with_index_constant() {
    var struct = struct()
        .primitive().constant(true).lengthExpression(constant(5)).bool()
        .build();

    assertThatThrownBy(() -> struct.addBool(0, 3, false))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("BoolType at position 0 is constant index: 3 value: false constant: true");
  }

  @Test
  void removeBool() {
    var struct = struct()
        .int8()
        .boolArray(0)
        .fromBytes(new byte[] {2, 1, 0})
        .build();

    struct.removeAll(1);

    assertThat(struct.getByteArray()).isEqualTo(ba().int8(0));
  }

  @Test
  void removeBool_position_negative() {
    var struct = struct()
        .int8()
        .boolArray(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(-1))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 2");
  }

  @Test
  void removeBool_position_greater_than_length() {
    var struct = struct()
        .int8()
        .boolArray(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 2");
  }

  @Test
  void removeBool_not_allocated() {
    var struct = struct()
        .allocated()
        .int8()
        .boolArray(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void removeBool_not_array() {
    var struct = struct()
        .bool()
        .build();

    assertThatThrownBy(() -> struct.removeAll(0))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("BoolType cannot remove from non-array type at position 0");
  }

  @Test
  void removeBool_with_index_0() {
    var struct = struct()
        .int8()
        .boolArray(0)
        .build();

    struct.addBool(1, true);
    struct.addBool(1, false);
    struct.remove(1, 0);

    assertThat(((BoolType) struct.getType(1)).getBoolean(struct, 0)).isFalse();

    assertThat(struct.getByteArray()).isEqualTo(ba().int8(1).bool(false));
  }

  @Test
  void removeBool_with_index_1() {
    var struct = struct()
        .int8()
        .boolArray(0)
        .build();
    struct.addBool(1, true);
    struct.addBool(1, false);

    struct.remove(1, 1);

    assertThat(struct.getByteArray()).isEqualTo(ba().int8(1).bool(true));
  }

  @Test
  void removeBool_with_index_negative() {
    var struct = struct()
        .int8()
        .boolArray(0)
        .fromBytes(new byte[] {2, 1, 0})
        .build();

    assertThatThrownBy(() -> struct.remove(1, -1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("BoolType at position 1 index: -1 length: 2");
  }

  @Test
  void removeBool_with_index_greater_than_length() {
    var struct = struct()
        .int8()
        .boolArray(0)
        .fromBytes(new byte[] {2, 1, 0})
        .build();

    assertThatThrownBy(() -> struct.remove(1, 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("BoolType at position 1 index: 2 length: 2");
  }

  @Test
  void removeBool_with_index_not_array() {
    var struct = struct()
        .bool()
        .fromBytes(new byte[] {1})
        .build();

    assertThatThrownBy(() -> struct.remove(0, 0))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("BoolType cannot remove from non-array type at position 0");
  }

  @Test
  void removeBoolArray_fixed_length() {
    var struct = struct()
        .primitive().constant(true).lengthExpression(constant(5)).bool()
        .build();

    assertThatThrownBy(() -> struct.removeAll(0))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Cannot remove fixed length array BoolType at position 0");
  }

  @Test
  void removeBoolArray_fixed_length_with_index() {
    var struct = struct()
        .primitive().constant(true).lengthExpression(constant(5)).bool()
        .build();

    assertThatThrownBy(() -> struct.remove(0, 3))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Cannot remove element from fixed length array BoolType at position 0 index: 3");
  }
}
