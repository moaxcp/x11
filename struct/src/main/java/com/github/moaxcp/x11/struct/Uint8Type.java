package com.github.moaxcp.x11.struct;

import org.jspecify.annotations.Nullable;

import static com.github.moaxcp.x11.struct.Size.UINT8;

public final class Uint8Type extends NumberType<Short> {

  public static Uint8Type uint8Type() {
    return uint8Type(-1);
  }

  public static Uint8Type uint8Type(int position) {
    return new Uint8Type(position);
  }

  public Uint8Type(int position, @Nullable Short constantValue, @Nullable Expression lengthExpression, @Nullable Assignment assignment) {
    super(position, UINT8, constantValue, lengthExpression, assignment);
  }

  public Uint8Type(int position) {
    super(position, UINT8);
  }

  @Override
  protected Uint8Type copy(int position) {
    return new Uint8Type(position, constantValue, lengthExpression, assignment);
  }
  
  

  @Override
  public Short get(Pointer<?, ? extends Type<?>> pointer) {
    throw new UnsupportedOperationException("get(Pointer) not supported for Uint8Type. Use getUint8(Pointer) instead.");
  }

  @Override
  public Short get(Pointer<?, ? extends Type<?>> pointer, long index) {
    throw new UnsupportedOperationException("get(Pointer, long) not supported for Uint8Type. Use getUint8(Pointer, long) instead.");
  }

  public short getUint8(Pointer<?, ? extends Type<?>> pointer) {
    return pointer.getByteArray().uint8(getOffset(pointer));
  }

  public short getUint8(Pointer<?, ? extends Type<?>> pointer, long index) {
    return pointer.getByteArray().uint8(getOffset(pointer, index));
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, Short value) {
    throw new UnsupportedOperationException("set(Pointer, Short) not supported for Uint8Type. Use set(Pointer, short) instead.");
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, long index, Short value) {
    throw new UnsupportedOperationException("set(Pointer, long, Short) not supported for Uint8Type. Use set(Pointer, long, short) instead.");
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, short value) {
    pointer.getByteArray().uint8(getOffset(pointer), value);
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, long index, short value) {
    pointer.getByteArray().uint8(getOffset(pointer, index), value);
  }

  @Override
  public void allocate(Pointer<?, ? extends Type<?>> pointer, long index) {
    pointer.getByteArray().addUint8(getOffset(pointer, index), constantValue != null ? constantValue : 0);
  }
}
