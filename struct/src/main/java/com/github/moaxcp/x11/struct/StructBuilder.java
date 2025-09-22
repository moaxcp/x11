package com.github.moaxcp.x11.struct;

public class StructBuilder {

  public static StructBuilder struct() {
    return new StructBuilder();
  }

  private boolean allocated = false;
  private long offset = 0;
  private int position = -1;
  private StructType structType;
  private ByteArray memory = new ByteArray();

  public StructBuilder allocated() {
    allocated = true;
    return this;
  }

  public StructBuilder offset(long offset) {
    this.offset = offset;
    return this;
  }

  public StructBuilder position(int position) {
    this.position = position;
    return this;
  }

  public StructBuilder structType(StructType structType) {
    this.structType = structType;
    return this;
  }

  public StructBuilder fromBytes(ByteArray memory) {
    this.memory = memory;
    this.allocated = true;
    return this;
  }

  public StructBuilder fromBytes(byte[] bytes) {
    this.memory = new ByteArray(bytes);
    this.allocated = true;
    return this;
  }

  public Struct build() {
    return new Struct(allocated, offset, structType, memory);
  }
}
