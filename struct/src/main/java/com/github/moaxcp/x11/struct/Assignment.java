package com.github.moaxcp.x11.struct;

import java.math.BigInteger;

public interface Assignment {

  class Noop implements Assignment {

    @Override
    public boolean positionUsed(Pointer<?, ? extends Type<?>> pointer, int position) {
      return false;
    }

    @Override
    public void assign(Pointer<?, ? extends Type<?>> pointer, long added) {

    }
  }

  static class Add implements Assignment {

    private int position;

    public Add(int position) {
      this.position = position;
    }

    @Override
    public boolean positionUsed(Pointer<?, ? extends Type<?>> pointer, int position) {
      return this.position == position;
    }

    @Override
    public void assign(Pointer<?, ? extends Type<?>> pointer, long added) {
      var type = pointer.getType(position);
      if (type.isConstant(pointer)) {
        throw new IllegalStateException("cannot add to constant in Assignment.add");
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
          BigInteger previous = u64.get(pointer);
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
        case StructType s -> throw new IllegalArgumentException("cannot add to non-integer type in Assignment.add");
        case null -> throw new IllegalArgumentException("cannot add to null type in Assignment.add");
      }
    }
  }

  static Assignment noop() {
    return new Noop();
  }

  static Assignment add(int position) {
    return new Add(position);
  }

  boolean positionUsed(Pointer<?, ? extends Type<?>> pointer, int position);

  void assign(Pointer<?, ? extends Type<?>> pointer, long added);

  //todo add direct assignment of length for allocation
}
