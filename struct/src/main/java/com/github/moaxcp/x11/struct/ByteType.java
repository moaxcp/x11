package com.github.moaxcp.x11.struct;

public class ByteType implements Type<Byte>, StructMember<Byte>, LengthType<Byte>, ElementType<Byte> {
  public static final int SIZE = 1;

  private int position;

  public ByteType() {
    this(-1);
  }

  public ByteType(int position) {
    this.position = position;
  }

  public ByteType(ByteType type) {
    this.position = type.position;
  }

  public ByteType copy() {
    return new ByteType(this);
  }

  @Override
  public int getPosition() {
    return position;
  }

  @Override
  public void setPosition(int position) {
    this.position = position;
  }

  @Override
  public int getByteLength(Struct struct) {
    return SIZE;
  }

  @Override
  public Byte get(Struct struct) {
    return struct.getByteArray().getByte(getOffset(struct));
  }

  @Override
  public void set(Struct struct, Byte data) {
    struct.getByteArray().setByte(getOffset(struct), data);
  }

  public byte getByte(Struct struct) {
    return struct.getByteArray().getByte(getOffset(struct));
  }

  public void setByte(Struct struct, byte b) {
    struct.getByteArray().setByte(getOffset(struct), b);
  }

  @Override
  public <T> Byte getLength(LengthList<Byte, T> list) {
    return list.getByteArray().getByte(list.getOffset());
  }

  @Override
  public <T> void setLength(LengthList<Byte, T> list, Byte data) {
    list.getByteArray().setByte(list.getOffset(), data);
  }

  @Override
  public <T> int getOffset(LengthList<Byte, T> list) {
    return list.getOffset();
  }

  @Override
  public <T> int getByteLength(LengthList<Byte, T> list) {
    return SIZE;
  }

  @Override
  public <L extends Number> int getElementOffset(LengthList<L, Byte> list, int index) {
    return list.getLengthOffset() + list.getLengthByteLength() + SIZE * index;
  }

  @Override
  public <L extends Number> Byte getElement(LengthList<L, Byte> list, int index) {
    return list.getByteArray().getByte(getElementOffset(list, index));
  }

  @Override
  public <L extends Number> void setElement(LengthList<L, Byte> list, int index, Byte data) {
    list.getByteArray().setByte(getElementOffset(list, index), data);
  }

  @Override
  public <L extends Number> int getByteLength(LengthList<L, Byte> list, int index) {
    return SIZE;
  }

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof ByteType byteType)) return false;

    return position == byteType.position;
  }

  @Override
  public int hashCode() {
    return position;
  }
}
