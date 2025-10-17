package com.github.moaxcp.x11.struct;


public abstract sealed class Type permits PadType, ValueType {
  protected final int position;

  public Type(int position) {
    this.position = position;
  }

  protected abstract Type copy(int position);

  public final int getPosition() {
    return position;
  }

  public final long getOffset(Pointer<?, ? extends Type> pointer) {
    long offset = pointer.getOffset();
    for (int i = 0; i < getPosition(); i++) {
      offset += pointer.getType(i).getByteLength(pointer);
    }
    return offset;
  }

  public abstract long getByteLength(Pointer<?, ? extends Type> pointer);

  public abstract boolean isFixedLength(Pointer<?, ? extends Type> pointer);

  public abstract void allocate(Pointer<?, ? extends Type> pointer);

  public abstract void remove(Pointer<?, ? extends Type> pointer);
}
