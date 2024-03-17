package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum VModsHigh implements IntValue {
  FIFTEEN(0b10000000),

  FOURTEEN(0b1000000),

  THIRTEEN(0b100000),

  TWELVE(0b10000),

  ELEVEN(0b1000),

  TEN(0b100),

  NINE(0b10),

  EIGHT(0b1);

  static final Map<Integer, VModsHigh> byCode = new HashMap<>();

  static {
        for(VModsHigh e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  VModsHigh(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static VModsHigh getByCode(int code) {
    return byCode.get(code);
  }
}
