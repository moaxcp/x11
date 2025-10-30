package com.github.moaxcp.x11.struct;

import org.jspecify.annotations.Nullable;

public class StructTypePadBuilder {
  private int position;
  @Nullable
  private ByteLengthListener byteLengthChange;
  private long length;

  public StructTypePadBuilder position(int position) {
    this.position = position;
    return this;
  }

  public StructTypePadBuilder byteLengthChange(ByteLengthListener byteLengthChange) {
    this.byteLengthChange = byteLengthChange;
    return this;
  }

  public StructTypePadBuilder length(long length) {
    this.length = length;
    return this;
  }

  public PadType pad() {
    return new PadType(position, length, false);
  }

  public PadType align() {
    return new PadType(position, length, true);
  }
}