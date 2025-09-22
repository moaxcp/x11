package com.github.moaxcp.x11.struct;

import java.util.Arrays;

public interface Expression {

  class Constant implements Expression {

    private final long value;

    Constant(long value) {
      this.value = value;
    }

    @Override
    public boolean isConstant(Pointer<?, ? extends Type<?>> pointer) {
      return true;
    }

    @Override
    public long evaluate(Pointer<?, ? extends Type<?>> pointer) {
      return value;
    }
  }

  class ValueOf implements Expression {

    private final int position;

    ValueOf(int position) {
      this.position = position;
    }

    @Override
    public boolean isConstant(Pointer<?, ? extends Type<?>> pointer) {
      return pointer.getType(position).isConstant();
    }

    @Override
    public long evaluate(Pointer<?, ? extends Type<?>> pointer) {
      return ((NumberType<?>) pointer.getType(position)).get(pointer).longValue();
    }
  }

  class Sum implements Expression {

    private final Expression[] expressions;

    Sum(Expression... expressions) {
      this.expressions = expressions;
    }

    @Override
    public boolean isConstant(Pointer<?, ? extends Type<?>> pointer) {
      return Arrays.stream(expressions).allMatch(e -> e.isConstant(pointer));
    }

    @Override
    public long evaluate(Pointer<?, ? extends Type<?>> pointer) {
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

  boolean isConstant(Pointer<?, ? extends Type<?>> pointer);

  long evaluate(Pointer<?, ? extends Type<?>> pointer);
}
