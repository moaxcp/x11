package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum BoolCtrlsLow implements IntValue {
  REPEAT_KEYS(0b1),

  SLOW_KEYS(0b10),

  BOUNCE_KEYS(0b100),

  STICKY_KEYS(0b1000),

  MOUSE_KEYS(0b10000),

  MOUSE_KEYS_ACCEL(0b100000),

  ACCESS_X_KEYS(0b1000000),

  ACCESS_X_TIMEOUT(0b10000000);

  static final Map<Integer, BoolCtrlsLow> byCode = new HashMap<>();

  static {
        for(BoolCtrlsLow e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  BoolCtrlsLow(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static BoolCtrlsLow getByCode(int code) {
    return byCode.get(code);
  }
}
