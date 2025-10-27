package com.github.moaxcp.x11.struct;

import org.jspecify.annotations.Nullable;

import static com.github.moaxcp.x11.struct.Primitive.INT16;

public final class Int16Type extends NumberType<Int16Type, Short> {

  public static Int16Type int16() {
    return int16(-1);
  }

  public static Int16Type int16(int position) {
    return new Int16Type(position);
  }

  public Int16Type(int position, @Nullable Short constantValue, @Nullable Expression lengthExpression) {
    super(position, INT16, constantValue, lengthExpression);
  }

  public Int16Type(int position) {
    super(position, INT16);
  }

  @Override
  protected Int16Type copy(int position) {
    return new Int16Type(position, constantValue, lengthExpression);
  }

  public short getInt16(Pointer<?, ? extends Type<?>> pointer) {
    return pointer.getByteArray().getInt16(getOffset(pointer));
  }

  public short getInt16(Pointer<?, ? extends Type<?>> pointer, long index) {
    checkIndex(pointer, index);
    return pointer.getByteArray().getInt16(getOffset(pointer, index));
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, short value) {
    setUnchecked(pointer, 0, value);
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, long index, short value) {
    checkIndex(pointer, index);
    setUnchecked(pointer, index, value);
  }

  private void setUnchecked(Pointer<?, ? extends Type<?>> pointer, long index, short value) {
    checkConstant(pointer, index, value);
    pointer.getByteArray().setInt16(getOffset(pointer, index), value);
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, short value) {
    add(pointer, getArrayLength(pointer), value);
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, long index, short value) {
    if (!isArray()) {
      throw new ArrayIndexOutOfBoundsException(getClass().getSimpleName() + " cannot add to non-array type at position " + getPosition() + " index: " + index + " length: " + 1);
    }
    allocate(pointer, index);
    setUnchecked(pointer, index, value);
  }

  public void allocate(Pointer<?, ? extends Type<?>> pointer) {
    if(isArray()) {
      long length = getArrayLength(pointer);
      for (int i = 0; i < length; i++) {
        pointer.getByteArray().addInt16(getOffset(pointer, i), constantValue != null ? constantValue : 0);
      }
    } else {
      pointer.getByteArray().addInt16(getOffset(pointer, 0), constantValue != null ? constantValue : 0);
    }
  }

  @Override
  public void allocate(Pointer<?, ? extends Type<?>> pointer, long index) {
    callWithArrayLengthChange(pointer, 1, () -> {
      callWithByteLengthChange(pointer, () -> {
        checkIndexAllocate(pointer, index);
        pointer.getByteArray().addInt16(getOffset(pointer, index), constantValue != null ? constantValue : 0);
      });
    });
  }
}
