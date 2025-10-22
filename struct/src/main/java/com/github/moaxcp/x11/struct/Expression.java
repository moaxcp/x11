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
    public boolean isConstant(Type<?> type) {
      return true;
    }

    @Override
    public long defaultValue(Type<?> type) {
      return value;
    }

    @Override
    public long evaluate(Pointer<?, ? extends Type<?>> pointer) {
      return value;
    }

    @Override
    public String toString() {
      return "Constant{" +
          "value=" + value +
          '}';
    }

    @Override
    public boolean equals(Object o) {
      if (o == null || getClass() != o.getClass()) return false;

      Constant constant = (Constant) o;
      return value == constant.value;
    }

    @Override
    public int hashCode() {
      return Long.hashCode(value);
    }
  }

  class ValueOf implements Expression {

    private final int position;

    ValueOf(int position) {
      this.position = position;
    }

    @Override
    public boolean isConstant(Type<?> type) {
      return type.getType(position) instanceof NumberType<?, ?> v && v.isConstant(type);
    }

    @Override
    public long defaultValue(Type<?> type) {
      return ((NumberType<?, ?>) type.getType(position)).defaultValue();
    }

    @Override
    public long evaluate(Pointer<?, ? extends Type<?>> pointer) {
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
          var value = u64.getUint64(pointer);
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

    @Override
    public String toString() {
      return "ValueOf{" +
          "position=" + position +
          '}';
    }

    @Override
    public boolean equals(Object o) {
      if (o == null || getClass() != o.getClass()) return false;

      ValueOf valueOf = (ValueOf) o;
      return position == valueOf.position;
    }

    @Override
    public int hashCode() {
      return position;
    }
  }

  class Sum implements Expression {

    private final Expression[] expressions;

    Sum(Expression... expressions) {
      this.expressions = expressions;
    }

    @Override
    public boolean isConstant(Type<?> type) {
      return Arrays.stream(expressions).allMatch(e -> e.isConstant(type));
    }

    @Override
    public long defaultValue(Type<?> type) {
      return Arrays.stream(expressions).mapToLong(e -> e.defaultValue(type)).sum();
    }

    @Override
    public long evaluate(Pointer<?, ? extends Type<?>> pointer) {
      return Arrays.stream(expressions).mapToLong(e -> e.evaluate(pointer)).sum();
    }

    @Override
    public String toString() {
      return "Sum{" +
          "expressions=" + Arrays.toString(expressions) +
          '}';
    }

    @Override
    public boolean equals(Object o) {
      if (o == null || getClass() != o.getClass()) return false;

      Sum sum = (Sum) o;
      return Arrays.equals(expressions, sum.expressions);
    }

    @Override
    public int hashCode() {
      return Arrays.hashCode(expressions);
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

  boolean isConstant(Type<?> type);

  long defaultValue(Type<?> type);

  long evaluate(Pointer<?, ? extends Type<?>> pointer);
}
