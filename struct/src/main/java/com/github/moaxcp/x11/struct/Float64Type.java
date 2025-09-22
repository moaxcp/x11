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
  public Double get(Pointer<?, ? extends Type<?>> pointer, long index) {
    return pointer.getByteArray().double64(getOffset(pointer, index));
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, long index, Double value) {
    pointer.getByteArray().double64(getOffset(pointer, index), value);
  }

  @Override
  public void allocate(Pointer<?, ? extends Type<?>> pointer, long index) {
    pointer.getByteArray().addDouble64(getOffset(pointer, index), constantValue != null ? constantValue : 0.0d);
  }
}
