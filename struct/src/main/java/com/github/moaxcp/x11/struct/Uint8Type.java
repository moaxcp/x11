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
  public Short get(Pointer<?, ? extends Type<?>> pointer, long index) {
    return pointer.getByteArray().uint8(getOffset(pointer, index));
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, long index, Short value) {
    pointer.getByteArray().uint8(getOffset(pointer, index), value);
  }

  @Override
  public void allocate(Pointer<?, ? extends Type<?>> pointer, long index) {
    pointer.getByteArray().addUint8(getOffset(pointer, index), constantValue != null ? constantValue : 0);
  }
}
