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
  public Float get(Pointer<?, ? extends Type<?>> pointer, long index) {
    return pointer.getByteArray().float32(getOffset(pointer, index));
  }

  public float getFloat32(Pointer<?, ? extends Type<?>> pointer) {
    return pointer.getByteArray().float32(getOffset(pointer));
  }

  public float getFloat32(Pointer<?, ? extends Type<?>> pointer, long index) {
    return pointer.getByteArray().float32(getOffset(pointer, index));
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, long index, Float value) {
    pointer.getByteArray().float32(getOffset(pointer, index), value);
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, float value) {
    pointer.getByteArray().float32(getOffset(pointer), value);
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, long index, float value) {
    pointer.getByteArray().float32(getOffset(pointer, index), value);
  }

  @Override
  public void allocate(Pointer<?, ? extends Type<?>> pointer, long index) {
    pointer.getByteArray().addFloat32(getOffset(pointer, index), constantValue != null ? constantValue : 0.0f);
  }
}
