package com.github.moaxcp.x11.struct;

import org.jspecify.annotations.Nullable;

import static com.github.moaxcp.x11.struct.Size.INT64;

public final class Int64Type extends NumberType<Long> {

  public static Int64Type int64Type() {
    return int64Type(-1);
  }

  public static Int64Type int64Type(int position) {
    return new Int64Type(position);
  }

  public Int64Type(int position, @Nullable Long constantValue, @Nullable Expression lengthExpression, @Nullable Assignment assignment) {
    super(position, INT64, constantValue, lengthExpression, assignment);
  }

  public Int64Type(int position) {
    super(position, INT64);
  }

  @Override
  protected Int64Type copy(int position) {
    return new Int64Type(position, constantValue, lengthExpression, assignment);
  }

  @Override
  public Long get(Pointer<?, ? extends Type<?>> pointer, long index) {
    return pointer.getByteArray().int64(getOffset(pointer, index));
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, long index, Long value) {
    pointer.getByteArray().int64(getOffset(pointer, index), value);
  }

  @Override
  public void allocate(Pointer<?, ? extends Type<?>> pointer, long index) {
    pointer.getByteArray().addInt64(getOffset(pointer, index), constantValue != null ? constantValue : 0L);
  }
}
