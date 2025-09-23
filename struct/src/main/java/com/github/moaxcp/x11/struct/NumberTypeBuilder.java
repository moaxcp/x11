package com.github.moaxcp.x11.struct;

import org.jspecify.annotations.Nullable;

import java.math.BigInteger;

public final class NumberTypeBuilder {

  public static NumberTypeBuilder number() {
    return new NumberTypeBuilder();
  }

  private int position = -1;
  @Nullable
  private Number constantValue;
  @Nullable
  private Expression lengthExpression;
  @Nullable
  private Assignment assignment;

  public NumberTypeBuilder position(int position) {
    this.position = position;
    return this;
  }

  public NumberTypeBuilder constant(Number constantValue) {
    this.constantValue = constantValue;
    return this;
  }

  public NumberTypeBuilder lengthExpression(Expression lengthExpression) {
    this.lengthExpression = lengthExpression;
    return this;
  }

  public NumberTypeBuilder assignment(Assignment assignment) {
    this.assignment = assignment;
    return this;
  }

  public Int8Type int8() {
    return new Int8Type(position, (Byte) constantValue, lengthExpression, assignment);
  }

  public Uint8Type uint8() {
    return new Uint8Type(position, (Short) constantValue, lengthExpression, assignment);
  }

  public Int16Type int16() {
    return new Int16Type(position, (Short) constantValue, lengthExpression, assignment);
  }

  public Uint16Type uint16() {
    return new Uint16Type(position, (Integer) constantValue, lengthExpression, assignment);
  }

  public Int32Type int32() {
    return new Int32Type(position, (Integer) constantValue, lengthExpression, assignment);
  }

  public Uint32Type uint32() {
    return new Uint32Type(position, (Long) constantValue, lengthExpression, assignment);
  }

  public Int64Type int64() {
    return new Int64Type(position, (Long) constantValue, lengthExpression, assignment);
  }

  public Uint64Type uint64() {
    return new Uint64Type(position, (BigInteger) constantValue, lengthExpression, assignment);
  }

  public Float32Type float32() {
    return new Float32Type(position, (Float) constantValue, lengthExpression, assignment);
  }

  public Float64Type float64() {
    return new Float64Type(position, (Double) constantValue, lengthExpression, assignment);
  }


}
