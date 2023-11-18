package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum VModsLow implements IntValue {
  SEVEN(0b10000000),

  SIX(0b1000000),

  FIVE(0b100000),

  FOUR(0b10000),

  THREE(0b1000),

  TWO(0b100),

  ONE(0b10),

  ZERO(0b1);

  static final Map<Integer, VModsLow> byCode = new HashMap<>();

  static {
        for(VModsLow e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  VModsLow(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static VModsLow getByCode(int code) {
    return byCode.get(code);
  }
}
