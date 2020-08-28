package com.github.moaxcp.x11client.protocol;

import java.lang.Integer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Popcount {
  public int popcount(int value) {
    return Integer.bitCount(value);
  }
}
