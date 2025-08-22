package com.github.moaxcp.x11.struct;

public class Struct implements Pointer<Struct, StructType> {
  private boolean allocated = false;
  private long offset;
  private StructType structType;
  private ByteArray byteArray;

   public Struct(Struct struct) {
    this(struct.allocated, struct.offset, struct.structType.copy(-1), struct.byteArray.copy());
   }

  public Struct(StructType structType) {
    this(0, structType);
  }

  public Struct(StructType structType, ByteArray byteArray) {
    this(true, 0, structType, byteArray);
  }

  public Struct(int offset, StructType structType) {
    this(false, offset, structType, new ByteArray());
  }

  public Struct(long offset, StructType structType, ByteArray byteArray) {
    this(true, offset, structType, byteArray);
  }

  public Struct(boolean allocated, long offset, StructType structType, ByteArray byteArray) {
     this.allocated = allocated;
    this.offset = offset;
    this.structType = structType;
    this.byteArray = byteArray;
    if (!this.allocated) {
      structType.allocate(this);
    }
    byteArray.addListener(this);
  }

  @Override
  public Struct copy() {
    return new Struct(this);
  }

  @Override
  public long getOffset() {
    return offset;
  }

  @Override
  public Struct setOffset(long offset) {
    this.offset = offset;
    return this;
  }

  @Override
  public StructType getType() {
    return structType;
  }

  @Override
  public <V extends Type<?>> V getType(int position) {
    return structType.getType(position);
  }

  @Override
  public ByteArray getByteArray() {
    return byteArray;
  }

  @Override
  public void setByteArray(ByteArray byteArray) {
    byteArray.removeListener(this);
    this.byteArray = byteArray;
    byteArray.addListener(this);
  }

  public long getByteLength() {
    return structType.getByteLength(this);
  }

  public long getByte(int position) {
    return structType.getByte(this, position);
  }

  public long getByte(int position, long index) {
     return structType.getByte(this, position, index);
  }

  public Struct setByte(int position, long b) {
    structType.setByte(this, position, b);
    return this;
  }

  public Struct addByte(int position, long b) {
    ((NumberType) structType.getType(position)).add(this, b);
    return this;
  }

  public Struct removeByte(int position, long index) {
    structType.getType(position).remove(this, index);
    return this;
  }

  public long getShort(int position) {
    return structType.getShort(this, position);
  }

  public long getShort(int position, long index) {
    return structType.getShort(this, position, index);
  }

  public Struct setShort(int position, long s) {
    structType.setShort(this, position, s);
    return this;
  }

  public Struct addShort(int position, long index) {
    ((NumberType) structType.getType(position)).add(this, index);
    return this;
  }

  public Struct removeShort(int position, long index) {
    structType.getType(position).remove(this, index);
    return this;
  }

  public Struct getStruct(int position) {
    return structType.getStruct(this, position);
  }

  public Struct setStruct(int position, Struct other) {
    structType.setStruct(this, position, other);
    return this;
  }

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof Struct struct)) return false;

    return offset == struct.offset && structType.equals(struct.structType) && getByteArray().compareBytes(getOffset(), struct.getByteArray(), struct.getOffset(), getByteLength());
  }

  @Override
  public int hashCode() {
    int result = Math.toIntExact(offset);
    result = 31 * result + structType.hashCode();
    for (int i = 0; i < getByteLength(); i++) {
      result = Math.toIntExact(31 * result + byteArray.getByte(getOffset() + i));
    }
    return result;
  }
}
