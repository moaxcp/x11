package com.github.moaxcp.x11.struct;

public class StructWithStructTypeBuilder extends StructTypeBuilder<StructWithStructTypeBuilder> {
  private final StructBuilder structBuilder;

  StructWithStructTypeBuilder(StructBuilder structBuilder) {
    this.structBuilder = structBuilder;
  }

  public StructBuilder end() {
    return structBuilder;
  }

  public ChildStructTypeBuilder<StructWithStructTypeBuilder> struct() {
    return new ChildStructTypeBuilder<>(this, fields());
  }

  public ChildStructTypeBuilder<StructWithStructTypeBuilder> structArray(int lengthPosition) {
    return new ChildStructTypeBuilder<>(this, fields()).lengthField(lengthPosition);
  }
}
