package com.github.moaxcp.x11.struct;

public class StructTypePrimitiveSubBuilder<PARENT extends StructTypeBuilder<PARENT>> {
  private final PARENT structTypeBuilder;
  private final StructTypePrimitiveBuilder structTypePrimitiveBuilder;

  StructTypePrimitiveSubBuilder(PARENT structTypeBuilder, int position) {
    this.structTypeBuilder = structTypeBuilder;
    structTypePrimitiveBuilder = new StructTypePrimitiveBuilder().position(position);
  }

  public StructTypePrimitiveSubBuilder<PARENT> constant(Object constantValue) {
    structTypePrimitiveBuilder.constant(constantValue);
    return this;
  }

  public StructTypePrimitiveSubBuilder<PARENT> lengthField(int lengthFieldPosition) {
    structTypePrimitiveBuilder.lengthExpression(Expression.valueOf(lengthFieldPosition));
    structTypePrimitiveBuilder.assignment(Assignment.add(lengthFieldPosition));
    return this;
  }

  public StructTypePrimitiveSubBuilder<PARENT> lengthExpression(Expression lengthExpression) {
    structTypePrimitiveBuilder.lengthExpression(lengthExpression);
    return this;
  }

  public StructTypePrimitiveSubBuilder<PARENT> assignment(Assignment assignment) {
    structTypePrimitiveBuilder.assignment(assignment);
    return this;
  }

  public PARENT bool() {
    return structTypeBuilder.primitive(structTypePrimitiveBuilder.bool());
  }

  public PARENT int8() {
    return structTypeBuilder.primitive(structTypePrimitiveBuilder.int8());
  }

  public PARENT uint8() {
    return structTypeBuilder.primitive(structTypePrimitiveBuilder.uint8());
  }

  public PARENT int16() {
    return structTypeBuilder.primitive(structTypePrimitiveBuilder.int16());
  }

  public PARENT uint16() {
    return structTypeBuilder.primitive(structTypePrimitiveBuilder.uint16());
  }

  public PARENT int32() {
    return structTypeBuilder.primitive(structTypePrimitiveBuilder.int32());
  }

  public PARENT uint32() {
    return structTypeBuilder.primitive(structTypePrimitiveBuilder.uint32());
  }

  public PARENT int64() {
    return structTypeBuilder.primitive(structTypePrimitiveBuilder.int64());
  }

  public PARENT uint64() {
    return structTypeBuilder.primitive(structTypePrimitiveBuilder.uint64());
  }

  public PARENT float32() {
    return structTypeBuilder.primitive(structTypePrimitiveBuilder.float32());
  }

  public PARENT float64() {
    return structTypeBuilder.primitive(structTypePrimitiveBuilder.float64());
  }
}
