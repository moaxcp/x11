package com.github.moaxcp.x11.struct;

public interface Pointer<SELF extends Pointer<SELF>> extends ByteArray.Listener {
  SELF copy();

  int getOffset();

  SELF setOffset(int offset);

  int getByteLength();

  Type<SELF> getType();

  ByteArray getByteArray();

  default void event(ByteArray.ByteShift event) {
    if (getOffset() >= event.offset()) {
      setOffset(getOffset() + event.size());
    }
  }
}
