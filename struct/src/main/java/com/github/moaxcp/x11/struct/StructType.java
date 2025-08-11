package com.github.moaxcp.x11.struct;

import java.util.ArrayList;
import java.util.List;

public class StructType implements Type<Struct>, StructMember<Struct>, ElementType<Struct> {
  private int position;
  private final List<StructMember<?>> schemas;
  
  public StructType() {
    this(-1);
  }

  public StructType(int position) {
    this.position = position;
    schemas = new ArrayList<>();
  }

  public StructType(StructType schema) {
    this(schema.getPosition());
    for(StructMember<?> type : schema.schemas) {
      withType(type.copy());
    }
  }

  public StructType copy() {
    return new StructType(this);
  }

  @Override
  public int getPosition() {
    return position;
  }

  @Override
  public void setPosition(int position) {
    this.position = position;
  }

  public <T> StructMember<T> getType(int position) {
    return (StructMember<T>) schemas.get(position);
  }

  @Override
  public int getByteLength(Struct struct) {
    var size = 0;
    for(StructMember<?> type : schemas) {
      size += type.getByteLength(struct);
    }
    return size;
  }

  @Override
  public void set(Struct struct, Struct data) {
    struct.getByteArray().setBytes(data.getByteArray(), data.getOffset(), getOffset(struct), data.getByteLength());
  }

  @Override
  public Struct get(Struct struct) {
    return new Struct(getOffset(struct), this, struct.getByteArray());
  }

  public byte getByte(Struct struct, int position) {
    return ((ByteType) schemas.get(position)).getByte(struct);
  }

  public void setByte(Struct struct, int position, byte b) {
    ((ByteType) schemas.get(position)).setByte(struct, b);
  }

  public short getShort(Struct struct, int position) {
    return ((ShortType) schemas.get(position)).getShort(struct);
  }

  public void setShort(Struct struct, int position, short s) {
    ((ShortType) schemas.get(position)).setShort(struct, s);
  }

  public Struct getStruct(Struct struct, int position) {
    return ((StructType) schemas.get(position)).get(struct);
  }

  public void setStruct(Struct struct, int position, Struct other) {
    ((StructType) schemas.get(position)).set(struct, other);
  }

  public StructType withType(StructMember<?> type) {
    if (type.getPosition() >= 0) {
      schemas.set(type.getPosition(), type);
    } else {
      type.setPosition(schemas.size());
      schemas.add(type);
    }
    return this;
  }

  public <L extends Number, E> void setList(Struct struct, int position, LengthList<L, E> list) {
    ((LengthListType<L, E>) schemas.get(position)).set(struct, list);
  }

  @Override
  public <L extends Number> int getElementOffset(LengthList<L, Struct> list, int index) {
    var offset = list.getOffset() + list.getType().getLengthType().getLength(list).intValue();
    for(int i = 0; i < index; i++) {
      offset += list.getElementByteLength(i);
    }
    return offset;
  }

  @Override
  public <L extends Number> Struct getElement(LengthList<L, Struct> list, int index) {
    return null;
  }

  @Override
  public <L extends Number> void setElement(LengthList<L, Struct> list, int index, Struct data) {

  }

  @Override
  public <L extends Number> int getByteLength(LengthList<L, Struct> list, int index) {
    var length = 0;
    for ( int i = 0; i < index; i++ ) {
      var offset = getElementOffset(list, i);
        var struct = new Struct(offset, this);
        length += struct.getByteLength();
    }
    return length;
  }
}
