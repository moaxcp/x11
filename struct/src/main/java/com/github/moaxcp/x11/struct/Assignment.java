package com.github.moaxcp.x11.struct;

import java.math.BigInteger;

public interface Assignment {

  static Assignment noop() {
    return (pointer, added) -> {};
  }

  static Assignment add(int position) {
    return (pointer, added) -> {
      var type = pointer.getType(position);
      if (type.isConstant()) {
        throw new IllegalStateException("cannot add to constant in Assignment.add");
      }
      switch (type) {
        case Int8Type i8 -> {
          var previous = (byte) i8.get(pointer);
          i8.set(pointer, (byte) (previous + added));
        }
        case Uint8Type u8 -> {
          var previous = (short) u8.get(pointer);
          u8.set(pointer, (short) (previous + added));
        }
        case Int16Type i16 -> {
          var previous = (short) i16.get(pointer);
          i16.set(pointer, (short) (previous + added));
        }
        case Uint16Type u16 -> {
          var previous = (int) u16.get(pointer);
          u16.set(pointer, (int) (previous + added));
        }
        case Int32Type i32 -> {
          var previous = i32.get(pointer);
          i32.set(pointer, (int) (previous + added));
        }
        case Uint32Type u32 -> {
          var previous = u32.get(pointer);
          u32.set(pointer, previous + added);
        }
        case Int64Type i64 -> {
          var previous = i64.get(pointer);
          i64.set(pointer, previous + added);
        }
        case Uint64Type u64 -> {
          var previous = u64.get(pointer);
          u64.set(pointer, previous.add(BigInteger.valueOf(added)));
        }
        case Float32Type f32 -> {
          var previous = f32.get(pointer);
          f32.set(pointer, previous + added);
        }
        case Float64Type f32 -> {
          var previous = f32.get(pointer);
          f32.set(pointer, previous + added);
        }
        case StructType s -> throw new IllegalArgumentException("cannot add to non-integer type in Assignment.add");
        case null -> throw new IllegalArgumentException("cannot add to null type in Assignment.add");
      }
    };
  }

  void assign(Pointer<?, ? extends Type<?>> pointer, long added);
}
