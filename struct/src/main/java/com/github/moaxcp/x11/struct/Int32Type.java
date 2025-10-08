package com.github.moaxcp.x11.struct;

import org.jspecify.annotations.Nullable;

import static com.github.moaxcp.x11.struct.Size.INT32;

public final class Int32Type extends NumberType<Integer> {

  public static Int32Type int32Type() {
    return int32Type(-1);
  }

  public static Int32Type int32Type(int position) {
    return new Int32Type(position);
  }

  public Int32Type(int position, @Nullable Integer constantValue, @Nullable Expression lengthExpression, @Nullable Assignment assignment) {
    super(position, INT32, constantValue, lengthExpression, assignment);
  }

  public Int32Type(int position) {
    super(position, INT32);
  }

  @Override
  protected Int32Type copy(int position) {
    return new Int32Type(position, constantValue, lengthExpression, assignment);
  }

  @Override
  public Integer get(Pointer<?, ? extends Type<?>> pointer) {
    throw new UnsupportedOperationException("get(Pointer) not supported for Int32Type. Use getInt32(Pointer) instead.");
  }

  @Override
  public Integer get(Pointer<?, ? extends Type<?>> pointer, long index) {
    throw new UnsupportedOperationException("get(Pointer, long) not supported for Int32Type. Use getInt32(Pointer, long) instead.");
  }

  public int getInt32(Pointer<?, ? extends Type<?>> pointer) {
    return pointer.getByteArray().int32(getOffset(pointer));
  }

  public int getInt32(Pointer<?, ? extends Type<?>> pointer, long index) {
    checkIndex(pointer, index);
    return pointer.getByteArray().int32(getOffset(pointer, index));
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, Integer value) {
    throw new UnsupportedOperationException("set(Pointer, Integer) not supported for Int32Type. Use set(Pointer, int) instead.");
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, long index, Integer value) {
    throw new UnsupportedOperationException("set(Pointer, long, Integer) not supported for Int32Type. Use set(Pointer, long, int) instead.");
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, int value) {
    setUnchecked(pointer, 0, value);
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, long index, int value) {
    checkIndex(pointer, index);
    setUnchecked(pointer, index, value);
  }

  private void setUnchecked(Pointer<?, ? extends Type<?>> pointer, long index, int value) {
    checkConstant(pointer, index, value);
    pointer.getByteArray().int32(getOffset(pointer, index), value);
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, Integer value) {
    throw new UnsupportedOperationException("add(Pointer, Integer) not supported for Int32Type. Use add(Pointer, int) instead.");
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, long index, Integer value) {
    throw new UnsupportedOperationException("add(Pointer, long, Integer) not supported for Int32Type. Use add(Pointer, long, int) instead.");
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, int value) {
    add(pointer, getArrayLength(pointer), value);
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, long index, int value) {
    if (!isArray()) {
      throw new ArrayIndexOutOfBoundsException(getClass().getSimpleName() + " cannot add to non-array type at position " + getPosition() + " index: " + index + " length: " + 1);
    }
    allocate(pointer, index);
    setUnchecked(pointer, index, value);
  }

  public void allocate(Pointer<?, ? extends Type<?>> pointer) {
    if (isArray()) {
      long length = getArrayLength(pointer);
      for (int i = 0; i < length; i++) {
        pointer.getByteArray().addInt32(getOffset(pointer, i), constantValue != null ? constantValue : 0);
      }
    } else {
      pointer.getByteArray().addInt32(getOffset(pointer, 0), constantValue != null ? constantValue : 0);
    }
  }

  @Override
  public void allocate(Pointer<?, ? extends Type<?>> pointer, long index) {
    checkIndexAllocate(pointer, index);
    pointer.getByteArray().addInt32(getOffset(pointer, index), constantValue != null ? constantValue : 0);
    if (assignment != null) {
      assignment.assign(pointer, 1);
    }
  }
}
