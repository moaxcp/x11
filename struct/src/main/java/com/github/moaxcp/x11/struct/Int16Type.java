package com.github.moaxcp.x11.struct;

import org.jspecify.annotations.Nullable;

import static com.github.moaxcp.x11.struct.Size.INT16;

public final class Int16Type extends NumberType<Short> {

  public static Int16Type int16Type() {
    return int16Type(-1);
  }

  public static Int16Type int16Type(int position) {
    return new Int16Type(position);
  }

  public Int16Type(int position, @Nullable Short constantValue, @Nullable Expression lengthExpression, @Nullable Assignment assignment) {
    super(position, INT16, constantValue, lengthExpression, assignment);
  }

  public Int16Type(int position) {
    super(position, INT16);
  }

  @Override
  protected Int16Type copy(int position) {
    return new Int16Type(position, constantValue, lengthExpression, assignment);
  }

  @Override
  public Short get(Pointer<?, ? extends Type<?>> pointer, long index) {
    return pointer.getByteArray().int16(getOffset(pointer, index));
  }

  public short getInt16(Pointer<?, ? extends Type<?>> pointer) {
    return pointer.getByteArray().int16(getOffset(pointer));
  }

  public short getInt16(Pointer<?, ? extends Type<?>> pointer, long index) {
    return pointer.getByteArray().int16(getOffset(pointer, index));
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, long index, Short value) {
    pointer.getByteArray().int16(getOffset(pointer, index), value);
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, short value) {
    pointer.getByteArray().int16(getOffset(pointer), value);
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, long index, short value) {
    pointer.getByteArray().int16(getOffset(pointer, index), value);
  }

  @Override
  public void allocate(Pointer<?, ? extends Type<?>> pointer, long index) {
    pointer.getByteArray().addInt16(getOffset(pointer, index), constantValue != null ? constantValue : 0);
  }
}
