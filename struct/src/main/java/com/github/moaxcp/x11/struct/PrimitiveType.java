package com.github.moaxcp.x11.struct;

import org.jspecify.annotations.Nullable;

public abstract sealed class PrimitiveType<T> extends Type<T> permits BoolType, NumberType {
  protected Size unitSize;

  public PrimitiveType(int position, Size size) {
    super(position);
    this.unitSize = size;
  }

  PrimitiveType(int position, Size unitSize, T constantValue, @Nullable Expression lengthExpression, @Nullable Assignment assignment) {
    super(position, constantValue, lengthExpression, assignment);
    this.unitSize = unitSize;
  }

  public final Size getUnitSize() {
    return unitSize;
  }

  @Override
  public final long getByteLength(Pointer<?, ? extends Type<?>> pointer, long index) {
    return getUnitSize().size();
  }

  @Override
  public final boolean isFixedLength(Pointer<?, ? extends Type<?>> pointer) {
    return lengthExpression == null || lengthExpression.isConstant(pointer);
  }
}
