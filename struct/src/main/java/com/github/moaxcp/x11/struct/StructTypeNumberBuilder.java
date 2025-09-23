package com.github.moaxcp.x11.struct;

public class StructTypeNumberBuilder {
  private final StructTypeBuilder structTypeBuilder;
  private final NumberTypeBuilder numberTypeBuilder;

  StructTypeNumberBuilder(StructTypeBuilder structTypeBuilder, int position) {
    this.structTypeBuilder = structTypeBuilder;
    numberTypeBuilder = NumberTypeBuilder.number().position(position);
  }

  public StructTypeNumberBuilder constant(Number constantValue) {
    numberTypeBuilder.constant(constantValue);
    return this;
  }

  public StructTypeNumberBuilder lengthField(int lengthFieldPosition) {
    numberTypeBuilder.lengthExpression(Expression.valueOf(lengthFieldPosition));
    numberTypeBuilder.assignment(Assignment.add(lengthFieldPosition));
    return this;
  }

  public StructTypeNumberBuilder lengthExpression(Expression lengthExpression) {
    numberTypeBuilder.lengthExpression(lengthExpression);
    return this;
  }

  public StructTypeNumberBuilder assignment(Assignment assignment) {
    numberTypeBuilder.assignment(assignment);
    return this;
  }

  public StructTypeBuilder int8() {
    return structTypeBuilder.field(numberTypeBuilder.int8());
  }

  public StructTypeBuilder uint8() {
    return structTypeBuilder.field(numberTypeBuilder.uint8());
  }

  public StructTypeBuilder int16() {
    return structTypeBuilder.field(numberTypeBuilder.int16());
  }

  public StructTypeBuilder uint16() {
    return structTypeBuilder.field(numberTypeBuilder.uint16());
  }

  public StructTypeBuilder int32() {
    return structTypeBuilder.field(numberTypeBuilder.int32());
  }

  public StructTypeBuilder uint32() {
    return structTypeBuilder.field(numberTypeBuilder.uint32());
  }

  public StructTypeBuilder int64() {
    return structTypeBuilder.field(numberTypeBuilder.int64());
  }

  public StructTypeBuilder uint64() {
    return structTypeBuilder.field(numberTypeBuilder.uint64());
  }

  public StructTypeBuilder float32() {
    return structTypeBuilder.field(numberTypeBuilder.float32());
  }

  public StructTypeBuilder float64() {
    return structTypeBuilder.field(numberTypeBuilder.float64());
  }
}
