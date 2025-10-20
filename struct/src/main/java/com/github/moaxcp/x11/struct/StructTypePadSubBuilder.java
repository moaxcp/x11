package com.github.moaxcp.x11.struct;

public class StructTypePadSubBuilder<PARENT extends StructTypeBuilder<PARENT>> {
  private final PARENT structTypeBuilder;
  private final StructTypePadBuilder structTypePadBuilder;

  StructTypePadSubBuilder(PARENT structTypeBuilder, int position) {
    this.structTypeBuilder = structTypeBuilder;
    structTypePadBuilder = new StructTypePadBuilder().position(position);
  }

  public StructTypePadSubBuilder<PARENT> length(long length) {
    structTypePadBuilder.length(length);
    return this;
  }

  public PARENT align() {
    return structTypeBuilder.type(structTypePadBuilder.align());
  }

  public PARENT pad() {
    return structTypeBuilder.type(structTypePadBuilder.pad());
  }
}
