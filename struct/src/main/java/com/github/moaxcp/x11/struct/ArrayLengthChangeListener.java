package com.github.moaxcp.x11.struct;

import java.math.BigInteger;

@FunctionalInterface
public interface ArrayLengthChangeListener {
  static ArrayLengthChangeListener lengthField(int position) {
    return new SetLengthFieldChangeListener(position);
  }

  class SetLengthFieldChangeListener implements ArrayLengthChangeListener {
    private final int position;

    public SetLengthFieldChangeListener(int position) {
      this.position = position;
    }

    @Override
    public void arrayLengthChanged(Pointer<?, ? extends Type<?>> pointer, long previous, long current) {
      switch (((NumberType) pointer.getType(position))) {
        case Float32Type t -> t.set(pointer, (float) current);
        case Float64Type t -> t.set(pointer, (double) current);
        case Int16Type t -> t.set(pointer, (short) current);
        case Int32Type t -> t.set(pointer, (int) current);
        case Int64Type t -> t.set(pointer, current);
        case Int8Type t -> t.set(pointer, (byte) current);
        case Uint8Type t -> t.set(pointer, (short) current);
        case Uint16Type t -> t.set(pointer, (short) current);
        case Uint32Type t -> t.set(pointer, (int) current);
        case Uint64Type t -> t.set(pointer, BigInteger.valueOf(current));
      }
    }

    @Override
    public String toString() {
      return "SetLengthFieldChangeListener{" +
          "position=" + position +
          '}';
    }

    @Override
    public boolean equals(Object o) {
      if (o == null || getClass() != o.getClass()) return false;

      SetLengthFieldChangeListener that = (SetLengthFieldChangeListener) o;
      return position == that.position;
    }

    @Override
    public int hashCode() {
      return position;
    }
  }

  void arrayLengthChanged(Pointer<?, ? extends Type<?>> pointer, long previous, long current);
}
