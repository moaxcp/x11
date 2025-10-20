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

  public Uint32Type(int position, @Nullable ByteLengthChangeListener byteLengthChange, @Nullable Long constantValue, @Nullable Expression lengthExpression, @Nullable Assignment assignment) {
    super(position, byteLengthChange, UINT32, constantValue, lengthExpression, assignment);
  }

  public Uint32Type(int position) {
    super(position, UINT32);
  }

  @Override
  protected Uint32Type copy(int position) {
    return new Uint32Type(position, byteLengthChange, constantValue, lengthExpression, assignment);
  }

  public long getUint32(Pointer<?, ? extends Type> pointer) {
    return pointer.getByteArray().getUint32(getOffset(pointer));
  }

  public long getUint32(Pointer<?, ? extends Type> pointer, long index) {
    checkIndex(pointer, index);
    return pointer.getByteArray().getUint32(getOffset(pointer, index));
  }

  public void set(Pointer<?, ? extends Type> pointer, long value) {
    setUnchecked(pointer, 0, value);
  }

  public void set(Pointer<?, ? extends Type> pointer, long index, long value) {
    checkIndex(pointer, index);
    setUnchecked(pointer, index, value);
  }

  private void setUnchecked(Pointer<?, ? extends Type> pointer, long index, long value) {
    checkConstant(pointer, index, value);
    pointer.getByteArray().setUint32(getOffset(pointer, index), value);
  }

  public void add(Pointer<?, ? extends Type> pointer, long value) {
    add(pointer, getArrayLength(pointer), value);
  }

  public void add(Pointer<?, ? extends Type> pointer, long index, long value) {
    if (!isArray()) {
      throw new ArrayIndexOutOfBoundsException(getClass().getSimpleName() + " cannot add to non-array type at position " + getPosition() + " index: " + index + " length: " + 1);
    }
    allocate(pointer, index);
    setUnchecked(pointer, index, value);
  }

  public void allocate(Pointer<?, ? extends Type> pointer) {
    if (isArray()) {
      long length = getArrayLength(pointer);
      for (int i = 0; i < length; i++) {
        pointer.getByteArray().addUint32(getOffset(pointer, i), constantValue != null ? constantValue : 0L);
      }
    } else {
      pointer.getByteArray().addUint32(getOffset(pointer, 0), constantValue != null ? constantValue : 0L);
    }
  }

  @Override
  public void allocate(Pointer<?, ? extends Type> pointer, long index) {
    checkIndexAllocate(pointer, index);
    pointer.getByteArray().addUint32(getOffset(pointer, index), constantValue != null ? constantValue : 0L);
    if (assignment != null) {
      assignment.assign(pointer, 1);
    }
  }
}
