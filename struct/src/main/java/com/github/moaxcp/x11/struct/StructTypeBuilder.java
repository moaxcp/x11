package com.github.moaxcp.x11.struct;

import java.util.ArrayList;
import java.util.List;

public abstract class StructTypeBuilder<SELF extends StructTypeBuilder<SELF>> {

  private int position;
  private Expression lengthExpression;
  private Assignment assignment;
  private Struct constant;
  private List<Type<?>> fields = new ArrayList<>();

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

  public StructTypeFieldSubBuilder<SELF> field() {
    return new StructTypeFieldSubBuilder<>((SELF) this, fields.size());
  }

  SELF field(Type<?> type) {
    fields.add(type);
    return (SELF) this;
  }

  public SELF bool() {
    return field().bool();
  }

  public SELF boolArray(int lengthPosition) {
    return field().lengthField(lengthPosition).bool();
  }

  public SELF boolArray(Expression expression) {
    return field().lengthExpression(expression).bool();
  }

  public SELF int8() {
    return field().int8();
  }

  public SELF int8Array(int lengthPosition) {
    return field().lengthField(lengthPosition).int8();
  }

  public SELF int8Array(Expression expression) {
    return field().lengthExpression(expression).int8();
  }

  public SELF uint8() {
    return field().uint8();
  }
  public SELF uint8Array(int lengthPosition) {
    return field().lengthField(lengthPosition).uint8();
  }

  public SELF uint8Array(Expression expression) {
    return field().lengthExpression(expression).uint8();
  }

  public SELF int16() {
    return field().int16();
  }

  public SELF int16Array(int lengthPosition) {
    return field().lengthField(lengthPosition).int16();
  }

  public SELF int16Array(Expression expression) {
    return field().lengthExpression(expression).int16();
  }

  public SELF uint16() {
    return field().uint16();
  }

  public SELF uint16Array(int lengthPosition) {
    return field().lengthField(lengthPosition).uint16();
  }

  public SELF uint16Array(Expression expression) {
    return field().lengthExpression(expression).uint16();
  }

  public SELF int32() {
    return field().int32();
  }

  public SELF int32Array(int lengthPosition) {
    return field().lengthField(lengthPosition).int32();
  }

  public SELF int32Array(Expression expression) {
    return field().lengthExpression(expression).int32();
  }
  public SELF uint32() {
    return field().uint32();
  }

  public SELF uint32Array(int lengthPosition) {
    return field().lengthField(lengthPosition).uint32();
  }

  public SELF uint32Array(Expression expression) {
    return field().lengthExpression(expression).uint32();
  }

  public SELF int64() {
    return field().int64();
  }

  public SELF int64Array(int lengthPosition) {
    return field().lengthField(lengthPosition).int64();
  }

  public SELF int64Array(Expression expression) {
    return field().lengthExpression(expression).int64();
  }

  public SELF uint64() {
    return field().uint64();
  }
  public SELF uint64Array(int lengthPosition) {
    return field().lengthField(lengthPosition).uint64();
  }

  public SELF uint64Array(Expression expression) {
    return field().lengthExpression(expression).uint64();
  }

  public SELF float32() {
    return field().float32();
  }

  public SELF float32Array(int lengthPosition) {
    return field().lengthField(lengthPosition).float32();
  }

  public SELF float32Array(Expression expression) {
    return field().lengthExpression(expression).float32();
  }

  public SELF float64() {
    return field().float64();
  }

  public SELF float64Array(int lengthPosition) {
    return field().lengthField(lengthPosition).float64();
  }

  public SELF float64Array(Expression expression) {
    return field().lengthExpression(expression).float64();
  }

  public SELF pad() {
    return field().pad();
  }

  public SELF padArray(int lengthPosition) {
    return field().lengthField(lengthPosition).pad();
  }

  public SELF padArray(Expression expression) {
    return field().lengthExpression(expression).pad();
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
