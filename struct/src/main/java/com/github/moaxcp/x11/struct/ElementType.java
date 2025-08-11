package com.github.moaxcp.x11.struct;

public interface ElementType<T> {

  ElementType<T> copy();

  <L extends Number> int getElementOffset(LengthList<L, T> list, int index);

  /**
   * Returns the value of the list element at the provided index in the provided list.
   * @param list
   * @param index
   * @return
   */
  <L extends Number> T getElement(LengthList<L, T> list, int index);

  /**
   * Sets the value of the list element at the provided index in the provided list.
   * @param list
   * @param index
   * @param data
   */
  <L extends Number> void setElement(LengthList<L, T> list, int index, T data);

  <L extends Number> int getByteLength(LengthList<L, T> list, int index);
}