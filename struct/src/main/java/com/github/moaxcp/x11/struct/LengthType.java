package com.github.moaxcp.x11.struct;

public interface LengthType<L extends Number> {

  LengthType<L> copy();

  <T> L getLength(LengthList<L, T> list);

  <T> void setLength(LengthList<L, T> list, L data);

  <T> int getOffset(LengthList<L, T> list);

  <T> int getByteLength(LengthList<L, T> list);
}
