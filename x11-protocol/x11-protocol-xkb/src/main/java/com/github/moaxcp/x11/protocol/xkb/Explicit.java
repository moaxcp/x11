package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Explicit implements IntValue {
  V_MOD_MAP(0b10000000),

  BEHAVIOR(0b1000000),

  AUTO_REPEAT(0b100000),

  INTERPRET(0b10000),

  KEY_TYPE4(0b1000),

  KEY_TYPE3(0b100),

  KEY_TYPE2(0b10),

  KEY_TYPE1(0b1);

  static final Map<Integer, Explicit> byCode = new HashMap<>();

  static {
        for(Explicit e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Explicit(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Explicit getByCode(int code) {
    return byCode.get(code);
  }
}
