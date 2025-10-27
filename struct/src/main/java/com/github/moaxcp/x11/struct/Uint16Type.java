package com.github.moaxcp.x11.struct;

import org.jspecify.annotations.Nullable;

import static com.github.moaxcp.x11.struct.Primitive.UINT16;

public final class Uint16Type extends NumberType<Uint16Type, Integer> {

  public static Uint16Type uint16() {
    return uint16(-1);
  }

  public static Uint16Type uint16(int position) {
    return new Uint16Type(position);
  }

  public Uint16Type(int position, @Nullable Integer constantValue, @Nullable Expression lengthExpression) {
    super(position, UINT16, constantValue, lengthExpression);
  }

  public Uint16Type(int position) {
    super(position, UINT16);
  }

  @Override
  protected Uint16Type copy(int position) {
    return new Uint16Type(position, constantValue, lengthExpression);
  }

  public int getUint16(Pointer<?, ? extends Type<?>> pointer) {
    return pointer.getByteArray().getUint16(getOffset(pointer));
  }

  public int getUint16(Pointer<?, ? extends Type<?>> pointer, long index) {
    checkIndex(pointer, index);
    return pointer.getByteArray().getUint16(getOffset(pointer, index));
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, int value) {
    setUnchecked(pointer, 0, value);
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, long index, int value) {
    checkIndex(pointer, index);
    setUnchecked(pointer, index, value);
  }

  private void setUnchecked(Pointer<?, ? extends Type<?>> pointer, long index, int value) {
    checkConstant(pointer, index, value);
    pointer.getByteArray().setUint16(getOffset(pointer, index), value);
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, int value) {
    add(pointer, getArrayLength(pointer), value);
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, long index, int value) {
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
        pointer.getByteArray().addUint16(getOffset(pointer, i), constantValue != null ? constantValue : 0);
      }
    } else {
      pointer.getByteArray().addUint16(getOffset(pointer, 0), constantValue != null ? constantValue : 0);
    }
  }

  @Override
  public void allocate(Pointer<?, ? extends Type<?>> pointer, long index) {
    callWithArrayLengthChange(pointer, 1, () -> {
      callWithByteLengthChange(pointer, () -> {
        checkIndexAllocate(pointer, index);
        pointer.getByteArray().addUint16(getOffset(pointer, index), constantValue != null ? constantValue : 0);
      });
    });
  }
}
