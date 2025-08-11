package com.github.moaxcp.x11.struct;

public class LengthListType<L extends Number, T> implements Type<LengthList<L, T>>, StructMember<LengthList<L, T>>, ElementType<LengthList<L, T>> {
  private int position;
  private final LengthType<L> lengthField;
  private final ElementType<T> elementField;

  public LengthListType(int position, LengthType<L> lengthField, ElementType<T> elementField) {
    this.position = position;
    this.lengthField = lengthField;
    this.elementField = elementField;
  }

  public LengthListType<L, T> copy() {
    return new LengthListType<>(position, lengthField.copy(), elementField.copy());
  }

  @Override
  public <N extends Number> int getByteLength(LengthList<N, LengthList<L, T>> list, int index) {
    return 0;
  }

  @Override
  public <N extends Number> void setElement(LengthList<N, LengthList<L, T>> list, int index, LengthList<L, T> data) {

  }

  @Override
  public <N extends Number> LengthList<L, T> getElement(LengthList<N, LengthList<L, T>> list, int index) {
    return null;
  }

  @Override
  public <N extends Number> int getElementOffset(LengthList<N, LengthList<L, T>> list, int index) {
    return 0;
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
    return 0;
  }

  @Override
  public LengthList<L, T> get(Struct struct) {
    return null;
  }

  @Override
  public void set(Struct struct, LengthList<L, T> data) {

  }

  /**
   * Returns the length field for this list. The length field contains or calculates the length of the list.
   * @return
   */
  LengthType<L> getLengthType() {
    return null;
  }

  /**
   * Returns the list element schema for the list. Each element in the list uses this schema.
   * @return
   */
  ElementType<T> getElementType() {
    return null;
  }

  public int getByteLength(LengthList<L, T> list) {
    return getLengthType().getByteLength(list);
  }

  /**
   * Returns the list length in the provided list.
   * @param list
   * @return
   */
  public L getListLength(LengthList<L, T> list) {
    return getLengthType().getLength(list);
  }

  public void add(LengthList<L, T> list, T data) {

  }
}
