package com.github.moaxcp.x11.struct;

public class StructBuilder {
  private final StructType structType;
  private Struct struct;

  public static StructBuilder struct() {
    return new StructBuilder();
  }

  public StructBuilder() {
    structType = new StructType();
    struct = new Struct(structType);
  }

  public StructBuilder withType(StructMember<?> type) {
    structType.withType(type);
    struct.setType(structType);
    return this;
  }

  public StructBuilder withByte() {
    return withByte(new ByteType());
  }

  public StructBuilder withByte(ByteType type) {
    withType(type);
    return this;
  }

  public StructBuilder withByte(int b) {
    var type = new ByteType();
    withType(type);
    struct.setByte(type.getPosition(), (byte) b);
    return this;
  }
  
  public StructBuilder withShort() {
    return withShort(new ShortType());
  }

  public StructBuilder withShort(ShortType type) {
    withType(type);
    return this;
  }

  public StructBuilder withShort(int s) {
    var type = new ShortType();
    withType(type);
    struct.setShort(type.getPosition(), (byte) s);
    return this;
  }

  public StructBuilder withStruct(StructType type) {
    withType(type);
    return this;
  }

  public StructBuilder withStruct(Struct struct) {
    var type = struct.getType();
    withType(type);
    struct.setStruct(type.getPosition(), struct);
    return this;
  }

  public StructBuilder withList(LengthListType<?, ?> type) {
    withType(type);
    return this;
  }

  public StructBuilder withList(LengthList<?, ?> list) {
    var type = list.getType();
    withType(type);
    struct.setList(type.getPosition(), list);
    return this;
  }

  public Struct build() {
    return struct;
  }
}
