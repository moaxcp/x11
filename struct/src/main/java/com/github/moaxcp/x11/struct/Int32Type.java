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
  public Integer get(Pointer<?, ? extends Type<?>> pointer, long index) {
    return pointer.getByteArray().int32(getOffset(pointer, index));
  }

  public int getInt32(Pointer<?, ? extends Type<?>> pointer) {
    return pointer.getByteArray().int32(getOffset(pointer));
  }

  public int getInt32(Pointer<?, ? extends Type<?>> pointer, long index) {
    return pointer.getByteArray().int32(getOffset(pointer, index));
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, long index, Integer value) {
    pointer.getByteArray().int32(getOffset(pointer, index), value);
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, int value) {
    pointer.getByteArray().int32(getOffset(pointer), value);
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, long index, int value) {
    pointer.getByteArray().int32(getOffset(pointer, index), value);
  }

  @Override
  public void allocate(Pointer<?, ? extends Type<?>> pointer, long index) {
    pointer.getByteArray().addInt32(getOffset(pointer, index), constantValue != null ? constantValue : 0);
  }
}
