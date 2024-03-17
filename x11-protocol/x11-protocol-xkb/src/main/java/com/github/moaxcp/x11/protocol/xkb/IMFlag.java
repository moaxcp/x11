package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum IMFlag implements IntValue {
  NO_EXPLICIT(0b10000000),

  NO_AUTOMATIC(0b1000000),

  L_E_D_DRIVES_K_B(0b100000);

  static final Map<Integer, IMFlag> byCode = new HashMap<>();

  static {
        for(IMFlag e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  IMFlag(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static IMFlag getByCode(int code) {
    return byCode.get(code);
  }
}
