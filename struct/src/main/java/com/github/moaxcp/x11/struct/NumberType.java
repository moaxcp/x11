package com.github.moaxcp.x11.struct;

import org.jspecify.annotations.Nullable;

public abstract sealed class NumberType<T extends Number> extends Type<T> permits Int8Type, Uint8Type, Int16Type, Uint16Type, Int32Type, Uint32Type, Int64Type, Uint64Type, Float32Type, Float64Type {

  protected Size unitSize;

  public NumberType(int position, Size size) {
    super(position);
    this.unitSize = size;
  }

  NumberType(int position, Size unitSize, T constantValue, @Nullable Expression lengthExpression, @Nullable Assignment assignment) {
    super(position, constantValue, lengthExpression, assignment);
    this.unitSize = unitSize;
  }

  public Size getUnitSize() {
    return unitSize;
  }

  @Override
  public final long getByteLength(Pointer<?, ? extends Type<?>> pointer, long index) {
    return getUnitSize().size();
  }

  @Override
  public boolean isFixedLength(Pointer<?, ? extends Type<?>> pointer) {
    return lengthExpression == null || lengthExpression.isConstant(pointer);
  }
}
