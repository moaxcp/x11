package com.github.moaxcp.x11.struct;

import org.jspecify.annotations.Nullable;

public abstract sealed class NumberType<SELF extends NumberType<SELF, T>, T extends Number> extends PrimitiveType<SELF, T> permits Int8Type, Uint8Type, Int16Type, Uint16Type, Int32Type, Uint32Type, Int64Type, Uint64Type, Float32Type, Float64Type {

  public NumberType(int position, Primitive size) {
    super(position, size);
  }

  NumberType(int position, Primitive unitSize, T constantValue, @Nullable Expression lengthExpression) {
    super(position, unitSize, constantValue, lengthExpression);
  }

  public long defaultValue() {
    return constantValue != null ? constantValue.longValue() : 0;
  }

  abstract void setForArrayLength(Pointer<?, ? extends Type<?>> pointer, long value);
}
