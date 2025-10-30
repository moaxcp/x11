package com.github.moaxcp.x11.struct;

import org.jspecify.annotations.Nullable;

import java.math.BigInteger;

@FunctionalInterface
public interface ArrayLengthListener {
  enum Reason {
    ARRAY_LENGTH,
    LENGTH_FIELD
  }

  static ArrayLengthListener lengthField(int position) {
    return new SetLengthFieldListener(position);
  }

  class SetLengthFieldListener implements ArrayLengthListener {
    private final int position;

    public SetLengthFieldListener(int position) {
      this.position = position;
    }

    @Override
    public void arrayLengthChanged(Reason reason, Pointer<?, ? extends Type<?>> pointer, long previous, long current) {
      if(reason == Reason.LENGTH_FIELD) {
        return;
      }
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

      SetLengthFieldListener that = (SetLengthFieldListener) o;
      return position == that.position;
    }

    @Override
    public int hashCode() {
      return position;
    }
  }

  void arrayLengthChanged(@Nullable Reason reason, Pointer<?, ? extends Type<?>> pointer, long previous, long current);
}
