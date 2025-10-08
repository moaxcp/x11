package com.github.moaxcp.x11.struct;

import org.jspecify.annotations.Nullable;

import static com.github.moaxcp.x11.struct.Size.FLOAT64;

public final class Float64Type extends NumberType<Double> {

  public static Float64Type float64Type() {
    return float64Type(-1);
  }

  public static Float64Type float64Type(int position) {
    return new Float64Type(position);
  }

  public Float64Type(int position, @Nullable Double constantValue, @Nullable Expression lengthExpression, @Nullable Assignment assignment) {
    super(position, FLOAT64, constantValue, lengthExpression, assignment);
  }

  public Float64Type(int position) {
    super(position, FLOAT64);
  }

  @Override
  protected Float64Type copy(int position) {
    return new Float64Type(position, constantValue, lengthExpression, assignment);
  }

  @Override
  public Double get(Pointer<?, ? extends Type<?>> pointer) {
    throw new UnsupportedOperationException("get(Pointer) not supported for Float64Type. Use getFloat64(Pointer) instead.");
  }

  @Override
  public Double get(Pointer<?, ? extends Type<?>> pointer, long index) {
    throw new UnsupportedOperationException("get(Pointer, long) not supported for Float64Type. Use getFloat64(Pointer, long) instead.");
  }

  public double getFloat64(Pointer<?, ? extends Type<?>> pointer) {
    return pointer.getByteArray().float64(getOffset(pointer));
  }

  public double getFloat64(Pointer<?, ? extends Type<?>> pointer, long index) {
    checkIndex(pointer, index);
    return pointer.getByteArray().float64(getOffset(pointer, index));
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, Double value) {
    throw new UnsupportedOperationException("set(Pointer, Double) not supported for Float64Type. Use set(Pointer, double) instead.");
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, long index, Double value) {
    throw new UnsupportedOperationException("set(Pointer, long, Double) not supported for Float64Type. Use set(Pointer, long, double) instead.");
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, double value) {
    setUnchecked(pointer, 0, value);
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, long index, double value) {
    checkIndex(pointer, index);
    setUnchecked(pointer, index, value);
  }

  private void setUnchecked(Pointer<?, ? extends Type<?>> pointer, long index, double value) {
    checkConstant(pointer, index, value);
    pointer.getByteArray().float64(getOffset(pointer, index), value);
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, Double value) {
    throw new UnsupportedOperationException("add(Pointer, Double) not supported for Float64Type. Use add(Pointer, double) instead.");
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, long index, Double value) {
    throw new UnsupportedOperationException("add(Pointer, long, Double) not supported for Float64Type. Use add(Pointer, long, double) instead.");
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, double value) {
    add(pointer, getArrayLength(pointer), value);
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, long index, double value) {
    if (!isArray()) {
      throw new ArrayIndexOutOfBoundsException(getClass().getSimpleName() + " cannot add to non-array type at position " + getPosition() + " index: " + index + " length: " + 1);
    }
    allocate(pointer, index);
    setUnchecked(pointer, index, value);
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
  public void allocate(Pointer<?, ? extends Type<?>> pointer, long index) {
    checkIndexAllocate(pointer, index);
    pointer.getByteArray().addFloat64(getOffset(pointer, index), constantValue != null ? constantValue : 0.0d);
    if (assignment != null) {
      assignment.assign(pointer, 1);
    }
  }
}
