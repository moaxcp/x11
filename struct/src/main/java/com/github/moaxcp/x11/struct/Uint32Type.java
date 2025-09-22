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
  public Long get(Pointer<?, ? extends Type<?>> pointer, long index) {
    return pointer.getByteArray().uint32(getOffset(pointer, index));
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, long index, Long value) {
    pointer.getByteArray().uint32(getOffset(pointer, index), value);
  }

  @Override
  public void allocate(Pointer<?, ? extends Type<?>> pointer, long index) {
    pointer.getByteArray().addUint32(getOffset(pointer, index), constantValue != null ? constantValue : 0L);
  }
}
