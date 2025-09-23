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
  private Struct constant;
  private List<Type<?>> fields = new ArrayList<>();

  public int fields() {
    return fields.size();
  }

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

  public StructTypeNumberBuilder number() {
    return new StructTypeNumberBuilder(this, fields.size());
  }

  StructTypeBuilder field(Type<?> type) {
    fields.add(type);
    return this;
  }

  public StructTypeBuilder int8() {
    return number().int8();
  }

  public StructTypeBuilder int8Array(int lengthPosition) {
    return number().lengthField(lengthPosition).int8();
  }

  public StructTypeBuilder uint8() {
    return number().uint8();
  }
  public StructTypeBuilder uint8Array(int lengthPosition) {
    return number().lengthField(lengthPosition).uint8();
  }

  public StructTypeBuilder int16() {
    return number().int16();
  }

  public StructTypeBuilder int16Array(int lengthPosition) {
    return number().lengthField(lengthPosition).int16();
  }

  public StructTypeBuilder uint16() {
    return number().uint16();
  }

  public StructTypeBuilder uint16Array(int lengthPosition) {
    return number().lengthField(lengthPosition).uint16();
  }

  public StructTypeBuilder int32() {
    return number().int32();
  }

  public StructTypeBuilder int32Array(int lengthPosition) {
    return number().lengthField(lengthPosition).int32();
  }
  public StructTypeBuilder uint32() {
    return number().uint32();
  }

  public StructTypeBuilder uint32Array(int lengthPosition) {
    return number().lengthField(lengthPosition).uint32();
  }

  public StructTypeBuilder int64() {
    return number().int64();
  }

  public StructTypeBuilder int64Array(int lengthPosition) {
    return number().lengthField(lengthPosition).int64();
  }

  public StructTypeBuilder uint64() {
    return number().uint64();
  }
  public StructTypeBuilder uint64Array(int lengthPosition) {
    return number().lengthField(lengthPosition).uint64();
  }

  public StructTypeBuilder float32() {
    return number().float32();
  }

  public StructTypeBuilder float32Array(int lengthPosition) {
    return number().lengthField(lengthPosition).float32();
  }

  public StructTypeBuilder float64() {
    return number().float64();
  }

  public StructTypeBuilder float64Array(int lengthPosition) {
    return number().lengthField(lengthPosition).float64();
  }

  public StructTypeStructTypeBuilder struct() {
    return new StructTypeStructTypeBuilder(this, fields.size());
  }

  public StructTypeStructTypeBuilder structArray(int lengthPosition) {
    return new StructTypeStructTypeBuilder(this, fields.size()).lengthField(lengthPosition);
  }

  public StructTypeBuilder struct(StructType structType) {
    fields.add(structType.copy(fields.size()));
    return this;
  }

  public StructTypeBuilder constant(Struct constant) {
    this.constant = constant;
    return this;
  }

  public StructType build() {
    return new StructType(position, constant, lengthExpression, assignment, fields);
  }

  public Struct toStruct() {
    return new Struct(false, 0, build(), new ByteArray());
  }

  public Struct toStruct(long offset, ByteArray bytes) {
    return new Struct(true, offset, build(), bytes);
  }
}
