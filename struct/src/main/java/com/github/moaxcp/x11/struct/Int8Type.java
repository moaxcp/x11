package com.github.moaxcp.x11.struct;

import org.jspecify.annotations.Nullable;

import static com.github.moaxcp.x11.struct.Size.INT8;

public final class Int8Type extends NumberType<Byte> {

  public static Int8Type int8() {
    return int8(-1);
  }

  public static Int8Type int8(int position) {
    return new Int8Type(position);
  }

  public Int8Type(int position, @Nullable Byte constantValue, @Nullable Expression lengthExpression, @Nullable Assignment assignment) {
    super(position, INT8, constantValue, lengthExpression, assignment);
  }

  public Int8Type(int position) {
    super(position, INT8);
  }

  @Override
  protected Int8Type copy(int position) {
    return new Int8Type(position, constantValue, lengthExpression, assignment);
  }

  @Override
  public Byte get(Pointer<?, ? extends Type<?>> pointer) {
    throw new UnsupportedOperationException("get(Pointer) not supported for Int8Type. Use getInt8(Pointer) instead.");
  }

  @Override
  public Byte get(Pointer<?, ? extends Type<?>> pointer, long index) {
    throw new UnsupportedOperationException("get(Pointer, long) not supported for Int8Type. Use getInt8(Pointer, long) instead.");
  }

  public byte getInt8(Pointer<?, ? extends Type<?>> pointer) {
    return pointer.getByteArray().int8(getOffset(pointer));
  }

  public byte getInt8(Pointer<?, ? extends Type<?>> pointer, long index) {
    return pointer.getByteArray().int8(getOffset(pointer, index));
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, Byte value) {
    throw new UnsupportedOperationException("set(Pointer, Byte) not supported for Int8Type. Use set(Pointer, byte) instead.");
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, long index, Byte value) {
    throw new UnsupportedOperationException("set(Pointer, long, Byte) not supported for Int8Type. Use set(Pointer, long, byte) instead.");
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, byte value) {
    pointer.getByteArray().int8(getOffset(pointer), value);
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, long index, byte value) {
    pointer.getByteArray().int8(getOffset(pointer, index), value);
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, Byte value) {
    throw new UnsupportedOperationException("add(Pointer, Byte) not supported for Int8Type. Use add(Pointer, byte) instead.");
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, long index, Byte value) {
    throw new UnsupportedOperationException("add(Pointer, long, Byte) not supported for Int8Type. Use add(Pointer, long, byte) instead.");
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, byte value) {
    add(pointer, getArrayLength(pointer), value);
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, long index, byte value) {
    allocate(pointer, index);
    set(pointer, index, value);
    assignment.assign(pointer, 1);
  }

  @Override
  public void allocate(Pointer<?, ? extends Type<?>> pointer, long index) {
    pointer.getByteArray().addInt8(getOffset(pointer, index), constantValue != null ? constantValue : 0);
  }
}
