package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum LedClass implements IntValue {
  KBD_FEEDBACK_CLASS(0),

  LED_FEEDBACK_CLASS(4),

  DFLT_X_I_CLASS(768),

  ALL_X_I_CLASSES(1280);

  static final Map<Integer, LedClass> byCode = new HashMap<>();

  static {
        for(LedClass e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  LedClass(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static LedClass getByCode(int code) {
    return byCode.get(code);
  }
}
