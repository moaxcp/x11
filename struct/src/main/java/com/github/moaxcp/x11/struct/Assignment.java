package com.github.moaxcp.x11.struct;

public interface Assignment {

  public static Assignment noop() {
    return (pointer, previous, added) -> {};
  }

  public static Assignment add(int position) {
    return (pointer, previous, added) -> {
      if (pointer.getType(position).isConstant()) {
        throw new IllegalStateException("cannot add to constant in Assignment.add");
      }
      ((NumberType) pointer.getType(position)).set(pointer, previous + added);
    };
  }

  void assign(Pointer<?, ? extends Type<?>> pointer, long previousValue, long added);
}
