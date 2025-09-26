package com.github.moaxcp.x11.struct;

import static com.github.moaxcp.x11.struct.NumberTypeBuilder.number;

public class NumberTypeSubBuilder<PARENT extends StructTypeBuilder<PARENT>> {
  private final PARENT structTypeBuilder;
  private final NumberTypeBuilder numberTypeBuilder;

  NumberTypeSubBuilder(PARENT structTypeBuilder, int position) {
    this.structTypeBuilder = structTypeBuilder;
    numberTypeBuilder = number().position(position);
  }

  public NumberTypeSubBuilder<PARENT> constant(Number constantValue) {
    numberTypeBuilder.constant(constantValue);
    return this;
  }

  public NumberTypeSubBuilder<PARENT> lengthField(int lengthFieldPosition) {
    numberTypeBuilder.lengthExpression(Expression.valueOf(lengthFieldPosition));
    numberTypeBuilder.assignment(Assignment.add(lengthFieldPosition));
    return this;
  }

  public NumberTypeSubBuilder<PARENT> lengthExpression(Expression lengthExpression) {
    numberTypeBuilder.lengthExpression(lengthExpression);
    return this;
  }

  public NumberTypeSubBuilder<PARENT> assignment(Assignment assignment) {
    numberTypeBuilder.assignment(assignment);
    return this;
  }

  public PARENT int8() {
    return structTypeBuilder.field(numberTypeBuilder.int8());
  }

  public PARENT uint8() {
    return structTypeBuilder.field(numberTypeBuilder.uint8());
  }

  public PARENT int16() {
    return structTypeBuilder.field(numberTypeBuilder.int16());
  }

  public PARENT uint16() {
    return structTypeBuilder.field(numberTypeBuilder.uint16());
  }

  public PARENT int32() {
    return structTypeBuilder.field(numberTypeBuilder.int32());
  }

  public PARENT uint32() {
    return structTypeBuilder.field(numberTypeBuilder.uint32());
  }

  public PARENT int64() {
    return structTypeBuilder.field(numberTypeBuilder.int64());
  }

  public PARENT uint64() {
    return structTypeBuilder.field(numberTypeBuilder.uint64());
  }

  public PARENT float32() {
    return structTypeBuilder.field(numberTypeBuilder.float32());
  }

  public PARENT float64() {
    return structTypeBuilder.field(numberTypeBuilder.float64());
  }
}
