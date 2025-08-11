package com.github.moaxcp.x11.struct;

import java.util.AbstractList;

public class LengthList<L extends Number, E> extends AbstractList<E> implements Pointer<LengthList<L, E>> {

  private int offset;
  private final LengthListType<L, E> listType;
  private final ByteArray byteArray;

  public LengthList(LengthList<L, E> lengthList) {
    this.offset = lengthList.offset;
    this.listType = lengthList.listType.copy();
    this.byteArray = lengthList.byteArray.copy();
  }

  public LengthList(int offset, LengthListType<L, E> listType, ByteArray byteArray) {
    this.offset = offset;
    this.listType = listType;
    this.byteArray = byteArray;
    byteArray.ensureSizeFor(offset, listType.getByteLength(this));
    byteArray.addListener(this);
  }

  @Override
  public LengthList<L, E> copy() {
    return new LengthList<>(this);
  }

  public int getOffset() {
    return offset;
  }

  @Override
  public int getByteLength() {
    return listType.getByteLength(this);
  }

  public LengthList<L, E> setOffset(int offset) {
    this.offset = offset;
    return this;
  }

  @Override
  public LengthListType<L, E> getType() {
    return listType;
  }

  @Override
  public ByteArray getByteArray() {
    return byteArray;
  }

  public int getLengthOffset() {
    return listType.getLengthType().getOffset(this);
  }

  public int getElementOffset(int index) {
    return listType.getElementType().getElementOffset(this, index);
  }

  public int getLengthByteLength() {
    return listType.getLengthType().getByteLength(this);
  }

  public int getElementByteLength(int index) {
    return listType.getElementType().getByteLength(this, index);
  }

  @Override
  public E get(int index) {
    return listType.getElementType().getElement(this, index);
  }

  @Override
  public E set(int index, E data) {
    var r = listType.getElementType().getElement(this, index);
    listType.getElementType().setElement(this, index, data);
    return r;
  }

  @Override
  public int size() {
    return (int) listType.getListLength(this);
  }

  @Override
  public boolean add(E data) {
    throw new UnsupportedOperationException("add is not supported for length list");
  }

  @Override
  public E remove(int index) {
    throw new UnsupportedOperationException("remove is not supported for length list");
  }

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof LengthList<?, ?> that)) return false;
    if (!super.equals(o)) return false;

    return offset == that.offset && listType.equals(that.listType) && getByteArray().compareBytes(getOffset(), that.getByteArray(), that.getOffset(), getByteLength());
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + offset;
    result = 31 * result + listType.hashCode();
    result = 31 * result + byteArray.hashCode();
    return result;
  }
}
