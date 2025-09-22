package com.github.moaxcp.x11.struct;

import org.jspecify.annotations.Nullable;

import static com.github.moaxcp.x11.struct.Size.INT8;

public final class Int8Type extends NumberType<Byte> {

  public static Int8Type byteType() {
    return byteType(-1);
  }

  public static Int8Type byteType(int position) {
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
  public Byte get(Pointer<?, ? extends Type<?>> pointer, long index) {
    return pointer.getByteArray().int8(getOffset(pointer, index));
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, long index, Byte value) {
    pointer.getByteArray().int8(getOffset(pointer, index), value);
  }

  @Override
  public void allocate(Pointer<?, ? extends Type<?>> pointer, long index) {
    pointer.getByteArray().addInt8(getOffset(pointer, index), constantValue != null ? constantValue : 0);
  }
}
