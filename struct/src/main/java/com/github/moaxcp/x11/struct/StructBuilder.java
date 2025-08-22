package com.github.moaxcp.x11.struct;

import java.util.ArrayList;
import java.util.List;

public class StructBuilder {

  public static StructBuilder struct() {
    return new StructBuilder();
  }

  private boolean allocated = false;
  private long offset = 0;
  private ByteArray byteArray = new ByteArray();
  private int position = -1;
  private final List<Type<?>> fields = new ArrayList<>();

  public int position() {
    return position;
  }

  public List<Type<?>> fields() {
    return fields;
  }

  public StructBuilder allocated() {
    allocated = true;
    return this;
  }

  public StructBuilder offset(long offset) {
    this.offset = offset;
    return this;
  }

  public StructBuilder fromBytes(ByteArray byteArray) {
    this.byteArray = byteArray;
    this.allocated = true;
    return this;
  }

  public StructBuilder fromBytes(byte[] bytes) {
    this.byteArray = new ByteArray(bytes);
    this.allocated = true;
    return this;
  }

  public StructBuilder position(int position) {
    this.position = position;
    return this;
  }

  public StructBuilder byteType() {
    fields.add(NumberType.byteType(fields.size()));
    return this;
  }

  public StructBuilder byteArray(int lengthPosition) {
    fields.add(NumberType.byteArray(fields.size(), lengthPosition));
    return this;
  }

  public StructBuilder shortType() {
    fields.add(NumberType.shortType(fields.size()));
    return this;
  }

  public StructBuilder shortArray(int lengthPosition) {
    fields.add(NumberType.shortArray(fields.size(), lengthPosition));
    return this;
  }

  public StructBuilder structType(StructType structType) {
    fields.add(structType.copy(fields.size()));
    return this;
  }

  public StructBuilder structArray(int length, StructType structType) {
    return null;
  }

  public Struct build() {
    return new Struct(allocated, offset, new StructType(position, fields), byteArray);
  }
}
