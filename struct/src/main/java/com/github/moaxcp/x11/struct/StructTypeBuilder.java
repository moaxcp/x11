package com.github.moaxcp.x11.struct;

import java.util.ArrayList;
import java.util.List;

public class StructTypeBuilder {

  public static StructTypeBuilder structType() {
    return new StructTypeBuilder();
  }
  private int position;
  private Expression lengthExpression;
  private Assignment assignment;
  private List<Type<?>> fields = new ArrayList<>();

  public StructTypeBuilder position(int position) {
    this.position = position;
    return this;
  }

  public StructTypeBuilder lengthExpression(Expression lengthExpression) {
    this.lengthExpression = lengthExpression;
    return this;
  }

  public StructTypeBuilder assignment(Assignment assingment) {
    this.assignment = assingment;
    return this;
  }

  public StructTypeBuilder int8() {
    fields.add(new Int8Type(fields.size()));
    return this;
  }

  public StructTypeBuilder int8(long constantValue) {
    fields.add(new Int8Type(fields.size(), (byte) constantValue, null, null));
    return this;
  }

  public StructTypeBuilder int8Array(int lengthPosition) {
    fields.add(new Int8Type(fields.size(), null, Expression.valueOf(lengthPosition), Assignment.add(lengthPosition)));
    return this;
  }

  public StructTypeBuilder int8Array(int length, long constantValue) {
    fields.add(new Int8Type(fields.size(), (byte) constantValue, Expression.constant(length), null));
    return this;
  }

  public StructTypeBuilder uint8() {
    fields.add(new Uint8Type(fields.size()));
    return this;
  }

  public StructTypeBuilder uint8(long constantValue) {
    fields.add(new Uint8Type(fields.size(), (short) constantValue, null, null));
    return this;
  }

  public StructTypeBuilder uint8Array(int lengthPosition) {
    fields.add(new Uint8Type(fields.size(), null, Expression.valueOf(lengthPosition), Assignment.add(lengthPosition)));
    return this;
  }

  public StructTypeBuilder uint8Array(int length, long constantValue) {
    fields.add(new Uint8Type(fields.size(), (short) constantValue, Expression.constant(length), null));
    return this;
  }

  public StructTypeBuilder int16() {
    fields.add(new Int16Type(fields.size()));
    return this;
  }

  public StructTypeBuilder int16(long constantValue) {
    fields.add(new Int16Type(fields.size(), (short) constantValue, null, null));
    return this;
  }

  public StructTypeBuilder int16Array(int lengthPosition) {
    fields.add(new Int16Type(fields.size(), null, Expression.valueOf(lengthPosition), Assignment.add(lengthPosition)));
    return this;
  }

  public StructTypeBuilder int16Array(int length, long constantValue) {
    fields.add(new Int16Type(fields.size(), (short) constantValue, Expression.constant(length), null));
    return this;
  }

  public StructTypeBuilder uint16() {
    fields.add(new Uint16Type(fields.size()));
    return this;
  }

  public StructTypeBuilder uint16(long constantValue) {
    fields.add(new Uint16Type(fields.size(), (int) constantValue, null, null));
    return this;
  }

  public StructTypeBuilder uint16Array(int lengthPosition) {
    fields.add(new Uint16Type(fields.size(), null, Expression.valueOf(lengthPosition), Assignment.add(lengthPosition)));
    return this;
  }

  public StructTypeBuilder uint16Array(int length, long constantValue) {
    fields.add(new Uint16Type(fields.size(), (int) constantValue, Expression.constant(length), null));
    return this;
  }

  public StructTypeBuilder int32() {
    fields.add(new Int32Type(fields.size()));
    return this;
  }

  public StructTypeBuilder int32(long constantValue) {
    fields.add(new Int32Type(fields.size(), (int) constantValue, null, null));
    return this;
  }

  public StructTypeBuilder int32Array(int lengthPosition) {
    fields.add(new Int32Type(fields.size(), null, Expression.valueOf(lengthPosition), Assignment.add(lengthPosition)));
    return this;
  }

  public StructTypeBuilder int32Array(int length, long constantValue) {
    fields.add(new Int32Type(fields.size(), (int) constantValue, Expression.constant(length), null));
    return this;
  }

  public StructTypeBuilder uint32() {
    fields.add(new Uint32Type(fields.size()));
    return this;
  }

  public StructTypeBuilder uint32(long constantValue) {
    fields.add(new Uint32Type(fields.size(), (long) constantValue, null, null));
    return this;
  }

  public StructTypeBuilder uint32Array(int lengthPosition) {
    fields.add(new Uint32Type(fields.size(), null, Expression.valueOf(lengthPosition), Assignment.add(lengthPosition)));
    return this;
  }

  public StructTypeBuilder uint32Array(int length, long constantValue) {
    fields.add(new Uint32Type(fields.size(), (long) constantValue, Expression.constant(length), null));
    return this;
  }

  public StructTypeBuilder int64() {
    fields.add(new Int64Type(fields.size()));
    return this;
  }

  public StructTypeBuilder int64(long constantValue) {
    fields.add(new Int64Type(fields.size(), (long) constantValue, null, null));
    return this;
  }

  public StructTypeBuilder int64Array(int lengthPosition) {
    fields.add(new Int64Type(fields.size(), null, Expression.valueOf(lengthPosition), Assignment.add(lengthPosition)));
    return this;
  }

  public StructTypeBuilder int64Array(int length, long constantValue) {
    fields.add(new Int64Type(fields.size(), (long) constantValue, Expression.constant(length), null));
    return this;
  }

  public StructTypeBuilder uint64() {
    fields.add(new Uint64Type(fields.size()));
    return this;
  }

  public StructTypeBuilder uint64(java.math.BigInteger constantValue) {
    fields.add(new Uint64Type(fields.size(), constantValue, null, null));
    return this;
  }

  public StructTypeBuilder uint64Array(int lengthPosition) {
    fields.add(new Uint64Type(fields.size(), null, Expression.valueOf(lengthPosition), Assignment.add(lengthPosition)));
    return this;
  }

  public StructTypeBuilder uint64Array(int length, java.math.BigInteger constantValue) {
    fields.add(new Uint64Type(fields.size(), constantValue, Expression.constant(length), null));
    return this;
  }

  public StructTypeBuilder float32() {
    fields.add(new Float32Type(fields.size()));
    return this;
  }

  public StructTypeBuilder float32(float constantValue) {
    fields.add(new Float32Type(fields.size(), constantValue, null, null));
    return this;
  }

  public StructTypeBuilder float32Array(int lengthPosition) {
    fields.add(new Float32Type(fields.size(), null, Expression.valueOf(lengthPosition), Assignment.add(lengthPosition)));
    return this;
  }

  public StructTypeBuilder float32Array(int length, float constantValue) {
    fields.add(new Float32Type(fields.size(), constantValue, Expression.constant(length), null));
    return this;
  }

  public StructTypeBuilder float64() {
    fields.add(new Float64Type(fields.size()));
    return this;
  }

  public StructTypeBuilder float64(double constantValue) {
    fields.add(new Float64Type(fields.size(), constantValue, null, null));
    return this;
  }

  public StructTypeBuilder float64Array(int lengthPosition) {
    fields.add(new Float64Type(fields.size(), null, Expression.valueOf(lengthPosition), Assignment.add(lengthPosition)));
    return this;
  }

  public StructTypeBuilder float64Array(int length, double constantValue) {
    fields.add(new Float64Type(fields.size(), constantValue, Expression.constant(length), null));
    return this;
  }

  public StructTypeBuilder struct(StructType structType) {
    fields.add(structType.copy(fields.size()));
    return this;
  }

  public StructType build() {
    return new StructType(position, lengthExpression, assignment, fields);
  }

  public Struct toStruct() {
    return new Struct(false, 0, build(), new ByteArray());
  }

  public Struct toStruct(long offset, ByteArray bytes) {
    return new Struct(true, offset, build(), bytes);
  }
}
