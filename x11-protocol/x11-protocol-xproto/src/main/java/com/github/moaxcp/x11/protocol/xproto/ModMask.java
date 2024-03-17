package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ModMask implements IntValue {
  SHIFT(0b1),

  LOCK(0b10),

  CONTROL(0b100),

  ONE(0b1000),

  TWO(0b10000),

  THREE(0b100000),

  FOUR(0b1000000),

  FIVE(0b10000000),

  ANY(0b1000000000000000);

  static final Map<Integer, ModMask> byCode = new HashMap<>();

  static {
        for(ModMask e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ModMask(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ModMask getByCode(int code) {
    return byCode.get(code);
  }
}
