package com.github.moaxcp.x11.struct;

import com.github.moaxcp.x11.struct.ArrayLengthListener.ArrayLengthReason;
import com.github.moaxcp.x11.struct.ValueChangeListener.ValueChangeReason;
import org.jspecify.annotations.Nullable;

import static com.github.moaxcp.x11.struct.Primitive.INT8;
import static com.github.moaxcp.x11.struct.ValueChangeListener.ValueChangeReason.SET_BY_ARRAY_LENGTH;
import static com.github.moaxcp.x11.struct.ValueChangeListener.ValueChangeReason.SET_VALUE;

public final class Int8Type extends NumberType<Int8Type, Byte> {

  public static Int8Type int8() {
    return int8(-1);
  }

  public static Int8Type int8(int position) {
    return new Int8Type(position);
  }

  public Int8Type(int position, @Nullable Byte constantValue, @Nullable Expression lengthExpression) {
    super(position, INT8, constantValue, lengthExpression);
  }

  public Int8Type(int position) {
    super(position, INT8);
  }

  @Override
  protected Int8Type copy(int position) {
    return new Int8Type(position, constantValue, lengthExpression);
  }

  public byte getInt8(Pointer<?, ? extends Type<?>> pointer) {
    return pointer.getByteArray().getInt8(getOffset(pointer));
  }

  public byte getInt8(Pointer<?, ? extends Type<?>> pointer, long index) {
    checkIndex(pointer, index);
    return pointer.getByteArray().getInt8(getOffset(pointer, index));
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, byte value) {
    setUnchecked(SET_VALUE, pointer, 0, value);
  }

  void setForArrayLength(Pointer<?, ? extends Type<?>> pointer, long value) {
    setUnchecked(SET_BY_ARRAY_LENGTH, pointer, 0, (byte) value);
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, long index, byte value) {
    checkIndex(pointer, index);
    setUnchecked(SET_VALUE, pointer, index, value);
  }

  private void setUnchecked(ValueChangeReason reason, Pointer<?, ? extends Type<?>> pointer, long index, byte value) {
    checkConstant(pointer, index, value);
    if (!valueChangeListeners.isEmpty()) {
      var old = pointer.getByteArray().getInt8(getOffset(pointer, index));
      pointer.getByteArray().setInt8(getOffset(pointer, index), value);
      notifyValueChange(reason, pointer, index, old, value);
    }
    pointer.getByteArray().setInt8(getOffset(pointer, index), value);
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, byte value) {
    add(pointer, getArrayLength(pointer), value);
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, long index, byte value) {
    if (!isArray()) {
      throw new ArrayIndexOutOfBoundsException(getClass().getSimpleName() + " cannot add to non-array type at position " + getPosition() + " index: " + index + " length: " + 1);
    }
    allocate(pointer, index);
    setUnchecked(SET_VALUE, pointer, index, value);
  }

  public void allocate(Pointer<?, ? extends Type<?>> pointer) {
    if(isArray()) {
        long length = getArrayLength(pointer);
        for (long i = 0; i < length; i++) {
          pointer.getByteArray().addInt8(getOffset(pointer, i), constantValue != null ? constantValue : 0);
        }
    } else {
      pointer.getByteArray().addInt8(getOffset(pointer, 0), constantValue != null ? constantValue : 0);
    }
  }

  @Override
  protected void allocate(ArrayLengthReason reason, Pointer<?, ? extends Type<?>> pointer, long index) {
    callWithArrayLengthChange(reason, pointer, 1, () -> {
      callWithByteLengthChange(pointer, () -> {
        checkIndexAllocate(pointer, index);
        pointer.getByteArray().addInt8(getOffset(pointer, index), constantValue != null ? constantValue : 0);
      });
    });
  }
}
