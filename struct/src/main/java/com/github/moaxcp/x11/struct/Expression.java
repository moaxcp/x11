package com.github.moaxcp.x11.struct;

import java.math.BigInteger;
import java.util.Arrays;

public interface Expression {

  class Constant implements Expression {

    private final long value;

    Constant(long value) {
      this.value = value;
    }

    @Override
    public boolean isConstant(Pointer<?, ? extends Type> pointer) {
      return true;
    }

    @Override
    public long evaluate(Pointer<?, ? extends Type> pointer) {
      return value;
    }
  }

  class ValueOf implements Expression {

    private final int position;

    ValueOf(int position) {
      this.position = position;
    }

    @Override
    public boolean isConstant(Pointer<?, ? extends Type> pointer) {
      return pointer.getType(position) instanceof ValueType<?> v && v.isConstant(pointer);
    }

    @Override
    public long evaluate(Pointer<?, ? extends Type> pointer) {
      var type = pointer.getType(position);
      return switch (type) {
        case Int8Type i8 -> i8.getInt8(pointer);
        case Uint8Type u8 -> u8.getUint8(pointer);
        case Int16Type i16 -> i16.getInt16(pointer);
        case Uint16Type u16 -> u16.getUint16(pointer);
        case Int32Type i32 -> i32.getInt32(pointer);
        case Uint32Type u32 -> u32.getUint32(pointer);
        case Int64Type i64 -> i64.getInt64(pointer);
        case Uint64Type u64 -> {
          var value = u64.get(pointer);
          if (value.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
            throw new IllegalArgumentException("cannot convert " + value + " to long");
          }
          yield value.longValue();
        }
        case Float32Type f32 -> (long) f32.getFloat32(pointer);
        case Float64Type f32 -> (long) f32.getFloat64(pointer);
        case BoolType ignored -> throw new IllegalArgumentException("cannot evaluate boolean type");
        case StructType ignored -> throw new IllegalArgumentException("cannot evaluate struct type");
        case PadType ignored -> throw new IllegalArgumentException("cannot evaluate pad type");
        case null -> throw new IllegalArgumentException("cannot evaluate null type");
      };
    }
  }

  class Sum implements Expression {

    private final Expression[] expressions;

    Sum(Expression... expressions) {
      this.expressions = expressions;
    }

    @Override
    public boolean isConstant(Pointer<?, ? extends Type> pointer) {
      return Arrays.stream(expressions).allMatch(e -> e.isConstant(pointer));
    }

    @Override
    public long evaluate(Pointer<?, ? extends Type> pointer) {
      return Arrays.stream(expressions).mapToLong(e -> e.evaluate(pointer)).sum();
    }
  }

  static Expression constant(long value) {
    return new Constant(value);
  }

  static Expression valueOf(int position) {
    return new ValueOf(position);
  }

  static Expression sum(Expression... expressions) {
    return new Sum(expressions);
  }

  boolean isConstant(Pointer<?, ? extends Type> pointer);

  long evaluate(Pointer<?, ? extends Type> pointer);
}
