package com.github.moaxcp.x11.struct;

import static com.github.moaxcp.x11.struct.StructTypeFieldBuilder.field;

public class StructTypeFieldSubBuilder<PARENT extends StructTypeBuilder<PARENT>> {
  private final PARENT structTypeBuilder;
  private final StructTypeFieldBuilder structTypeFieldBuilder;

  StructTypeFieldSubBuilder(PARENT structTypeBuilder, int position) {
    this.structTypeBuilder = structTypeBuilder;
    structTypeFieldBuilder = field().position(position);
  }

  public StructTypeFieldSubBuilder<PARENT> constant(Object constantValue) {
    structTypeFieldBuilder.constant(constantValue);
    return this;
  }

  public StructTypeFieldSubBuilder<PARENT> lengthField(int lengthFieldPosition) {
    structTypeFieldBuilder.lengthExpression(Expression.valueOf(lengthFieldPosition));
    structTypeFieldBuilder.assignment(Assignment.add(lengthFieldPosition));
    return this;
  }

  public StructTypeFieldSubBuilder<PARENT> lengthExpression(Expression lengthExpression) {
    structTypeFieldBuilder.lengthExpression(lengthExpression);
    return this;
  }

  public StructTypeFieldSubBuilder<PARENT> assignment(Assignment assignment) {
    structTypeFieldBuilder.assignment(assignment);
    return this;
  }

  public PARENT bool() {
    return structTypeBuilder.field(structTypeFieldBuilder.bool());
  }

  public PARENT int8() {
    return structTypeBuilder.field(structTypeFieldBuilder.int8());
  }

  public PARENT uint8() {
    return structTypeBuilder.field(structTypeFieldBuilder.uint8());
  }

  public PARENT int16() {
    return structTypeBuilder.field(structTypeFieldBuilder.int16());
  }

  public PARENT uint16() {
    return structTypeBuilder.field(structTypeFieldBuilder.uint16());
  }

  public PARENT int32() {
    return structTypeBuilder.field(structTypeFieldBuilder.int32());
  }

  public PARENT uint32() {
    return structTypeBuilder.field(structTypeFieldBuilder.uint32());
  }

  public PARENT int64() {
    return structTypeBuilder.field(structTypeFieldBuilder.int64());
  }

  public PARENT uint64() {
    return structTypeBuilder.field(structTypeFieldBuilder.uint64());
  }

  public PARENT float32() {
    return structTypeBuilder.field(structTypeFieldBuilder.float32());
  }

  public PARENT float64() {
    return structTypeBuilder.field(structTypeFieldBuilder.float64());
  }

  public PARENT pad() {
    return structTypeBuilder.field(structTypeFieldBuilder.pad());
  }
}
