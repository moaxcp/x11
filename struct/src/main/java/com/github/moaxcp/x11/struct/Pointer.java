package com.github.moaxcp.x11.struct;

public interface Pointer<SELF extends Pointer<SELF, T>, T extends Type<SELF>> extends ByteArrayListener {
  SELF copy();

  long getOffset();

  SELF setOffset(long offset);

  long getByteLength();

  T getType();

  <V extends Type<?>> V getType(int position);

  ByteArray getByteArray();

  void setByteArray(ByteArray memory);

  default void shift(ShiftBytes shift) {
    if (getOffset() >= shift.offset()) {
      var offset = getOffset() + shift.size();
      setOffset(offset);
    }
  }
}
