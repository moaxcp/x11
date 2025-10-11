package com.github.moaxcp.x11.struct;

import static com.github.moaxcp.x11.struct.PrimitiveTypeBuilder.primitive;

public class PrimitiveTypeSubBuilder<PARENT extends StructTypeBuilder<PARENT>> {
  private final PARENT structTypeBuilder;
  private final PrimitiveTypeBuilder primitiveTypeBuilder;

  PrimitiveTypeSubBuilder(PARENT structTypeBuilder, int position) {
    this.structTypeBuilder = structTypeBuilder;
    primitiveTypeBuilder = primitive().position(position);
  }

  public PrimitiveTypeSubBuilder<PARENT> constant(Object constantValue) {
    primitiveTypeBuilder.constant(constantValue);
    return this;
  }

  public PrimitiveTypeSubBuilder<PARENT> lengthField(int lengthFieldPosition) {
    primitiveTypeBuilder.lengthExpression(Expression.valueOf(lengthFieldPosition));
    primitiveTypeBuilder.assignment(Assignment.add(lengthFieldPosition));
    return this;
  }

  public PrimitiveTypeSubBuilder<PARENT> lengthExpression(Expression lengthExpression) {
    primitiveTypeBuilder.lengthExpression(lengthExpression);
    return this;
  }

  public PrimitiveTypeSubBuilder<PARENT> assignment(Assignment assignment) {
    primitiveTypeBuilder.assignment(assignment);
    return this;
  }

  public PARENT bool() {
    return structTypeBuilder.field(primitiveTypeBuilder.bool());
  }

  public PARENT int8() {
    return structTypeBuilder.field(primitiveTypeBuilder.int8());
  }

  public PARENT uint8() {
    return structTypeBuilder.field(primitiveTypeBuilder.uint8());
  }

  public PARENT int16() {
    return structTypeBuilder.field(primitiveTypeBuilder.int16());
  }

  public PARENT uint16() {
    return structTypeBuilder.field(primitiveTypeBuilder.uint16());
  }

  public PARENT int32() {
    return structTypeBuilder.field(primitiveTypeBuilder.int32());
  }

  public PARENT uint32() {
    return structTypeBuilder.field(primitiveTypeBuilder.uint32());
  }

  public PARENT int64() {
    return structTypeBuilder.field(primitiveTypeBuilder.int64());
  }

  public PARENT uint64() {
    return structTypeBuilder.field(primitiveTypeBuilder.uint64());
  }

  public PARENT float32() {
    return structTypeBuilder.field(primitiveTypeBuilder.float32());
  }

  public PARENT float64() {
    return structTypeBuilder.field(primitiveTypeBuilder.float64());
  }
}
