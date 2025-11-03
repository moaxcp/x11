package com.github.moaxcp.x11.struct;

import com.github.moaxcp.x11.struct.ArrayLengthListener.ArrayLengthReason;
import com.github.moaxcp.x11.struct.ValueChangeListener.ValueChangeReason;
import org.jspecify.annotations.Nullable;

import static com.github.moaxcp.x11.struct.Primitive.UINT32;
import static com.github.moaxcp.x11.struct.ValueChangeListener.ValueChangeReason.SET_BY_ARRAY_LENGTH;
import static com.github.moaxcp.x11.struct.ValueChangeListener.ValueChangeReason.SET_VALUE;

public final class Uint32Type extends NumberType<Uint32Type, Long> {

  public static Uint32Type uint32Type() {
    return uint32Type(-1);
  }

  public static Uint32Type uint32Type(int position) {
    return new Uint32Type(position);
  }

  public Uint32Type(int position, @Nullable Long constantValue, @Nullable Expression lengthExpression) {
    super(position, UINT32, constantValue, lengthExpression);
  }

  public Uint32Type(int position) {
    super(position, UINT32);
  }

  @Override
  protected Uint32Type copy(int position) {
    return new Uint32Type(position, constantValue, lengthExpression);
  }

  public long getUint32(Pointer<?, ? extends Type<?>> pointer) {
    return pointer.getByteArray().getUint32(getOffset(pointer));
  }

  public long getUint32(Pointer<?, ? extends Type<?>> pointer, long index) {
    checkIndex(pointer, index);
    return pointer.getByteArray().getUint32(getOffset(pointer, index));
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, long value) {
    setUnchecked(SET_VALUE, pointer, 0, value);
  }

  void setForArrayLength(Pointer<?, ? extends Type<?>> pointer, long value) {
    setUnchecked(SET_BY_ARRAY_LENGTH, pointer, 0, (byte) value);
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, long index, long value) {
    checkIndex(pointer, index);
    setUnchecked(SET_VALUE, pointer, index, value);
  }

  private void setUnchecked(ValueChangeReason reason, Pointer<?, ? extends Type<?>> pointer, long index, long value) {
    checkConstant(pointer, index, value);
    if (!valueChangeListeners.isEmpty()) {
      var old = pointer.getByteArray().getUint32(getOffset(pointer, index));
      pointer.getByteArray().setUint32(getOffset(pointer, index), value);
      notifyValueChange(reason, pointer, index, old, value);
    }
    pointer.getByteArray().setUint32(getOffset(pointer, index), value);
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, long value) {
    add(pointer, getArrayLength(pointer), value);
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, long index, long value) {
    if (!isArray()) {
      throw new ArrayIndexOutOfBoundsException(getClass().getSimpleName() + " cannot add to non-array type at position " + getPosition() + " index: " + index + " length: " + 1);
    }
    allocate(pointer, index);
    setUnchecked(SET_VALUE, pointer, index, value);
  }

  public void allocate(Pointer<?, ? extends Type<?>> pointer) {
    if (isArray()) {
      long length = getArrayLength(pointer);
      for (int i = 0; i < length; i++) {
        pointer.getByteArray().addUint32(getOffset(pointer, i), constantValue != null ? constantValue : 0L);
      }
    } else {
      pointer.getByteArray().addUint32(getOffset(pointer, 0), constantValue != null ? constantValue : 0L);
    }
  }

  @Override
  protected void allocate(ArrayLengthReason reason, Pointer<?, ? extends Type<?>> pointer, long index) {
    callWithArrayLengthChange(reason, pointer, 1, () -> {
      callWithByteLengthChange(pointer, () -> {
        checkIndexAllocate(pointer, index);
        pointer.getByteArray().addUint32(getOffset(pointer, index), constantValue != null ? constantValue : 0L);
      });
    });
  }
}
