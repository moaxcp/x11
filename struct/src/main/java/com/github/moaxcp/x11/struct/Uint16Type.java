package com.github.moaxcp.x11.struct;

import org.jspecify.annotations.Nullable;

import static com.github.moaxcp.x11.struct.Size.UINT16;

public final class Uint16Type extends NumberType<Integer> {

  public static Uint16Type uint16Type() {
    return uint16Type(-1);
  }

  public static Uint16Type uint16Type(int position) {
    return new Uint16Type(position);
  }

  public Uint16Type(int position, @Nullable Integer constantValue, @Nullable Expression lengthExpression, @Nullable Assignment assignment) {
    super(position, UINT16, constantValue, lengthExpression, assignment);
  }

  public Uint16Type(int position) {
    super(position, UINT16);
  }

  @Override
  protected Uint16Type copy(int position) {
    return new Uint16Type(position, constantValue, lengthExpression, assignment);
  }

  @Override
  public Integer get(Pointer<?, ? extends Type<?>> pointer) {
    throw new UnsupportedOperationException("get(Pointer) not supported for Uint16Type. Use getUint16(Pointer) instead.");
  }

  @Override
  public Integer get(Pointer<?, ? extends Type<?>> pointer, long index) {
    throw new UnsupportedOperationException("get(Pointer, long) not supported for Uint16Type. Use getUint16(Pointer, long) instead.");
  }

  public int getUint16(Pointer<?, ? extends Type<?>> pointer) {
    return pointer.getByteArray().uint16(getOffset(pointer));
  }

  public int getUint16(Pointer<?, ? extends Type<?>> pointer, long index) {
    return pointer.getByteArray().uint16(getOffset(pointer, index));
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, Integer value) {
    throw new UnsupportedOperationException("set(Pointer, Integer) not supported for Uint16Type. Use set(Pointer, int) instead.");
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, long index, Integer value) {
    throw new UnsupportedOperationException("set(Pointer, long, Integer) not supported for Uint16Type. Use set(Pointer, long, int) instead.");
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, int value) {
    pointer.getByteArray().uint16(getOffset(pointer), value);
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, long index, int value) {
    pointer.getByteArray().uint16(getOffset(pointer, index), value);
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, Integer value) {
    throw new UnsupportedOperationException("add(Pointer, Integer) not supported for Uint16Type. Use add(Pointer, int) instead.");
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, long index, Integer value) {
    throw new UnsupportedOperationException("add(Pointer, long, Integer) not supported for Uint16Type. Use add(Pointer, long, int) instead.");
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, int value) {
    add(pointer, getArrayLength(pointer), value);
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, long index, int value) {
    allocate(pointer, index);
    set(pointer, index, value);
    assignment.assign(pointer, 1);
  }

  @Override
  public void allocate(Pointer<?, ? extends Type<?>> pointer, long index) {
    pointer.getByteArray().addUint16(getOffset(pointer, index), constantValue != null ? constantValue : 0);
  }
}
