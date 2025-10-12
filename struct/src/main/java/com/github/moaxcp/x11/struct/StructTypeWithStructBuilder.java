package com.github.moaxcp.x11.struct;

public class StructTypeWithStructBuilder extends StructTypeBuilder<StructTypeWithStructBuilder> {
  private final StructBuilder structBuilder;

  StructTypeWithStructBuilder(StructBuilder structBuilder) {
    this.structBuilder = structBuilder;
  }

  public StructBuilder end() {
    return structBuilder;
  }

  public ChildStructTypeBuilder<StructTypeWithStructBuilder> struct() {
    return new ChildStructTypeBuilder<>(this, fields());
  }

  public ChildStructTypeBuilder<StructTypeWithStructBuilder> structArray(int lengthPosition) {
    return new ChildStructTypeBuilder<>(this, fields()).lengthField(lengthPosition);
  }
}
