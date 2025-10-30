package com.github.moaxcp.x11.struct;

import org.jspecify.annotations.Nullable;

public abstract sealed class PrimitiveType<SELF extends PrimitiveType<SELF, T>, T> extends ValueType<SELF, T> permits BoolType, NumberType {
  protected Primitive unitSize;

  public PrimitiveType(int position, Primitive size) {
    super(position);
    this.unitSize = size;
  }

  PrimitiveType(int position, Primitive unitSize, T constantValue, @Nullable Expression lengthExpression) {
    super(position, constantValue, lengthExpression);
    this.unitSize = unitSize;
  }

  public final Primitive getUnitSize() {
    return unitSize;
  }

  @Override
  public long getOffset(Pointer<?, ? extends Type<?>> pointer, long index) {
    long offset = getOffset(pointer);
    offset += index * getUnitSize().size();
    return offset;
  }

  @Override
  public long getAllocationLength(Type<?> parent) {
    if (isArray()) {
      return 0;
    }
    return unitSize.size();
  }

  @Override
  public long getByteLength(Pointer<?, ? extends Type<?>> pointer) {
    if(!isArray()) {
      return unitSize.size();
    }
    return getArrayLength(pointer) * getUnitSize().size();
  }

  @Override
  public final long getByteLength(Pointer<?, ? extends Type<?>> pointer, long index) {
    return getUnitSize().size();
  }

  @Override
  public final boolean isFixedLength(Pointer<?, ? extends Type<?>> pointer) {
    return lengthExpression == null || lengthExpression.isConstant(pointer.getType());
  }

  @Override
  public final T get(Pointer<?, ? extends Type<?>> pointer) {
    throw new UnsupportedOperationException("get(Pointer) not supported for " + getClass().getSimpleName() + ". Use get" + unitSize.title() + "(Pointer) instead.");
  }

  @Override
  public final T get(Pointer<?, ? extends Type<?>> pointer, long index) {
    throw new UnsupportedOperationException("get(Pointer, long) not supported for " + getClass().getSimpleName() + ". Use get" + unitSize.title() + "(Pointer, long) instead.");
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, T value) {
    throw new UnsupportedOperationException("set(Pointer, " + unitSize.wrapper() + ") not supported for " + getClass().getSimpleName() + ". Use set(Pointer, " + unitSize.primitive() + ") instead.");
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, long index, T value) {
    throw new UnsupportedOperationException("set(Pointer, long, " + unitSize.wrapper() + ") not supported for " + getClass().getSimpleName() + ". Use set(Pointer, long, " + unitSize.primitive() + ") instead.");
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, T value) {
    throw new UnsupportedOperationException("add(Pointer, " + unitSize.wrapper() + ") not supported for " + getClass().getSimpleName() + ". Use add(Pointer, " + unitSize.primitive() + ") instead.");
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, long index, T value) {
    throw new UnsupportedOperationException("add(Pointer, long, " + unitSize.wrapper() + ") not supported for " + getClass().getSimpleName() + ". Use add(Pointer, long, " + unitSize.primitive() + ") instead.");
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "unitSize=" + unitSize +
        ", lengthExpression=" + lengthExpression +
        ", constantValue=" + constantValue +
        ", byteLengthChangeListeners=" + byteLengthListeners +
        ", position=" + position +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    PrimitiveType<?, ?> that = (PrimitiveType<?, ?>) o;
    return unitSize == that.unitSize;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + unitSize.hashCode();
    return result;
  }
}
