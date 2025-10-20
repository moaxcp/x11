package com.github.moaxcp.x11.struct;

import java.math.BigInteger;

public interface Assignment {

  class Noop implements Assignment {

    @Override
    public boolean positionUsed(Pointer<?, ? extends Type> pointer, int position) {
      return false;
    }

    @Override
    public void assign(Pointer<?, ? extends Type> pointer, long added) {

    }

    @Override
    public String toString() {
      return "Noop{}";
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }
  }

  static class Add implements Assignment {

    private final int position;

    public Add(int position) {
      this.position = position;
    }

    @Override
    public boolean positionUsed(Pointer<?, ? extends Type> pointer, int position) {
      return this.position == position;
    }

    @Override
    public void assign(Pointer<?, ? extends Type> pointer, long added) {
      var type = pointer.getType(position);
      if (type instanceof ValueType<?> v && v.isConstant(pointer)) {
        throw new IllegalStateException(v.getClass().getSimpleName() + " at position " + v.getPosition() + " is constant. Cannot add to constant.");
      }
      switch (type) {
        case Int8Type i8 -> {
          byte previous = i8.getInt8(pointer);
          i8.set(pointer, (byte) (previous + added));
        }
        case Uint8Type u8 -> {
          short previous = u8.getUint8(pointer);
          u8.set(pointer, (short) (previous + added));
        }
        case Int16Type i16 -> {
          short previous = i16.getInt16(pointer);
          i16.set(pointer, (short) (previous + added));
        }
        case Uint16Type u16 -> {
          int previous = u16.getUint16(pointer);
          u16.set(pointer, (int) (previous + added));
        }
        case Int32Type i32 -> {
          int previous = i32.getInt32(pointer);
          i32.set(pointer, (int) (previous + added));
        }
        case Uint32Type u32 -> {
          long previous = u32.getUint32(pointer);
          u32.set(pointer, previous + added);
        }
        case Int64Type i64 -> {
          long previous = i64.getInt64(pointer);
          i64.set(pointer, previous + added);
        }
        case Uint64Type u64 -> {
          BigInteger previous = u64.getUint64(pointer);
          u64.set(pointer, previous.add(BigInteger.valueOf(added)));
        }
        case Float32Type f32 -> {
          float previous = f32.getFloat32(pointer);
          f32.set(pointer, previous + added);
        }
        case Float64Type f64 -> {
          double previous = f64.getFloat64(pointer);
          f64.set(pointer, previous + added);
        }
        case BoolType ignored -> throw new IllegalArgumentException("cannot add to boolean type in Assignment.add");
        case StructType ignored -> throw new IllegalArgumentException("cannot add to non-integer type in Assignment.add");
        case PadType ignored -> throw new IllegalArgumentException("cannot add to pad type in Assignment.add");
        case null -> throw new IllegalArgumentException("cannot add to null type in Assignment.add");
      }
    }

    @Override
    public String toString() {
      return "Add{" +
          "position=" + position +
          '}';
    }

    @Override
    public boolean equals(Object o) {
      if (o == null || getClass() != o.getClass()) return false;

      Add add = (Add) o;
      return position == add.position;
    }

    @Override
    public int hashCode() {
      return position;
    }
  }

  static Assignment noop() {
    return new Noop();
  }

  static Assignment add(int position) {
    return new Add(position);
  }

  boolean positionUsed(Pointer<?, ? extends Type> pointer, int position);

  void assign(Pointer<?, ? extends Type> pointer, long added);

  //todo add direct assignment of length for allocation
}
