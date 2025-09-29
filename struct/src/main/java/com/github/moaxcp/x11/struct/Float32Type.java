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
    pointer.getByteArray().float32(getOffset(pointer), value);
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, long index, float value) {
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
    allocate(pointer, index);
    set(pointer, index, value);
    assignment.assign(pointer, 1);
  }

  @Override
  public void allocate(Pointer<?, ? extends Type<?>> pointer, long index) {
    pointer.getByteArray().addFloat32(getOffset(pointer, index), constantValue != null ? constantValue : 0.0f);
  }
}
