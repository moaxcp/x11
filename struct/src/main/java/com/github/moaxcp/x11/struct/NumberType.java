package com.github.moaxcp.x11.struct;

import org.jspecify.annotations.Nullable;

public abstract sealed class NumberType<T extends Number> extends PrimitiveType<T> permits Int8Type, Uint8Type, Int16Type, Uint16Type, Int32Type, Uint32Type, Int64Type, Uint64Type, Float32Type, Float64Type {

  public NumberType(int position, Size size) {
    super(position, size);
  }

  NumberType(int position, Size unitSize, T constantValue, @Nullable Expression lengthExpression, @Nullable Assignment assignment) {
    super(position, unitSize, constantValue, lengthExpression, assignment);
  }
}
