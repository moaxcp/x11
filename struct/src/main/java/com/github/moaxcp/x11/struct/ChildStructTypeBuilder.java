package com.github.moaxcp.x11.struct;

public class ChildStructTypeBuilder<PARENT extends StructTypeBuilder<?>> extends StructTypeBuilder<ChildStructTypeBuilder<PARENT>> {
  private final PARENT parent;

  ChildStructTypeBuilder(PARENT structTypeBuilder, int position) {
    this.parent = structTypeBuilder;
    this.position(position);
  }

  public PARENT end() {
    parent.type(toStructType());
    return parent;
  }
}
