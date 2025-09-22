package com.github.moaxcp.x11.struct;

public record ShiftBytes(long offset, long size) {
  public static ShiftBytes shiftBytes(long offset, long size) {
    return new ShiftBytes(offset, size);
  }
}
