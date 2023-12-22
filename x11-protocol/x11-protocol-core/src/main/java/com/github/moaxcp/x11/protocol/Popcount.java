package com.github.moaxcp.x11.protocol;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Popcount {
  public int popcount(int value) {
    return Integer.bitCount(value);
  }
  public int popcount(long value) {
    return Long.bitCount(value);
  }
}
