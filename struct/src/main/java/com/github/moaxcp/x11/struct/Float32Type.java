package com.github.moaxcp.x11.struct;

import org.jspecify.annotations.Nullable;

import static com.github.moaxcp.x11.struct.Size.FLOAT32;

public final class Float32Type extends NumberType<Float> {

  public static Float32Type float32Type() {
    return float32Type(-1);
  }

  public static Float32Type float32Type(int position) {
    return new Float32Type(position);
  }

  public Float32Type(int position, @Nullable Float constantValue, @Nullable Expression lengthExpression, @Nullable Assignment assignment) {
    super(position, FLOAT32, constantValue, lengthExpression, assignment);
  }

  public Float32Type(int position) {
    super(position, FLOAT32);
  }

  @Override
  protected Float32Type copy(int position) {
    return new Float32Type(position, constantValue, lengthExpression, assignment);
  }

  @Override
  public Float get(Pointer<?, ? extends Type<?>> pointer) {
    throw new UnsupportedOperationException("get(Pointer) not supported for Float32Type. Use getFloat32(Pointer) instead.");
  }

  @Override
  public Float get(Pointer<?, ? extends Type<?>> pointer, long index) {
    throw new UnsupportedOperationException("get(Pointer, long) not supported for Float32Type. Use getFloat32(Pointer, long) instead.");
  }

  public float getFloat32(Pointer<?, ? extends Type<?>> pointer) {
    return pointer.getByteArray().float32(getOffset(pointer));
  }

  public float getFloat32(Pointer<?, ? extends Type<?>> pointer, long index) {
    checkIndex(pointer, index);
    return pointer.getByteArray().float32(getOffset(pointer, index));
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, Float value) {
    throw new UnsupportedOperationException("set(Pointer, Float) not supported for Float32Type. Use set(Pointer, float) instead.");
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, long index, Float value) {
    throw new UnsupportedOperationException("set(Pointer, long, Float) not supported for Float32Type. Use set(Pointer, long, float) instead.");
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, float value) {
    setUnchecked(pointer, 0, value);
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, long index, float value) {
    checkIndex(pointer, index);
    setUnchecked(pointer, index, value);
  }

  private void setUnchecked(Pointer<?, ? extends Type<?>> pointer, long index, float value) {
    checkConstant(pointer, index, value);
    pointer.getByteArray().float32(getOffset(pointer, index), value);
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, Float value) {
    throw new UnsupportedOperationException("add(Pointer, Float) not supported for Float32Type. Use add(Pointer, float) instead.");
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, long index, Float value) {
    throw new UnsupportedOperationException("add(Pointer, long, Float) not supported for Float32Type. Use add(Pointer, long, float) instead.");
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, float value) {
    add(pointer, getArrayLength(pointer), value);
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, long index, float value) {
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
        pointer.getByteArray().addFloat32(getOffset(pointer, i), constantValue != null ? constantValue : 0.0f);
      }
    } else {
      pointer.getByteArray().addFloat32(getOffset(pointer, 0), constantValue != null ? constantValue : 0.0f);
    }
  }

  @Override
  public void allocate(Pointer<?, ? extends Type<?>> pointer, long index) {
    checkIndexAllocate(pointer, index);
    pointer.getByteArray().addFloat32(getOffset(pointer, index), constantValue != null ? constantValue : 0.0f);
    if (assignment != null) {
      assignment.assign(pointer, 1);
    }
  }
}
