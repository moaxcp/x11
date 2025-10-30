package com.github.moaxcp.x11.struct;


import java.util.ArrayList;
import java.util.List;

public abstract sealed class Type<SELF extends Type<SELF>> permits PadType, ValueType {
  protected final int position;
  protected List<ByteLengthListener> byteLengthListeners = new ArrayList<>();

  public Type(int position) {
    this.position = position;
  }

  protected abstract SELF copy(int position);

  public final SELF addByteLengthChangeListener(ByteLengthListener listener) {
    byteLengthListeners.add(listener);
    return (SELF) this;
  }

  public final SELF addByteLengthChangeListeners(List<ByteLengthListener> listeners) {
    byteLengthListeners.addAll(listeners);
    return (SELF) this;
  }

  public final int getPosition() {
    return position;
  }

  public <V extends Type<?>> V getType(int position) {
    return null;
  }

  public final long getOffset(Pointer<?, ? extends Type<?>> pointer) {
    long offset = pointer.getOffset();
    for (int i = 0; i < getPosition(); i++) {
      offset += pointer.getType(i).getByteLength(pointer);
    }
    return offset;
  }

  public abstract long getAllocationLength(Type<?> parent);

  public abstract long getByteLength(Pointer<?, ? extends Type<?>> pointer);

  public abstract boolean isFixedLength(Pointer<?, ? extends Type<?>> pointer);

  public abstract void allocate(Pointer<?, ? extends Type<?>> pointer);

  protected final void notifyByteLengthChange(Pointer<?, ? extends Type<?>> pointer, long previousLength, long currentLength) {
    byteLengthListeners.forEach(b -> b.byteLengthChanged(pointer, previousLength, currentLength));
  }

  protected void callWithByteLengthChange(Pointer<?, ? extends Type<?>> pointer, Runnable runnable) {
    if (byteLengthListeners.isEmpty()) {
      runnable.run();
      return;
    }
    var previous = getByteLength(pointer);
    runnable.run();
    var current = getByteLength(pointer);
    notifyByteLengthChange(pointer, previous, current);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;

    Type<?> type = (Type<?>) o;
    return position == type.position && byteLengthListeners.equals(type.byteLengthListeners);
  }

  @Override
  public int hashCode() {
    int result = position;
    result = 31 * result + byteLengthListeners.hashCode();
    return result;
  }
}
