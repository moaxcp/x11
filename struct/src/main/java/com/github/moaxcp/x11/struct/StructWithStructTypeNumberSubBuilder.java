package com.github.moaxcp.x11.struct;

import static com.github.moaxcp.x11.struct.NumberTypeBuilder.number;

public class StructWithStructTypeNumberSubBuilder {
  private final StructWithStructTypeBuilder structTypeBuilder;
  private final NumberTypeBuilder numberTypeBuilder;

  StructWithStructTypeNumberSubBuilder(StructWithStructTypeBuilder structTypeBuilder, int position) {
    this.structTypeBuilder = structTypeBuilder;
    numberTypeBuilder = number().position(position);
  }

  public StructWithStructTypeNumberSubBuilder constant(Number constantValue) {
    numberTypeBuilder.constant(constantValue);
    return this;
  }

  public StructWithStructTypeNumberSubBuilder lengthField(int lengthFieldPosition) {
    numberTypeBuilder.lengthExpression(Expression.valueOf(lengthFieldPosition));
    numberTypeBuilder.assignment(Assignment.add(lengthFieldPosition));
    return this;
  }

  public StructWithStructTypeNumberSubBuilder lengthExpression(Expression lengthExpression) {
    numberTypeBuilder.lengthExpression(lengthExpression);
    return this;
  }

  public StructWithStructTypeNumberSubBuilder assignment(Assignment assignment) {
    numberTypeBuilder.assignment(assignment);
    return this;
  }

  public StructWithStructTypeBuilder int8() {
    return structTypeBuilder.field(numberTypeBuilder.int8());
  }

  public StructWithStructTypeBuilder uint8() {
    return structTypeBuilder.field(numberTypeBuilder.uint8());
  }

  public StructWithStructTypeBuilder int16() {
    return structTypeBuilder.field(numberTypeBuilder.int16());
  }

  public StructWithStructTypeBuilder uint16() {
    return structTypeBuilder.field(numberTypeBuilder.uint16());
  }

  public StructWithStructTypeBuilder int32() {
    return structTypeBuilder.field(numberTypeBuilder.int32());
  }

  public StructWithStructTypeBuilder uint32() {
    return structTypeBuilder.field(numberTypeBuilder.uint32());
  }

  public StructWithStructTypeBuilder int64() {
    return structTypeBuilder.field(numberTypeBuilder.int64());
  }

  public StructWithStructTypeBuilder uint64() {
    return structTypeBuilder.field(numberTypeBuilder.uint64());
  }

  public StructWithStructTypeBuilder float32() {
    return structTypeBuilder.field(numberTypeBuilder.float32());
  }

  public StructWithStructTypeBuilder float64() {
    return structTypeBuilder.field(numberTypeBuilder.float64());
  }
}
