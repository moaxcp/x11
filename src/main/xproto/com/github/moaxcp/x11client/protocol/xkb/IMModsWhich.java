package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum IMModsWhich implements IntValue {
  USE_COMPAT(0b10000),

  USE_EFFECTIVE(0b1000),

  USE_LOCKED(0b100),

  USE_LATCHED(0b10),

  USE_BASE(0b1);

  static final Map<Integer, IMModsWhich> byCode = new HashMap<>();

  static {
        for(IMModsWhich e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  IMModsWhich(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static IMModsWhich getByCode(int code) {
    return byCode.get(code);
  }
}
