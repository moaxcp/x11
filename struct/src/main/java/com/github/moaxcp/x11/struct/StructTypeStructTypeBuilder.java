package com.github.moaxcp.x11.struct;

import static com.github.moaxcp.x11.struct.Expression.valueOf;
import static com.github.moaxcp.x11.struct.StructTypeBuilder.structType;

public class StructTypeStructTypeBuilder {
  private final StructTypeBuilder parent;
  private final StructTypeBuilder child;

  StructTypeStructTypeBuilder(StructTypeBuilder structTypeBuilder, int position) {
    this.parent = structTypeBuilder;
    child = structType().position(position);
  }

  public StructTypeStructTypeBuilder constant(Struct constantValue) {
    child.constant(constantValue);
    return this;
  }

  public StructTypeStructTypeBuilder lengthField(int lengthFieldPosition) {
    child.lengthExpression(valueOf(lengthFieldPosition));
    child.assignment(Assignment.add(lengthFieldPosition));
    return this;
  }

  public StructTypeStructTypeBuilder lengthExpression(Expression lengthExpression) {
    child.lengthExpression(lengthExpression);
    return this;
  }

  public StructTypeStructTypeBuilder assignment(Assignment assignment) {
    child.assignment(assignment);
    return this;
  }

  public StructTypeBuilder int8() {
    return child.number().int8();
  }

  public StructTypeBuilder int8Array(int lengthPosition) {
    return child.number().lengthExpression(valueOf(lengthPosition)).int8();
  }

  public StructTypeBuilder uint8() {
    return child.number().uint8();
  }
  public StructTypeBuilder uint8Array(int lengthPosition) {
    return child.number().lengthExpression(valueOf(lengthPosition)).uint8();
  }

  public StructTypeBuilder int16() {
    return child.number().int16();
  }

  public StructTypeBuilder int16Array(int lengthPosition) {
    return child.number().lengthExpression(valueOf(lengthPosition)).int16();
  }

  public StructTypeBuilder uint16() {
    return child.number().uint16();
  }

  public StructTypeBuilder uint16Array(int lengthPosition) {
    return child.number().lengthExpression(valueOf(lengthPosition)).uint16();
  }

  public StructTypeBuilder int32() {
    return child.number().int32();
  }

  public StructTypeBuilder int32Array(int lengthPosition) {
    return child.number().lengthExpression(valueOf(lengthPosition)).int32();
  }
  public StructTypeBuilder uint32() {
    return child.number().uint32();
  }

  public StructTypeBuilder uint32Array(int lengthPosition) {
    return child.number().lengthExpression(valueOf(lengthPosition)).uint32();
  }

  public StructTypeBuilder int64() {
    return child.number().int64();
  }

  public StructTypeBuilder int64Array(int lengthPosition) {
    return child.number().lengthExpression(valueOf(lengthPosition)).int64();
  }

  public StructTypeBuilder uint64() {
    return child.number().uint64();
  }
  public StructTypeBuilder uint64Array(int lengthPosition) {
    return child.number().lengthExpression(valueOf(lengthPosition)).uint64();
  }

  public StructTypeBuilder float32() {
    return child.number().float32();
  }

  public StructTypeBuilder float32Array(int lengthPosition) {
    return child.number().lengthExpression(valueOf(lengthPosition)).float32();
  }

  public StructTypeBuilder float64() {
    return child.number().float64();
  }

  public StructTypeBuilder float64Array(int lengthPosition) {
    return child.number().lengthExpression(valueOf(lengthPosition)).float64();
  }

  public StructTypeStructTypeBuilder struct() {
    return new StructTypeStructTypeBuilder(child, child.fields());
  }

  public StructTypeStructTypeBuilder structArray(int lengthPosition) {
    return new StructTypeStructTypeBuilder(child, child.fields()).lengthExpression(valueOf(lengthPosition));
  }

  public StructTypeBuilder endStruct() {
    parent.field(child.build());
    return parent;
  }
}
