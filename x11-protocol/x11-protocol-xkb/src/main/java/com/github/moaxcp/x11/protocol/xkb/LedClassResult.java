package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum LedClassResult implements IntValue {
  KBD_FEEDBACK_CLASS(0),

  LED_FEEDBACK_CLASS(4);

  static final Map<Integer, LedClassResult> byCode = new HashMap<>();

  static {
        for(LedClassResult e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  LedClassResult(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static LedClassResult getByCode(int code) {
    return byCode.get(code);
  }
}
