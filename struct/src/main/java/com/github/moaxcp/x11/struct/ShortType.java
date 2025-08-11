package com.github.moaxcp.x11.struct;

public class ShortType implements Type<Short>, StructMember<Short>, LengthType<Short>, ElementType<Short> {
  public static final int SIZE = 2;

  private int position;

  public ShortType() {
    this(-1);
  }

  public ShortType(int position) {
    this.position = position;
  }

  public ShortType(ShortType type) {
    this.position = type.position;
  }

  public ShortType copy() {
    return new ShortType(this);
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
  public Short get(Struct struct) {
    return struct.getByteArray().getShort(getOffset(struct));
  }

  @Override
  public void set(Struct struct, Short data) {
    struct.getByteArray().setShort(getOffset(struct), data);
  }

  public short getShort(Struct struct) {
    return struct.getByteArray().getShort(getOffset(struct));
  }

  public void setShort(Struct struct, short s) {
    struct.getByteArray().setShort(getOffset(struct), s);
  }

  @Override
  public <T> Short getLength(LengthList<Short, T> list) {
    return list.getByteArray().getShort(list.getOffset());
  }

  @Override
  public <T> void setLength(LengthList<Short, T> list, Short data) {
    list.getByteArray().setShort(list.getOffset(), data);
  }

  @Override
  public <T> int getOffset(LengthList<Short, T> list) {
    return list.getOffset();
  }

  @Override
  public <T> int getByteLength(LengthList<Short, T> list) {
    return SIZE;
  }

  @Override
  public <L extends Number> int getElementOffset(LengthList<L, Short> list, int index) {
    return list.getOffset() + list.getLengthOffset() + list.getLengthByteLength() + SIZE * index;
  }

  @Override
  public <L extends Number> Short getElement(LengthList<L, Short> list, int index) {
    return list.getByteArray().getShort(getElementOffset(list, index));
  }

  @Override
  public <L extends Number> void setElement(LengthList<L, Short> list, int index, Short data) {
    list.getByteArray().setShort(getElementOffset(list, index), data);
  }

  @Override
  public <L extends Number> int getByteLength(LengthList<L, Short> list, int index) {
    return SIZE;
  }

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof ShortType shortType)) return false;

    return position == shortType.position;
  }

  @Override
  public int hashCode() {
    return position;
  }
}
