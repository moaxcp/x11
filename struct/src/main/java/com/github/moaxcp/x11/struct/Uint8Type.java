package com.github.moaxcp.x11.struct;

import com.github.moaxcp.x11.struct.ArrayLengthListener.ArrayLengthReason;
import com.github.moaxcp.x11.struct.ValueChangeListener.ValueChangeReason;
import org.jspecify.annotations.Nullable;

import static com.github.moaxcp.x11.struct.Primitive.UINT8;
import static com.github.moaxcp.x11.struct.ValueChangeListener.ValueChangeReason.SET_BY_ARRAY_LENGTH;
import static com.github.moaxcp.x11.struct.ValueChangeListener.ValueChangeReason.SET_VALUE;

public final class Uint8Type extends NumberType<Uint8Type, Short> {

  public static Uint8Type uint8Type() {
    return uint8Type(-1);
  }

  public static Uint8Type uint8Type(int position) {
    return new Uint8Type(position);
  }

  public Uint8Type(int position, @Nullable Short constantValue, @Nullable Expression lengthExpression) {
    super(position, UINT8, constantValue, lengthExpression);
  }

  public Uint8Type(int position) {
    super(position, UINT8);
  }

  @Override
  protected Uint8Type copy(int position) {
    return new Uint8Type(position, constantValue, lengthExpression);
  }

  public short getUint8(Pointer<?, ? extends Type<?>> pointer) {
    return pointer.getByteArray().getUint8(getOffset(pointer));
  }

  public short getUint8(Pointer<?, ? extends Type<?>> pointer, long index) {
    checkIndex(pointer, index);
    return pointer.getByteArray().getUint8(getOffset(pointer, index));
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, short value) {
    setUnchecked(SET_VALUE, pointer, 0, value);
  }

  void setForArrayLength(Pointer<?, ? extends Type<?>> pointer, long value) {
    setUnchecked(SET_BY_ARRAY_LENGTH, pointer, 0, (short) value);
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, long index, short value) {
    checkIndex(pointer, index);
    setUnchecked(SET_VALUE, pointer, index, value);
  }

  private void setUnchecked(ValueChangeReason reason, Pointer<?, ? extends Type<?>> pointer, long index, short value) {
    checkConstant(pointer, index, value);
    if (!valueChangeListeners.isEmpty()) {
      var old = pointer.getByteArray().getUint8(getOffset(pointer, index));
      pointer.getByteArray().setUint8(getOffset(pointer, index), value);
      notifyValueChange(reason, pointer, index, old, value);
    }
    pointer.getByteArray().setUint8(getOffset(pointer, index), value);
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, short value) {
    add(pointer, getArrayLength(pointer), value);
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, long index, short value) {
    if (!isArray()) {
      throw new ArrayIndexOutOfBoundsException(getClass().getSimpleName() + " cannot add to non-array type at position " + getPosition() + " index: " + index + " length: " + 1);
    }
    allocate(pointer, index);
    setUnchecked(SET_VALUE, pointer, index, value);
  }

  public void allocate(Pointer<?, ? extends Type<?>> pointer) {
    if(isArray()) {
      long length = getArrayLength(pointer);
      for (int i = 0; i < length; i++) {
        pointer.getByteArray().addUint8(getOffset(pointer, i), constantValue != null ? constantValue : 0);
      }
    } else {
      pointer.getByteArray().addUint8(getOffset(pointer, 0), constantValue != null ? constantValue : 0);
    }
  }

  @Override
  protected void allocate(ArrayLengthReason reason, Pointer<?, ? extends Type<?>> pointer, long index) {
    callWithArrayLengthChange(reason, pointer, 1, () -> {
      callWithByteLengthChange(pointer, () -> {
        checkIndexAllocate(pointer, index);
        pointer.getByteArray().addUint8(getOffset(pointer, index), constantValue != null ? constantValue : 0);
      });
    });
  }
}
