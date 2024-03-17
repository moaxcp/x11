package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum BellClass implements IntValue {
  KBD_FEEDBACK_CLASS(0),

  BELL_FEEDBACK_CLASS(5),

  DFLT_X_I_CLASS(768);

  static final Map<Integer, BellClass> byCode = new HashMap<>();

  static {
        for(BellClass e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  BellClass(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static BellClass getByCode(int code) {
    return byCode.get(code);
  }
}
