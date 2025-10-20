package com.github.moaxcp.x11.struct;

@FunctionalInterface
public interface ByteLengthChangeListener {
  static ByteLengthChangeListener align(int alignPosition) {
    return ((pointer, previous, current) -> {
      ((PadType) pointer.getType(alignPosition)).reAlign(pointer, previous, current);
    });
  }

  void byteLengthChanged(Pointer<?, ? extends Type> pointer, long previous, long current);
}
