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
  public Integer get(Pointer<?, ? extends Type<?>> pointer, long index) {
    return pointer.getByteArray().uint16(getOffset(pointer, index));
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, long index, Integer value) {
    pointer.getByteArray().uint16(getOffset(pointer, index), value);
  }

  @Override
  public void allocate(Pointer<?, ? extends Type<?>> pointer, long index) {
    pointer.getByteArray().addUint16(getOffset(pointer, index), constantValue != null ? constantValue : 0);
  }
}
