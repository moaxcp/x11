package com.github.moaxcp.x11.struct;

public class Struct implements Pointer<Struct> {
  private int offset;
  private StructType structType;
  private final ByteArray byteArray;

  public Struct() {
     this(0);
   }

   public Struct(Struct struct) {
    this(struct.offset, struct.structType.copy(), struct.byteArray.copy());
   }

  public Struct(StructType structType) {
    this(0, structType);
  }

  public Struct(StructType structType, ByteArray byteArray) {
    this(0, structType, byteArray);
  }

  public Struct(int offset) {
    this(offset, new StructType());
  }

  public Struct(int offset, StructType structType) {
    this(offset, structType, new ByteArray());
  }

  public Struct(int offset, StructType structType, ByteArray byteArray) {
    this.offset = offset;
    this.structType = structType;
    this.byteArray = byteArray;
    byteArray.ensureSizeFor(offset, structType.getByteLength(this));
    byteArray.addListener(this);
  }

  @Override
  public Struct copy() {
    return new Struct(this);
  }

  public int getOffset() {
    return offset;
  }

  public Struct setOffset(int offset) {
    this.offset = offset;
    return this;
  }

  public StructType getType() {
    return structType;
  }

  public void setType(StructType structType) {
    this.structType = structType;
  }

  public ByteArray getByteArray() {
    return byteArray;
  }

  public int getByteLength() {
    return structType.getByteLength(this);
  }

  public byte getByte(int position) {
    return structType.getByte(this, position);
  }

  public Struct setByte(int position, byte b) {
    structType.setByte(this, position, b);
    return this;
  }

  public short getShort(int position) {
    return structType.getShort(this, position);
  }

  public Struct setShort(int position, short s) {
    structType.setShort(this, position, s);
    return this;
  }

  public Struct getStruct(int position) {
    return structType.getStruct(this, position);
  }

  public Struct setStruct(int position, Struct other) {
    structType.setStruct(this, position, other);
    return this;
  }

  public void setList(int position, LengthList<?, ?> list) {
    structType.setList(this, position, list);
  }

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof Struct struct)) return false;

    return offset == struct.offset && structType.equals(struct.structType) && getByteArray().compareBytes(getOffset(), struct.getByteArray(), struct.getOffset(), getByteLength());
  }

  @Override
  public int hashCode() {
    int result = offset;
    result = 31 * result + structType.hashCode();
    for (int i = 0; i < getByteLength(); i++) {
      result = 31 * result + byteArray.getByte(getOffset() + i);
    }
    return result;
  }
}
