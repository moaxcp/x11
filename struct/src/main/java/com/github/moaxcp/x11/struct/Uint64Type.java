package com.github.moaxcp.x11.struct;

import org.jspecify.annotations.Nullable;

import java.math.BigInteger;

import static com.github.moaxcp.x11.struct.Size.UINT64;

public final class Uint64Type extends NumberType<BigInteger> {

  public static Uint64Type uint64Type() {
    return uint64Type(-1);
  }

  public static Uint64Type uint64Type(int position) {
    return new Uint64Type(position);
  }

  public Uint64Type(int position, @Nullable BigInteger constantValue, @Nullable Expression lengthExpression, @Nullable Assignment assignment) {
    super(position, UINT64, constantValue, lengthExpression, assignment);
  }

  public Uint64Type(int position) {
    super(position, UINT64);
  }

  @Override
  protected Uint64Type copy(int position) {
    return new Uint64Type(position, constantValue, lengthExpression, assignment);
  }

  @Override
  public BigInteger get(Pointer<?, ? extends Type<?>> pointer, long index) {
    return pointer.getByteArray().uint64(getOffset(pointer, index));
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, long index, BigInteger value) {
    pointer.getByteArray().uint64(getOffset(pointer, index), value);
  }

  @Override
  public void allocate(Pointer<?, ? extends Type<?>> pointer, long index) {
    pointer.getByteArray().addUint64(getOffset(pointer, index), constantValue != null ? constantValue : BigInteger.ZERO);
  }
}
