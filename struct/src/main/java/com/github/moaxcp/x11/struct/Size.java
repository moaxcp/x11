package com.github.moaxcp.x11.struct;

public enum Size {
  INT8(1),
  UINT8(1),
  INT16(2),
  UINT16(2),
  INT32(4),
  UINT32(4),
  INT64(8),
  UINT64(8),
  FLOAT32(4),
  FLOAT64(8);

  private final int size;

  Size(int size) {
    this.size = size;
  }

  public int size() {
    return size;
  }
}
