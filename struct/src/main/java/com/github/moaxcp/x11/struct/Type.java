package com.github.moaxcp.x11.struct;

import org.jspecify.annotations.Nullable;

import java.util.Objects;

/**
 * Base class for all types in a struct. A type can be a simple primitive such as byte, short, double or it can also be
 * a struct.
 * 
 * Any type can represent a single value or an array of values. To be an array
 * the type must have a lengthExpression. An Assignment is required for variable length arrays.
 * @param <T>
 */
public abstract sealed class Type<T> permits PrimitiveType, StructType, PadType {
  protected final int position;
  @Nullable
  protected final Expression lengthExpression;
  @Nullable
  protected final Assignment assignment;
  @Nullable
  protected T constantValue;

  public Type(int position, @Nullable T constantValue, @Nullable Expression lengthExpression, @Nullable Assignment assignment) {
    this.position = position;
    this.constantValue = constantValue;
    this.lengthExpression = lengthExpression;
    this.assignment = assignment;
  }

  public Type(int position) {
    this(position, null, null, null);
  }

  protected abstract Type<T> copy(int position);

  public final int getPosition() {
    return position;
  }

  public final @Nullable T getConstantValue() {
    return constantValue;
  }

  public final @Nullable Expression getLengthExpression() {
    return lengthExpression;
  }

  @Nullable
  public final Assignment getAssingment() {
    return assignment;
  }
  
  public final boolean isArray() {
    return lengthExpression != null;
  }

  public final long getOffset(Pointer<?, ? extends Type<?>> pointer) {
    long offset = pointer.getOffset();
    for (int i = 0; i < getPosition(); i++) {
      offset += pointer.getType(i).getByteLength(pointer);
    }
    return offset;
  }

  public final long getOffset(Pointer<?, ? extends Type<?>> pointer, long index) {
    long offset = getOffset(pointer);
    for (int i = 0; i < index; i++) {
      offset += pointer.getType(position).getByteLength(pointer, i);
    }
    return offset;
  }

  public final long getByteLength(Pointer<?, ? extends Type<?>> pointer) {
    if(!isArray()) {
      return getByteLength(pointer, 0);
    }
    var length = 0L;
    var l = getArrayLength(pointer);
    for (int i = 0; i < l; i++) {
      length += getByteLength(pointer, i);
    }
    return length;
  }

  public abstract long getByteLength(Pointer<?, ? extends Type<?>> pointer, long index);

  public final long getArrayLength(Pointer<?, ? extends Type<?>> pointer) {
    if (!isArray()) {
      return 1;
    }
    return lengthExpression.evaluate(pointer);
  }

  public abstract boolean isFixedLength(Pointer<?, ? extends Type<?>> pointer);

  public final boolean isConstant(Pointer<?, ? extends Type<?>> pointer) {
    return constantValue != null || (lengthExpression != null && lengthExpression.isConstant(pointer));
  }

  public T get(Pointer<?, ? extends Type<?>> pointer) {
    return get(pointer, 0);
  }
  
  public abstract T get(Pointer<?, ? extends Type<?>> pointer, long index);

  protected void checkIndex(Pointer<?, ? extends Type<?>> pointer, long index) {
    var length = getArrayLength(pointer);
    if (index >= length || index < 0) {
      throw new ArrayIndexOutOfBoundsException(this.getClass().getSimpleName() + " at position " + getPosition() + " index: " + index + " length: " + length);
    }
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, T value) {
    set(pointer, 0, value);
  }
  
  public abstract void set(Pointer<?, ? extends Type<?>> pointer, long index, T value);

  protected void checkConstant(Pointer<?, ? extends Type<?>> pointer, long index, T value) {
    if (isConstant(pointer) && !Objects.equals(constantValue, value)) {
      throw new UnsupportedOperationException(getClass().getSimpleName() + " at position " + getPosition() + " is constant index: " + index + " value: " + value + " constant: " + constantValue);
    }
  }
  
  public void add(Pointer<?, ? extends Type<?>> pointer, T value) {
    add(pointer, getArrayLength(pointer), value);
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, long index, T value) {
    if (!isArray()) {
      throw new ArrayIndexOutOfBoundsException(getClass().getSimpleName() + " cannot add to non-array type at position " + getPosition());
    }
    allocate(pointer, index);
    set(pointer, index, value);
    assignment.assign(pointer, 1);
  }

  void checkIndexAllocate(Pointer<?, ? extends Type<?>> pointer, long index) {
    var newLength = getArrayLength(pointer) + 1;
    if (index >= newLength || index < 0) {
      throw new ArrayIndexOutOfBoundsException(this.getClass().getSimpleName() + " at position " + getPosition() + " index: " + index + " new length: " + newLength);
    }
  }

  public void allocate(Pointer<?, ? extends Type<?>> pointer) {
    if(isArray()) {
      long length = getArrayLength(pointer);
      for (int i = 0; i < length; i++) {
        allocate(pointer, i);
      }
    } else {
      allocate(pointer, 0);
    }
  }

  public abstract void allocate(Pointer<?, ? extends Type<?>> pointer, long index);

  public final void removeAll(Pointer<?, ? extends Type<?>> pointer) {
    if (!isArray()) {
      throw new ArrayIndexOutOfBoundsException(getClass().getSimpleName() + " cannot remove from non-array type at position " + getPosition());
    }
    if (isFixedLength(pointer)) {
      throw new UnsupportedOperationException("Cannot remove fixed length array " + getClass().getSimpleName() + " at position " + getPosition());
    }
    var length = getArrayLength(pointer);
    pointer.getByteArray().remove(getOffset(pointer), getByteLength(pointer));
    if (assignment != null) {
      assignment.assign(pointer, -length);
    }
  }
  
  public final void remove(Pointer<?, ? extends Type<?>> pointer, long index) {
    if (!isArray()) {
      throw new ArrayIndexOutOfBoundsException(getClass().getSimpleName() + " cannot remove from non-array type at position " + getPosition());
    }
    if (isFixedLength(pointer)) {
      throw new UnsupportedOperationException("Cannot remove element from fixed length array " + getClass().getSimpleName() + " at position " + getPosition() + " index: " + index);
    }
    checkIndex(pointer, index);
    pointer.getByteArray().remove(getOffset(pointer, index), getByteLength(pointer, index));
    if (assignment != null) {
      assignment.assign(pointer, -1);
    }
  }
}
