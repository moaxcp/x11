package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum BoolCtrlsHigh implements IntValue {
  ACCESS_X_FEEDBACK(0b1),

  AUDIBLE_BELL(0b10),

  OVERLAY1(0b100),

  OVERLAY2(0b1000),

  IGNORE_GROUP_LOCK(0b10000);

  static final Map<Integer, BoolCtrlsHigh> byCode = new HashMap<>();

  static {
        for(BoolCtrlsHigh e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  BoolCtrlsHigh(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static BoolCtrlsHigh getByCode(int code) {
    return byCode.get(code);
  }
}
