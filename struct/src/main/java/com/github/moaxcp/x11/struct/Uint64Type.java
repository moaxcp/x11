package com.github.moaxcp.x11.struct;

import com.github.moaxcp.x11.struct.ArrayLengthListener.ArrayLengthReason;
import com.github.moaxcp.x11.struct.ValueChangeListener.ValueChangeReason;
import org.jspecify.annotations.Nullable;

import java.math.BigInteger;

import static com.github.moaxcp.x11.struct.Primitive.UINT64;
import static com.github.moaxcp.x11.struct.ValueChangeListener.ValueChangeReason.SET_BY_ARRAY_LENGTH;
import static com.github.moaxcp.x11.struct.ValueChangeListener.ValueChangeReason.SET_VALUE;

public final class Uint64Type extends NumberType<Uint64Type, BigInteger> {

  public static Uint64Type uint64Type() {
    return uint64Type(-1);
  }

  public static Uint64Type uint64Type(int position) {
    return new Uint64Type(position);
  }

  public Uint64Type(int position, @Nullable BigInteger constantValue, @Nullable Expression lengthExpression) {
    super(position, UINT64, constantValue, lengthExpression);
  }

  public Uint64Type(int position) {
    super(position, UINT64);
  }

  @Override
  protected Uint64Type copy(int position) {
    return new Uint64Type(position, constantValue, lengthExpression);
  }

  public BigInteger getUint64(Pointer<?, ? extends Type<?>> pointer) {
    return pointer.getByteArray().getUint64(getOffset(pointer));
  }

  public BigInteger getUint64(Pointer<?, ? extends Type<?>> pointer, long index) {
    checkIndex(pointer, index);
    return pointer.getByteArray().getUint64(getOffset(pointer, index));
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, BigInteger value) {
    setUnchecked(SET_VALUE, pointer, 0, value);
  }

  void setForArrayLength(Pointer<?, ? extends Type<?>> pointer, long value) {
    setUnchecked(SET_BY_ARRAY_LENGTH, pointer, 0, BigInteger.valueOf(value));
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, long index, BigInteger value) {
    checkIndex(pointer, index);
    setUnchecked(SET_VALUE, pointer, index, value);
  }

  private void setUnchecked(ValueChangeReason reason, Pointer<?, ? extends Type<?>> pointer, long index, BigInteger value) {
    checkConstant(pointer, index, value);
    if (!valueChangeListeners.isEmpty()) {
      var old = pointer.getByteArray().getUint64(getOffset(pointer, index));
      pointer.getByteArray().setUint64(getOffset(pointer, index), value);
      notifyValueChange(reason, pointer, index, old, value);
    }
    pointer.getByteArray().setUint64(getOffset(pointer, index), value);
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, BigInteger value) {
    add(pointer, getArrayLength(pointer), value);
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, long index, BigInteger value) {
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
        pointer.getByteArray().addUint64(getOffset(pointer, i), constantValue != null ? constantValue : BigInteger.ZERO);
      }
    } else {
      pointer.getByteArray().addUint64(getOffset(pointer, 0), constantValue != null ? constantValue : BigInteger.ZERO);
    }
  }

  @Override
  protected void allocate(ArrayLengthReason reason, Pointer<?, ? extends Type<?>> pointer, long index) {
    callWithArrayLengthChange(reason, pointer, 1, () -> {
      callWithByteLengthChange(pointer, () -> {
        checkIndexAllocate(pointer, index);
        pointer.getByteArray().addUint64(getOffset(pointer, index), constantValue != null ? constantValue : BigInteger.ZERO);
      });
    });
  }
}
