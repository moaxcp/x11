package com.github.moaxcp.x11.struct;

public enum Size {
  BOOL(1),
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

  public String label() {
    return toString().toLowerCase();
  }

  public String title() {
    return toString().substring(0, 1).toUpperCase() + toString().substring(1).toLowerCase();
  }

  public String primitive() {
    return switch(this) {
      case BOOL -> "boolean";
      case INT8 -> "byte";
      case UINT8, INT16 -> "short";
      case UINT16, INT32 -> "int";
      case UINT32, INT64 -> "long";
      case UINT64 -> "BigInteger";
      case FLOAT32 -> "float";
      case FLOAT64 -> "double";
    };
  }
}
