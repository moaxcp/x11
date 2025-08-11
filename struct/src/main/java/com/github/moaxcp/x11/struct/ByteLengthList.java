package com.github.moaxcp.x11.struct;

public class ByteLengthList<L extends Number> extends LengthList<L, Byte> {

  public ByteLengthList(int offset, LengthListType<L, Byte> schema, ByteArray bytes) {
    super(offset, schema, bytes);
  }
}
