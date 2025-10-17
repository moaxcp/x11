package com.github.moaxcp.x11.struct;

import java.util.ArrayList;
import java.util.List;

public abstract class StructTypeBuilder<SELF extends StructTypeBuilder<SELF>> {

  private int position;
  private Expression lengthExpression;
  private Assignment assignment;
  private Struct constant;
  private List<Type> fields = new ArrayList<>();

  public int fields() {
    return fields.size();
  }

  public SELF position(int position) {
    this.position = position;
    return (SELF) this;
  }

  public SELF lengthField(int lengthFieldPosition) {
    this.lengthExpression = Expression.valueOf(lengthFieldPosition);
    this.assignment = Assignment.add(lengthFieldPosition);
    return (SELF) this;
  }

  public SELF lengthExpression(Expression lengthExpression) {
    this.lengthExpression = lengthExpression;
    return (SELF) this;
  }

  public SELF assignment(Assignment assingment) {
    this.assignment = assingment;
    return (SELF) this;
  }

  public StructTypePrimitiveSubBuilder<SELF> primitive() {
    return new StructTypePrimitiveSubBuilder<>((SELF) this, fields.size());
  }

  SELF primitive(Type type) {
    fields.add(type);
    return (SELF) this;
  }

  public SELF bool() {
    return primitive().bool();
  }

  public SELF boolArray(int lengthPosition) {
    return primitive().lengthField(lengthPosition).bool();
  }

  public SELF boolArray(Expression expression) {
    return primitive().lengthExpression(expression).bool();
  }

  public SELF int8() {
    return primitive().int8();
  }

  public SELF int8Array(int lengthPosition) {
    return primitive().lengthField(lengthPosition).int8();
  }

  public SELF int8Array(Expression expression) {
    return primitive().lengthExpression(expression).int8();
  }

  public SELF uint8() {
    return primitive().uint8();
  }
  public SELF uint8Array(int lengthPosition) {
    return primitive().lengthField(lengthPosition).uint8();
  }

  public SELF uint8Array(Expression expression) {
    return primitive().lengthExpression(expression).uint8();
  }

  public SELF int16() {
    return primitive().int16();
  }

  public SELF int16Array(int lengthPosition) {
    return primitive().lengthField(lengthPosition).int16();
  }

  public SELF int16Array(Expression expression) {
    return primitive().lengthExpression(expression).int16();
  }

  public SELF uint16() {
    return primitive().uint16();
  }

  public SELF uint16Array(int lengthPosition) {
    return primitive().lengthField(lengthPosition).uint16();
  }

  public SELF uint16Array(Expression expression) {
    return primitive().lengthExpression(expression).uint16();
  }

  public SELF int32() {
    return primitive().int32();
  }

  public SELF int32Array(int lengthPosition) {
    return primitive().lengthField(lengthPosition).int32();
  }

  public SELF int32Array(Expression expression) {
    return primitive().lengthExpression(expression).int32();
  }
  public SELF uint32() {
    return primitive().uint32();
  }

  public SELF uint32Array(int lengthPosition) {
    return primitive().lengthField(lengthPosition).uint32();
  }

  public SELF uint32Array(Expression expression) {
    return primitive().lengthExpression(expression).uint32();
  }

  public SELF int64() {
    return primitive().int64();
  }

  public SELF int64Array(int lengthPosition) {
    return primitive().lengthField(lengthPosition).int64();
  }

  public SELF int64Array(Expression expression) {
    return primitive().lengthExpression(expression).int64();
  }

  public SELF uint64() {
    return primitive().uint64();
  }
  public SELF uint64Array(int lengthPosition) {
    return primitive().lengthField(lengthPosition).uint64();
  }

  public SELF uint64Array(Expression expression) {
    return primitive().lengthExpression(expression).uint64();
  }

  public SELF float32() {
    return primitive().float32();
  }

  public SELF float32Array(int lengthPosition) {
    return primitive().lengthField(lengthPosition).float32();
  }

  public SELF float32Array(Expression expression) {
    return primitive().lengthExpression(expression).float32();
  }

  public SELF float64() {
    return primitive().float64();
  }

  public SELF float64Array(int lengthPosition) {
    return primitive().lengthField(lengthPosition).float64();
  }

  public SELF float64Array(Expression expression) {
    return primitive().lengthExpression(expression).float64();
  }

  public ChildStructTypeBuilder<SELF> struct() {
    return new ChildStructTypeBuilder<>((SELF) this, fields.size());
  }

  public ChildStructTypeBuilder<SELF> structArray(int lengthPosition) {
    return new ChildStructTypeBuilder<>((SELF) this, fields.size()).lengthField(lengthPosition);
  }

  public SELF constant(Struct constant) {
    this.constant = constant;
    return (SELF) this;
  }

  public StructType toStructType() {
    return new StructType(position, constant, lengthExpression, assignment, fields);
  }
}
