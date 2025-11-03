package com.github.moaxcp.x11.struct;

import com.github.moaxcp.x11.struct.ArrayLengthListener.ArrayLengthReason;
import com.github.moaxcp.x11.struct.ValueChangeListener.ValueChangeReason;
import org.jspecify.annotations.Nullable;

import static com.github.moaxcp.x11.struct.Primitive.FLOAT64;
import static com.github.moaxcp.x11.struct.ValueChangeListener.ValueChangeReason.SET_BY_ARRAY_LENGTH;
import static com.github.moaxcp.x11.struct.ValueChangeListener.ValueChangeReason.SET_VALUE;

public final class Float64Type extends NumberType<Float64Type, Double> {

  public static Float64Type float64Type() {
    return float64Type(-1);
  }

  public static Float64Type float64Type(int position) {
    return new Float64Type(position);
  }

  public Float64Type(int position, @Nullable Double constantValue, @Nullable Expression lengthExpression) {
    super(position, FLOAT64, constantValue, lengthExpression);
  }

  public Float64Type(int position) {
    super(position, FLOAT64);
  }

  @Override
  protected Float64Type copy(int position) {
    return new Float64Type(position, constantValue, lengthExpression);
  }

  public double getFloat64(Pointer<?, ? extends Type<?>> pointer) {
    return pointer.getByteArray().getFloat64(getOffset(pointer));
  }

  public double getFloat64(Pointer<?, ? extends Type<?>> pointer, long index) {
    checkIndex(pointer, index);
    return pointer.getByteArray().getFloat64(getOffset(pointer, index));
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, double value) {
    setUnchecked(SET_VALUE, pointer, 0, value);
  }

  void setForArrayLength(Pointer<?, ? extends Type<?>> pointer, long value) {
    setUnchecked(SET_BY_ARRAY_LENGTH, pointer, 0, (double) value);
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, long index, double value) {
    checkIndex(pointer, index);
    setUnchecked(SET_VALUE, pointer, index, value);
  }

  private void setUnchecked(ValueChangeReason reason, Pointer<?, ? extends Type<?>> pointer, long index, double value) {
    checkConstant(pointer, index, value);
    if (!valueChangeListeners.isEmpty()) {
      var old = pointer.getByteArray().getFloat64(getOffset(pointer, index));
      pointer.getByteArray().setFloat64(getOffset(pointer, index), value);
      notifyValueChange(reason, pointer, index, old, value);
    }
    pointer.getByteArray().setFloat64(getOffset(pointer, index), value);
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, double value) {
    add(pointer, getArrayLength(pointer), value);
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, long index, double value) {
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
        pointer.getByteArray().addFloat64(getOffset(pointer, i), constantValue != null ? constantValue : 0.0d);
      }
    } else {
      pointer.getByteArray().addFloat64(getOffset(pointer, 0), constantValue != null ? constantValue : 0.0d);
    }
  }

  @Override
  protected void allocate(ArrayLengthReason reason, Pointer<?, ? extends Type<?>> pointer, long index) {
    callWithArrayLengthChange(reason, pointer, 1, () -> {
      callWithByteLengthChange(pointer, () -> {
        checkIndexAllocate(pointer, index);
        pointer.getByteArray().addFloat64(getOffset(pointer, index), constantValue != null ? constantValue : 0.0d);
      });
    });
  }
}
