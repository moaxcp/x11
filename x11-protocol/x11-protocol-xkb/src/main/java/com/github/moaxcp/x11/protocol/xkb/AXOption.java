package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum AXOption implements IntValue {
  S_K_PRESS_F_B(0b1),

  S_K_ACCEPT_F_B(0b10),

  FEATURE_F_B(0b100),

  SLOW_WARN_F_B(0b1000),

  INDICATOR_F_B(0b10000),

  STICKY_KEYS_F_B(0b100000),

  TWO_KEYS(0b1000000),

  LATCH_TO_LOCK(0b10000000),

  S_K_RELEASE_F_B(0b100000000),

  S_K_REJECT_F_B(0b1000000000),

  B_K_REJECT_F_B(0b10000000000),

  DUMB_BELL(0b100000000000);

  static final Map<Integer, AXOption> byCode = new HashMap<>();

  static {
        for(AXOption e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  AXOption(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static AXOption getByCode(int code) {
    return byCode.get(code);
  }
}
