package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum PerClientFlag implements IntValue {
  DETECTABLE_AUTO_REPEAT(0b1),

  GRABS_USE_X_K_B_STATE(0b10),

  AUTO_RESET_CONTROLS(0b100),

  LOOKUP_STATE_WHEN_GRABBED(0b1000),

  SEND_EVENT_USES_X_K_B_STATE(0b10000);

  static final Map<Integer, PerClientFlag> byCode = new HashMap<>();

  static {
        for(PerClientFlag e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  PerClientFlag(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static PerClientFlag getByCode(int code) {
    return byCode.get(code);
  }
}
