package com.github.moaxcp.x11.struct;

import org.jspecify.annotations.Nullable;

import static com.github.moaxcp.x11.struct.Size.UINT32;

public final class Uint32Type extends NumberType<Long> {

  public static Uint32Type uint32Type() {
    return uint32Type(-1);
  }

  public static Uint32Type uint32Type(int position) {
    return new Uint32Type(position);
  }

  public Uint32Type(int position, @Nullable Long constantValue, @Nullable Expression lengthExpression, @Nullable Assignment assignment) {
    super(position, UINT32, constantValue, lengthExpression, assignment);
  }

  public Uint32Type(int position) {
    super(position, UINT32);
  }

  @Override
  protected Uint32Type copy(int position) {
    return new Uint32Type(position, constantValue, lengthExpression, assignment);
  }
  
  

  @Override
  public Long get(Pointer<?, ? extends Type<?>> pointer) {
    throw new UnsupportedOperationException("get(Pointer) not supported for Uint32Type. Use getUint32(Pointer) instead.");
  }

  @Override
  public Long get(Pointer<?, ? extends Type<?>> pointer, long index) {
    throw new UnsupportedOperationException("get(Pointer, long) not supported for Uint32Type. Use getUint32(Pointer, long) instead.");
  }

  public long getUint32(Pointer<?, ? extends Type<?>> pointer) {
    return pointer.getByteArray().uint32(getOffset(pointer));
  }

  public long getUint32(Pointer<?, ? extends Type<?>> pointer, long index) {
    return pointer.getByteArray().uint32(getOffset(pointer, index));
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, Long value) {
    throw new UnsupportedOperationException("set(Pointer, Long) not supported for Uint32Type. Use set(Pointer, long) instead.");
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, long index, Long value) {
    throw new UnsupportedOperationException("set(Pointer, long, Long) not supported for Uint32Type. Use set(Pointer, long, long) instead.");
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, long value) {
    pointer.getByteArray().uint32(getOffset(pointer), value);
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, long index, long value) {
    pointer.getByteArray().uint32(getOffset(pointer, index), value);
  }

  @Override
  public void allocate(Pointer<?, ? extends Type<?>> pointer, long index) {
    pointer.getByteArray().addUint32(getOffset(pointer, index), constantValue != null ? constantValue : 0L);
  }
}
