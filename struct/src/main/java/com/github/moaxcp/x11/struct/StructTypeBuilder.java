package com.github.moaxcp.x11.struct;

import java.util.ArrayList;
import java.util.List;

public abstract class StructTypeBuilder<SELF extends StructTypeBuilder<SELF>>{

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

  public NumberTypeSubBuilder number() {
    return new NumberTypeSubBuilder(this, fields.size());
  }

  SELF field(Type<?> type) {
    fields.add(type);
    return (SELF) this;
  }

  public SELF int8() {
    return (SELF) number().int8();
  }

  public SELF int8Array(int lengthPosition) {
    return (SELF) number().lengthField(lengthPosition).int8();
  }

  public SELF uint8() {
    return (SELF) number().uint8();
  }
  public SELF uint8Array(int lengthPosition) {
    return (SELF) number().lengthField(lengthPosition).uint8();
  }

  public SELF int16() {
    return (SELF) number().int16();
  }

  public SELF int16Array(int lengthPosition) {
    return (SELF) number().lengthField(lengthPosition).int16();
  }

  public SELF uint16() {
    return (SELF) number().uint16();
  }

  public SELF uint16Array(int lengthPosition) {
    return (SELF) number().lengthField(lengthPosition).uint16();
  }

  public SELF int32() {
    return (SELF) number().int32();
  }

  public SELF int32Array(int lengthPosition) {
    return (SELF) number().lengthField(lengthPosition).int32();
  }
  public SELF uint32() {
    return (SELF) number().uint32();
  }

  public SELF uint32Array(int lengthPosition) {
    return (SELF) number().lengthField(lengthPosition).uint32();
  }

  public SELF int64() {
    return (SELF) number().int64();
  }

  public SELF int64Array(int lengthPosition) {
    return (SELF) number().lengthField(lengthPosition).int64();
  }

  public SELF uint64() {
    return (SELF) number().uint64();
  }
  public SELF uint64Array(int lengthPosition) {
    return (SELF) number().lengthField(lengthPosition).uint64();
  }

  public SELF float32() {
    return (SELF) number().float32();
  }

  public SELF float32Array(int lengthPosition) {
    return (SELF) number().lengthField(lengthPosition).float32();
  }

  public SELF float64() {
    return (SELF) number().float64();
  }

  public SELF float64Array(int lengthPosition) {
    return (SELF) number().lengthField(lengthPosition).float64();
  }

  public ChildStructTypeBuilder<SELF> struct() {
    return new ChildStructTypeBuilder(this, fields.size());
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
