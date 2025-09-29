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
    pointer.getByteArray().float64(getOffset(pointer), value);
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, long index, double value) {
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
    allocate(pointer, index);
    set(pointer, index, value);
    assignment.assign(pointer, 1);
  }

  @Override
  public void allocate(Pointer<?, ? extends Type<?>> pointer, long index) {
    pointer.getByteArray().addFloat64(getOffset(pointer, index), constantValue != null ? constantValue : 0.0d);
  }
}
