package com.github.moaxcp.x11.struct;

import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract sealed class ValueType<SELF extends ValueType<SELF, T>, T> extends Type<SELF> permits PrimitiveType, StructType {
  @Nullable
  protected final Expression lengthExpression;
  @Nullable
  protected final T constantValue;
  protected final List<ArrayLengthChangeListener> arrayLengthChangeListeners;

  public ValueType(int position) {
    super(position);
    this.lengthExpression = null;
    this.constantValue = null;
    arrayLengthChangeListeners = new ArrayList<>();
  }

  public ValueType(int position, @Nullable T constantValue, @Nullable Expression lengthExpression) {
    super(position);
    this.lengthExpression = lengthExpression;
    this.constantValue = constantValue;
    arrayLengthChangeListeners = new ArrayList<>();
  }

  public final @Nullable T getConstantValue() {
    return constantValue;
  }

  public final @Nullable Expression getLengthExpression() {
    return lengthExpression;
  }

  public SELF addArrayLengthChangeListener(ArrayLengthChangeListener arrayLengthChange) {
    arrayLengthChangeListeners.add(arrayLengthChange);
    return (SELF) this;
  }

  public SELF addArrayLengthChangeListeners(List<ArrayLengthChangeListener> arrayLengthChange) {
    arrayLengthChangeListeners.addAll(arrayLengthChange);
    return (SELF) this;
  }

  public long getOffset(Pointer<?, ? extends Type<?>> pointer, long index) {
    long offset = getOffset(pointer);
    for (int i = 0; i < index; i++) {
      offset += getByteLength(pointer, i);
    }
    return offset;
  }

  public final boolean isArray() {
    return lengthExpression != null;
  }

  @Override
  public long getByteLength(Pointer<?, ? extends Type<?>> pointer) {
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

  public final boolean isConstant(Type<?> type) {
    return constantValue != null || (lengthExpression != null && lengthExpression.isConstant(type));
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
    if (isConstant(pointer.getType()) && !Objects.equals(constantValue, value)) {
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
  }

  public abstract void allocate(Pointer<?, ? extends Type<?>> pointer, long index);

  void checkIndexAllocate(Pointer<?, ? extends Type<?>> pointer, long index) {
    var newLength = getArrayLength(pointer) + 1;
    if (index >= newLength || index < 0) {
      throw new ArrayIndexOutOfBoundsException(this.getClass().getSimpleName() + " at position " + getPosition() + " index: " + index + " new length: " + newLength);
    }
  }

  public final void remove(Pointer<?, ? extends Type<?>> pointer) {
    if (!isArray()) {
      throw new ArrayIndexOutOfBoundsException(getClass().getSimpleName() + " cannot remove from non-array type at position " + getPosition());
    }
    if (isFixedLength(pointer)) {
      throw new UnsupportedOperationException("Cannot remove fixed length array " + getClass().getSimpleName() + " at position " + getPosition());
    }
    callWithArrayLengthChange(pointer, -getArrayLength(pointer), () -> callWithByteLengthChange(pointer, () -> pointer.getByteArray().remove(getOffset(pointer), getByteLength(pointer))));
  }

  public final void remove(Pointer<?, ? extends Type<?>> pointer, long index) {
    if (!isArray()) {
      throw new ArrayIndexOutOfBoundsException(getClass().getSimpleName() + " cannot remove from non-array type at position " + getPosition());
    }
    if (isFixedLength(pointer)) {
      throw new UnsupportedOperationException("Cannot remove element from fixed length array " + getClass().getSimpleName() + " at position " + getPosition() + " index: " + index);
    }
    checkIndex(pointer, index);
    callWithArrayLengthChange(pointer, -1, () -> {
      callWithByteLengthChange(pointer, () -> {
        pointer.getByteArray().remove(getOffset(pointer, index), getByteLength(pointer, index));
      });
    });
  }

  protected void callWithArrayLengthChange(Pointer<?, ? extends Type<?>> pointer, long added, Runnable runnable) {
    if (arrayLengthChangeListeners.isEmpty()) {
      runnable.run();
      return;
    }
    var oldLength = getArrayLength(pointer);
    runnable.run();
    var newLength = oldLength + added;
    if (newLength != oldLength) {
      for (ArrayLengthChangeListener listener : arrayLengthChangeListeners) {
        listener.arrayLengthChanged(pointer, oldLength, newLength);
      }
    }
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    ValueType<?, ?> valueType = (ValueType<?, ?>) o;
    return Objects.equals(lengthExpression, valueType.lengthExpression) && Objects.equals(arrayLengthChangeListeners, valueType.arrayLengthChangeListeners) && Objects.equals(constantValue, valueType.constantValue);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + Objects.hashCode(lengthExpression);
    result = 31 * result + Objects.hashCode(arrayLengthChangeListeners);
    result = 31 * result + Objects.hashCode(constantValue);
    return result;
  }
}
