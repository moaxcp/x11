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
  public Long get(Pointer<?, ? extends Type<?>> pointer) {
    throw new UnsupportedOperationException("get(Pointer) not supported for Int64Type. Use getInt64(Pointer) instead.");
  }

  @Override
  public Long get(Pointer<?, ? extends Type<?>> pointer, long index) {
    throw new UnsupportedOperationException("get(Pointer, long) not supported for Int64Type. Use getInt64(Pointer, long) instead.");
  }

  public long getInt64(Pointer<?, ? extends Type<?>> pointer) {
    return pointer.getByteArray().int64(getOffset(pointer));
  }

  public long getInt64(Pointer<?, ? extends Type<?>> pointer, long index) {
    return pointer.getByteArray().int64(getOffset(pointer, index));
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, Long value) {
    throw new UnsupportedOperationException("set(Pointer, Long) not supported for Int64Type. Use setInt64(Pointer, long) instead.");
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, long index, Long value) {
    throw new UnsupportedOperationException("set(Pointer, long, Long) not supported for Int64Type. Use setInt64(Pointer, long, long) instead.");
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, long value) {
    pointer.getByteArray().int64(getOffset(pointer), value);
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, long index, long value) {
    pointer.getByteArray().int64(getOffset(pointer, index), value);
  }

  @Override
  public void allocate(Pointer<?, ? extends Type<?>> pointer, long index) {
    pointer.getByteArray().addInt64(getOffset(pointer, index), constantValue != null ? constantValue : 0L);
  }
}
