package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11.struct.Builders.struct;
import static com.github.moaxcp.x11.struct.ByteArray.ba;
import static com.github.moaxcp.x11.struct.ByteLengthListener.align;
import static com.github.moaxcp.x11.struct.Expression.constant;
import static com.github.moaxcp.x11.struct.Expression.valueOf;
import static com.github.moaxcp.x11.struct.Float32Type.float32Type;
import static com.github.moaxcp.x11.struct.Primitive.FLOAT32;
import static com.github.moaxcp.x11.struct.PrimitiveBuilder.primitive;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Float32TypeTest {

  @Test
  void constructor() {
    var type = float32Type();
    assertThat(type.getPosition()).isEqualTo(-1);
  }

  @Test
  void constructorPosition() {
    var type = float32Type(15);
    assertThat(type.getPosition()).isEqualTo(15);
  }

  @Test
  void constructorEverything() {
    var struct = struct()
        .float32()
        .primitive().constant(3.0f).lengthExpression(valueOf(0)).float32()
        .align(2)
        .build();

    assertThat(struct.getByteLength()).isEqualTo(FLOAT32.size() + 2);
    assertThat(struct.<Float32Type>getType(1)).isEqualTo(primitive().position(1).byteLengthListener(align(2)).constant(3.0f).lengthExpression(valueOf(0)).float32());
  }

  @Test
  void copy() {
    var type = float32Type();
    var copy = type.copy(15);
    assertThat(copy.getPosition()).isEqualTo(15);
  }

  @Test
  void getByteLength() {
    var struct = struct()
        .float32()
        .build();

    assertThat(struct.getByteLength()).isEqualTo(FLOAT32.size());
    assertThat(struct.getType(0).getByteLength(struct)).isEqualTo(FLOAT32.size());
  }

  @Test
  void isFixedLength() {
    var struct = struct()
        .float32()
        .build();

    assertThat(struct.getType(0).isFixedLength(struct)).isTrue();
    assertThat(struct.isFixedLength()).isTrue();
  }

  @Test
  void isFixedLengthArray() {
    var struct = struct()
        .float32Array(constant(3))
        .build();

    assertThat(struct.getType(0).isFixedLength(struct)).isTrue();
    assertThat(struct.isFixedLength()).isTrue();
  }

  @Test
  void allocate() {
    var struct = struct()
        .float32()
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 0, 0, 0});
  }

  @Test
  void allocate_with_constant() {
    var struct = struct()
        .primitive().constant(3.0f).float32()
        .build();

    // 3.0f -> 0x40400000 -> {64,64,0,0}
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {64, 64, 0, 0});
  }

  @Test
  void allocate_empty_array() {
    var struct = struct()
        .float32()
        .float32Array(0)
        .build();

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 0, 0, 0});
  }

  @Test
  void allocate_array_length_with_constant() {
    var struct = struct()
        .primitive().constant(3.0f).float32()
        .float32Array(0)
        .build();

    // 3.0f, then three zeros
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {
        64, 64, 0, 0,
        0, 0, 0, 0,
        0, 0, 0, 0,
        0, 0, 0, 0
    });
  }

  @Test
  void allocate_array_length_and_array_with_constant() {
    var struct = struct()
        .primitive().constant(3.0f).float32()
        .primitive().constant(2.0f).lengthField(0).float32()
        .build();

    // 3.0f then 3 elements of 2.0f
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {
        64, 64, 0, 0,
        64, 0, 0, 0,
        64, 0, 0, 0,
        64, 0, 0, 0
    });
  }

  @Test
  void setWrapper() {
    var struct = struct()
        .float32()
        .build();

    assertThatThrownBy(() -> ((Float32Type) struct.getType(0)).set(struct, Float.valueOf(2.0f)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("set(Pointer, Float) not supported for Float32Type. Use set(Pointer, float) instead.");
  }

  @Test
  void setFloat32() {
    var struct = struct()
        .float32()
        .build();

    struct.setFloat32(0, 2.0f);

    // 2.0f -> 0x40000000
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {64, 0, 0, 0});
  }

  @Test
  void setFloat32_position_negative() {
    var struct = struct()
        .float32()
        .build();

    assertThatThrownBy(() -> struct.setFloat32(-1, 2.0f))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 1");
  }

  @Test
  void setFloat32_position_greater_than_length() {
    var struct = struct()
        .float32()
        .build();

    assertThatThrownBy(() -> struct.setFloat32(2, 2.0f))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 1");
  }

  @Test
  void setFloat32_not_allocated() {
    var struct = struct()
        .allocated()
        .float32()
        .build();

    assertThatThrownBy(() -> struct.setFloat32(0, 2.0f))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void setFloat32_constant() {
    var struct = struct()
        .primitive().constant(3.0f).float32()
        .build();

    assertThatThrownBy(() -> struct.setFloat32(0, 2.0f))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Float32Type at position 0 is constant index: 0 value: 2.0 constant: 3.0");
  }

  @Test
  void setArrayWrapper() {
    var struct = struct()
        .float32()
        .float32Array(0)
        .build();

    assertThatThrownBy(() -> ((Float32Type) struct.getType(1)).set(struct, 0, Float.valueOf(2.0f)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("set(Pointer, long, Float) not supported for Float32Type. Use set(Pointer, long, float) instead.");
  }

  @Test
  void setFloat32Array() {
    var struct = struct()
        .float32()
        .float32Array(0)
        .float32()
        .fromBytes(new byte[] {
            63, -128, 0, 0,
            64, -128, 0, 0,
            64, 64, 0, 0
        })
        .build();

    struct.setFloat32(1, 0, 2.0f);

    assertThat(struct.getFloat32(0)).isEqualTo(1.0f);
    assertThat(struct.getFloat32(1, 0)).isEqualTo(2.0f);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {
        63, -128, 0, 0,
        64, 0, 0, 0,
        64, 64, 0, 0
    });
  }

  @Test
  void setFloat32Array_negative() {
    var struct = struct()
        .float32()
        .float32Array(0)
        .float32()
        .fromBytes(new byte[] {
            63, -128, 0, 0,
            64, -128, 0, 0,
            64, 64, 0, 0
        })
        .build();

    assertThatThrownBy(() -> struct.setFloat32(1, -1, 2.0f))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Float32Type at position 1 index: -1 length: 1");
  }

  @Test
  void setFloat32Array_greater_than_length() {
    var struct = struct()
        .float32()
        .float32Array(0)
        .float32()
        .fromBytes(new byte[] {
            63, -128, 0, 0,
            64, -128, 0, 0,
            64, 64, 0, 0
        })
        .build();

    assertThatThrownBy(() -> struct.setFloat32(1, 2, 2.0f))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Float32Type at position 1 index: 2 length: 1");

    assertThat(struct.getFloat32(0)).isEqualTo(1.0f);
    assertThat(struct.getFloat32(1, 0)).isEqualTo(4.0f);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {
        63, -128, 0, 0,
        64, -128, 0, 0,
        64, 64, 0, 0
    });
  }

  @Test
  void setFloat32Array_not_allocated() {
    var struct = struct()
        .allocated()
        .float32()
        .float32Array(0)
        .build();

    assertThatThrownBy(() -> struct.setFloat32(1, 0, 2.0f))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void setFloat32Array_index_0_not_array() {
    var struct = struct()
        .float32()
        .build();

    struct.setFloat32(0, 0, 2.0f);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {64, 0, 0, 0});
  }

  @Test
  void setFloat32Array_index_1_not_array() {
    var struct = struct()
        .float32()
        .build();

    assertThatThrownBy(() -> struct.setFloat32(0, 1, 2.0f))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Float32Type at position 0 index: 1 length: 1");
  }

  @Test
  void setFloat32Array_constant_value_and_length() {
    var struct = struct()
        .primitive().constant(3.0f).lengthExpression(constant(3)).float32()
        .build();

    assertThatThrownBy(() -> struct.setFloat32(0, 2, 2.0f))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Float32Type at position 0 is constant index: 2 value: 2.0 constant: 3.0");
  }

  @Test
  void setFloat32Array_constant_value() {
    var struct = struct()
        .float32()
        .primitive().constant(3.0f).lengthField(0).float32()
        .fromBytes(ba().float32(2).float32(3).float32(3))
        .build();

    assertThatThrownBy(() -> struct.setFloat32(1, 1, 2.0f))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Float32Type at position 1 is constant index: 1 value: 2.0 constant: 3.0");
  }

  @Test
  void setFloat32Array_constant_value_same() {
    var struct = struct()
        .float32()
        .primitive().constant(3.0f).lengthField(0).float32()
        .fromBytes(new byte[] {
            64, 0, 0, 0,
            64, -128, 0, 0,
            64, 64, 0, 0
        })
        .build();

    struct.setFloat32(1, 1, 3.0f);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {
        64, 0, 0, 0,
        64, -128, 0, 0,
        64, 64, 0, 0
    });
  }

  @Test
  @Disabled
  void setFloat32Array_set_length_field_without_adding_to_array() {
    var struct = struct()
        .float32()
        .float32Array(0)
        .build();

    assertThatThrownBy(() -> struct.setFloat32(0, 2.0f))
        .isInstanceOf(AssertionError.class)
        .hasMessage("Float32Type at position 0 is being set and will not match the array it is used as a length for.");
  }

  @Test
  void getWrapper() {
    var struct = struct()
        .float32()
        .build();

    assertThatThrownBy(() -> ((Float32Type) struct.getType(0)).get(struct))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("get(Pointer) not supported for Float32Type. Use getFloat32(Pointer) instead.");
  }

  @Test
  void getFloat32() {
    var struct = struct()
        .float32()
        .build();

    struct.setFloat32(0, 2.0f);

    assertThat(struct.getFloat32(0)).isEqualTo(2.0f);
  }

  @Test
  void getFloat32_position_negative() {
    var struct = struct()
        .float32()
        .build();

    assertThatThrownBy(() -> struct.getFloat32(-1))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 1");
  }

  @Test
  void getFloat32_position_greater_than_length() {
    var struct = struct()
        .float32()
        .build();

    assertThatThrownBy(() -> struct.getFloat32(2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 1");
  }

  @Test
  void getFloat32Allocated() {
    var struct = struct()
        .float32()
        .build();

    assertThat(struct.getFloat32(0)).isEqualTo(0.0f);
    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {0, 0, 0, 0});
  }

  @Test
  void getFloat32NotAllocated() {
    var struct = struct()
        .allocated()
        .float32()
        .build();

    assertThatThrownBy(() -> struct.getFloat32(0)).isInstanceOf(ArrayIndexOutOfBoundsException.class);
  }

  @Test
  void getFloat32_constant() {
    var struct = struct()
        .primitive().constant(3.0f).float32()
        .build();

    assertThat(struct.getFloat32(0)).isEqualTo(3.0f);
  }

  @Test
  void getFloat32_index_0_not_array() {
    var struct = struct()
        .float32()
        .fromBytes(new byte[] {0, 0, 0, 0})
        .build();

    assertThat(struct.getFloat32(0, 0)).isEqualTo(0.0f);
  }

  @Test
  void getFloat32_index_1_not_array() {
    var struct = struct()
        .float32()
        .fromBytes(new byte[] {64, 0, 0, 0})
        .build();

    assertThatThrownBy(() -> struct.getFloat32(0, 1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Float32Type at position 0 index: 1 length: 1");
  }

  @Test
  void getArrayWrapper() {
    var struct = struct()
        .float32()
        .float32Array(0)
        .build();

    assertThatThrownBy(() -> ((Float32Type) struct.getType(1)).get(struct, 0))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("get(Pointer, long) not supported for Float32Type. Use getFloat32(Pointer, long) instead.");
  }

  @Test
  void getFloat32Array() {
    var struct = struct()
        .float32()
        .float32Array(0)
        .fromBytes(new byte[] {
            64, 0, 0, 0,
            64, -128, 0, 0,
            64, 64, 0, 0
        })
        .build();

    assertThat(struct.getFloat32(1, 0)).isEqualTo(4.0f);
    assertThat(struct.getFloat32(1, 1)).isEqualTo(3.0f);
  }

  @Test
  void getFloat32Array_negative() {
    var struct = struct()
        .float32()
        .float32Array(0)
        .build();

    assertThatThrownBy(() -> struct.getFloat32(1, -1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Float32Type at position 1 index: -1 length: 0");
  }

  @Test
  void getFloat32Array_greater_than_length() {
    var struct = struct()
        .float32()
        .float32Array(0)
        .fromBytes(new byte[] {
            64, 0, 0, 0,
            64, -128, 0, 0,
            64, 64, 0, 0
        })
        .build();

    assertThatThrownBy(() -> struct.getFloat32(1, 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Float32Type at position 1 index: 2 length: 2");
  }

  @Test
  void getFloat32Array_not_allocated() {
    var struct = struct()
        .allocated()
        .float32()
        .float32Array(0)
        .build();

    assertThatThrownBy(() -> struct.getFloat32(1, 0))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void getFloat32Array_constant() {
    var struct = struct()
        .primitive().constant(3.0f).lengthExpression(constant(3)).float32()
        .build();

    assertThat(struct.getFloat32(0, 2)).isEqualTo(3.0f);
  }

  @Test
  void addFloat32Wrapper() {
    var struct = struct()
        .float32()
        .float32Array(0)
        .build();

    assertThatThrownBy(() -> ((Float32Type) struct.getType(1)).add(struct, Float.valueOf(1.0f)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("add(Pointer, Float) not supported for Float32Type. Use add(Pointer, float) instead.");
  }

  @Test
  void addFloat32Wrapper_with_index() {
    var struct = struct()
        .float32()
        .float32Array(0)
        .build();

    assertThatThrownBy(() -> ((Float32Type) struct.getType(1)).add(struct, 0, Float.valueOf(1.0f)))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("add(Pointer, long, Float) not supported for Float32Type. Use add(Pointer, long, float) instead.");
  }

  @Test
  void addFloat32() {
    var struct = struct()
        .float32()
        .float32Array(0)
        .fromBytes(new byte[] {
            64, 0, 0, 0,
            64, -128, 0, 0,
            64, 64, 0, 0
        })
        .build();

    struct.addFloat32(1, 3.0f);
    struct.addFloat32(1, 2.0f);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] {
        64, -128, 0, 0,
        64, -128, 0, 0,
        64, 64, 0, 0,
        64, 64, 0, 0,
        64, 0, 0, 0
    });
  }

  @Test
  void addFloat32_position_negative() {
    var struct = struct()
        .float32()
        .float32Array(0)
        .fromBytes(new byte[] {
            64, 0, 0, 0,
            64, -128, 0, 0,
            64, 64, 0, 0
        })
        .build();

    assertThatThrownBy(() -> struct.addFloat32(-1, 3.0f))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 2");
  }

  @Test
  void addFloat32_position_greater_than_length() {
    var struct = struct()
        .float32()
        .float32Array(0)
        .fromBytes(new byte[] {
            64, 0, 0, 0,
            64, -128, 0, 0,
            64, 64, 0, 0
        })
        .build();

    assertThatThrownBy(() -> struct.addFloat32(3, 3.0f))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 3 out of bounds for length 2");
  }

  @Test
  void addFloat32_not_allocated() {
    var struct = struct()
        .allocated()
        .float32()
        .float32Array(0)
        .build();
    assertThatThrownBy(() -> struct.addFloat32(1, 3.0f))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void addFloat32Array_constant() {
    var struct = struct()
        .primitive().constant(3.0f).lengthExpression(constant(3)).float32()
        .build();

    assertThatThrownBy(() -> struct.addFloat32(0, 5.0f))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Float32Type at position 0 is constant index: 3 value: 5.0 constant: 3.0");
  }

  @Test
  void addFloat32_not_array() {
    var struct = struct()
        .float32()
        .fromBytes(new byte[] {64, 0, 0, 0})
        .build();
    assertThatThrownBy(() -> struct.addFloat32(0, 3.0f))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Float32Type cannot add to non-array type at position 0 index: 1 length: 1");
  }

  @Test
  void addFloat32_with_index() {
    var struct = struct()
        .float32()
        .float32Array(0)
        .fromBytes(ba().float32(2).float32(2).float32(3))
        .build();

    struct.addFloat32(1, 0, 1.5f);
    struct.addFloat32(1, 1, 2.5f);

    assertThat(struct.getByteArray()).isEqualTo(ba().float32(4).float32(1.5f).float32(2.5f).float32(2f).float32(3f));
  }

  @Test
  void addFloat32_with_index_negative() {
    var struct = struct()
        .float32()
        .float32Array(0)
        .fromBytes(ba().float32(2).float32(2).float32(3))
        .build();

    assertThatThrownBy(() -> struct.addFloat32(1, -1, 3.0f))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Float32Type at position 1 index: -1 new length: 3");
  }

  @Test
  void addFloat32_with_index_greater_than_length() {
    var struct = struct()
        .float32()
        .float32Array(0)
        .fromBytes(ba().float64(2).float64(2).float64(3))
        .build();

    assertThatThrownBy(() -> struct.addFloat32(1, 3, 3.0f))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Float32Type at position 1 index: 3 new length: 3");
  }

  @Test
  void addFloat32_with_index_not_allocated() {
    var struct = struct()
        .allocated()
        .float32()
        .float32Array(0)
        .build();

    assertThatThrownBy(() -> struct.addFloat32(1, 0, 3.0f))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void addFloat32_with_index_0_not_array() {
    var struct = struct()
        .float32()
        .fromBytes(new byte[] {64, 0, 0, 0})
        .build();

    assertThatThrownBy(() -> struct.addFloat32(0, 0, 3.0f))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Float32Type cannot add to non-array type at position 0 index: 0 length: 1");
  }

  @Test
  void addFloat32_with_index_1_not_array() {
    var struct = struct()
        .float32()
        .fromBytes(new byte[] {64, 0, 0, 0})
        .build();

    assertThatThrownBy(() -> struct.addFloat32(0, 1, 3.0f))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Float32Type cannot add to non-array type at position 0 index: 1 length: 1");
  }

  @Test
  void addFloat32Array_with_index_constant() {
    var struct = struct()
        .primitive().constant(3.0f).lengthExpression(constant(3)).float32()
        .build();

    assertThatThrownBy(() -> struct.addFloat32(0, 2, 4.0f))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Float32Type at position 0 is constant index: 2 value: 4.0 constant: 3.0");
  }

  @Test
  void removeFloat32() {
    var struct = struct()
        .float32()
        .float32Array(0)
        .fromBytes(ba().float32(2).float32(2).float32(3))
        .build();

    struct.removeAll(1);

    assertThat(struct.getByteArray().getBytes()).isEqualTo(new byte[] { 0, 0, 0, 0 } );
  }

  @Test
  void removeFloat32_position_negative() {
    var struct = struct()
        .float32()
        .float32Array(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(-1))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index -1 out of bounds for length 2");
  }

  @Test
  void removeFloat32_position_greater_than_length() {
    var struct = struct()
        .float32()
        .float32Array(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(2))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index 2 out of bounds for length 2");
  }

  @Test
  void removeFloat32_not_allocated() {
    var struct = struct()
        .allocated()
        .float32()
        .float32Array(0)
        .build();

    assertThatThrownBy(() -> struct.removeAll(1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Index 0 out of bounds for length 0");
  }

  @Test
  void removeFloat32_not_array() {
    var struct = struct()
        .float32()
        .build();

    assertThatThrownBy(() -> struct.removeAll(0))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Float32Type cannot remove from non-array type at position 0");
  }

  @Test
  void removeFloat32_with_index_0() {
    var struct = struct()
        .float32()
        .float32Array(0)
        .build();

    struct.addFloat32(1, 3.0f);
    struct.addFloat32(1, 2.0f);
    struct.remove(1, 0);

    assertThat(((Float32Type) struct.getType(1)).getFloat32(struct, 0)).isEqualTo(2.0f);

    assertThat(struct.getByteArray()).isEqualTo(ba().float32(1f).float32(2.0f));
  }

  @Test
  void removeFloat32_with_index_1() {
    var struct = struct()
        .float32()
        .float32Array(0)
        .build();
    struct.addFloat32(1, 3.0f);
    struct.addFloat32(1, 2.0f);

    struct.remove(1, 1);

    assertThat(struct.getByteArray()).isEqualTo(ba().float32(1f).float32(3.0f));
  }

  @Test
  void removeFloat32_with_index_negative() {
    var struct = struct()
        .float32()
        .float32Array(0)
        .fromBytes(ba().float32(2).float32(2).float32(3))
        .build();

    assertThatThrownBy(() -> struct.remove(1, -1))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Float32Type at position 1 index: -1 length: 2");
  }

  @Test
  void removeFloat32_with_index_greater_than_length() {
    var struct = struct()
        .float32()
        .float32Array(0)
        .fromBytes(ba().float32(2).float32(2).float32(3))
        .build();

    assertThatThrownBy(() -> struct.remove(1, 2))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Float32Type at position 1 index: 2 length: 2");
  }

  @Test
  void removeFloat32_with_index_not_array() {
    var struct = struct()
        .float32()
        .fromBytes(ba().float32(2))
        .build();

    assertThatThrownBy(() -> struct.remove(0, 0))
        .isInstanceOf(ArrayIndexOutOfBoundsException.class)
        .hasMessage("Float32Type cannot remove from non-array type at position 0");
  }

  @Test
  void removeFloat32Array_fixed_length() {
    var struct = struct()
        .primitive().constant(3.0f).lengthExpression(constant(3)).float32()
        .build();

    assertThatThrownBy(() -> struct.removeAll(0))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Cannot remove fixed length array Float32Type at position 0");
  }

  @Test
  void removeFloat32Array_fixed_length_with_index() {
    var struct = struct()
        .primitive().constant(3.0f).lengthExpression(constant(3)).float32()
        .build();

    assertThatThrownBy(() -> struct.remove(0, 2))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Cannot remove element from fixed length array Float32Type at position 0 index: 2");
  }
}
