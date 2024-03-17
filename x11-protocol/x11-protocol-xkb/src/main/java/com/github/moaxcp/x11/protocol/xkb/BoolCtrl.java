package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum BoolCtrl implements IntValue {
  REPEAT_KEYS(0b1),

  SLOW_KEYS(0b10),

  BOUNCE_KEYS(0b100),

  STICKY_KEYS(0b1000),

  MOUSE_KEYS(0b10000),

  MOUSE_KEYS_ACCEL(0b100000),

  ACCESS_X_KEYS(0b1000000),

  ACCESS_X_TIMEOUT_MASK(0b10000000),

  ACCESS_X_FEEDBACK_MASK(0b100000000),

  AUDIBLE_BELL_MASK(0b1000000000),

  OVERLAY1_MASK(0b10000000000),

  OVERLAY2_MASK(0b100000000000),

  IGNORE_GROUP_LOCK_MASK(0b1000000000000);

  static final Map<Integer, BoolCtrl> byCode = new HashMap<>();

  static {
        for(BoolCtrl e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  BoolCtrl(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static BoolCtrl getByCode(int code) {
    return byCode.get(code);
  }
}
