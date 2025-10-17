package com.github.moaxcp.x11.struct;

import org.jspecify.annotations.Nullable;

public abstract sealed class PrimitiveType<T> extends ValueType<T> permits BoolType, NumberType {
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
  public long getOffset(Pointer<?, ? extends Type> pointer, long index) {
    long offset = getOffset(pointer);
    offset += index * getUnitSize().size();
    return offset;
  }

  @Override
  public long getByteLength(Pointer<?, ? extends Type> pointer) {
    if(!isArray()) {
      return unitSize.size();
    }
    return getArrayLength(pointer) * getUnitSize().size();
  }

  @Override
  public final long getByteLength(Pointer<?, ? extends Type> pointer, long index) {
    return getUnitSize().size();
  }

  @Override
  public final boolean isFixedLength(Pointer<?, ? extends Type> pointer) {
    return lengthExpression == null || lengthExpression.isConstant(pointer);
  }

  @Override
  public final T get(Pointer<?, ? extends Type> pointer) {
    throw new UnsupportedOperationException("get(Pointer) not supported for " + getClass().getSimpleName() + ". Use get" + unitSize.title() + "(Pointer) instead.");
  }

  @Override
  public final T get(Pointer<?, ? extends Type> pointer, long index) {
    throw new UnsupportedOperationException("get(Pointer, long) not supported for " + getClass().getSimpleName() + ". Use get" + unitSize.title() + "(Pointer, long) instead.");
  }

  @Override
  public final void set(Pointer<?, ? extends Type> pointer, T value) {
    throw new UnsupportedOperationException("set(Pointer, Byte) not supported for " + getClass().getSimpleName() + ". Use set(Pointer, " + unitSize.primitive() + ") instead.");
  }

  @Override
  public final void set(Pointer<?, ? extends Type> pointer, long index, T value) {
    throw new UnsupportedOperationException("set(Pointer, long, T) not supported for " + getClass().getSimpleName() + ". Use set(Pointer, long, " + unitSize.primitive() + ") instead.");
  }

  public final void add(Pointer<?, ? extends Type> pointer, T value) {
    throw new UnsupportedOperationException("add(Pointer, Byte) not supported for " + getClass().getSimpleName() + ". Use add(Pointer, " + unitSize.primitive() + ") instead.");
  }

  public final void add(Pointer<?, ? extends Type> pointer, long index, T value) {
    throw new UnsupportedOperationException("add(Pointer, long, Byte) not supported for " + getClass().getSimpleName() + ". Use add(Pointer, long, " + unitSize.primitive() + ") instead.");
  }
}
