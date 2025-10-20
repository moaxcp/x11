package com.github.moaxcp.x11.struct;


import org.jspecify.annotations.Nullable;

public abstract sealed class Type permits PadType, ValueType {
  protected final int position;
  @Nullable
  protected final ByteLengthChangeListener byteLengthChange;

  public Type(int position, @Nullable ByteLengthChangeListener byteLengthChange) {
    this.position = position;
    this.byteLengthChange = byteLengthChange;
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

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;

    Type type = (Type) o;
    return position == type.position;
  }

  @Override
  public int hashCode() {
    return position;
  }
}
