package com.github.moaxcp.x11.struct;

import org.jspecify.annotations.Nullable;

public final class PadType extends Type<Byte> {

  public PadType(int position) {
    this(position, null, null);
  }

  public PadType(int position,
                 @Nullable Expression lengthExpression,
                 @Nullable Assignment assignment) {
    super(position, (byte) 0, lengthExpression, assignment);
  }

  @Override
  protected Type<Byte> copy(int position) {
    return new PadType(position, lengthExpression, assignment);
  }

  @Override
  public long getByteLength(Pointer<?, ? extends Type<?>> pointer, long index) {
    return 1;
  }

  @Override
  public boolean isFixedLength(Pointer<?, ? extends Type<?>> pointer) {
    return lengthExpression == null || lengthExpression.isConstant(pointer);
  }

  @Override
  public Byte get(Pointer<?, ? extends Type<?>> pointer, long index) {
    throw new UnsupportedOperationException("PadType does not support get operations");
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, long index, Byte value) {
    throw new UnsupportedOperationException("PadType does not support set operations");
  }

  @Override
  public void add(Pointer<?, ? extends Type<?>> pointer, long index, Byte value) {
    if (!isArray()) {
      throw new ArrayIndexOutOfBoundsException(getClass().getSimpleName() + " cannot add to non-array type at position " + getPosition());
    }
    allocate(pointer, index);
  }

  @Override
  public void add(Pointer<?, ? extends Type<?>> pointer, Byte value) {
    add(pointer, getArrayLength(pointer), value);
  }

  public void allocate(Pointer<?, ? extends Type<?>> pointer) {
    if(isArray()) {
      long length = getArrayLength(pointer);
      for (int i = 0; i < length; i++) {
        pointer.getByteArray().addInt8(getOffset(pointer, i), (byte) 0);
      }
    } else {
      pointer.getByteArray().addInt8(getOffset(pointer, 0), (byte) 0);
    }
  }

  @Override
  public void allocate(Pointer<?, ? extends Type<?>> pointer, long index) {
    checkIndexAllocate(pointer, index);
    pointer.getByteArray().addInt8(getOffset(pointer, index), (byte) 0);
    if (assignment != null) {
      assignment.assign(pointer, 1);
    }
  }
}
