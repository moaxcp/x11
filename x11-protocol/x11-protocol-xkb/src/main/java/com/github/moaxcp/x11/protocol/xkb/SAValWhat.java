package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum SAValWhat implements IntValue {
  IGNORE_VAL(0),

  SET_VAL_MIN(1),

  SET_VAL_CENTER(2),

  SET_VAL_MAX(3),

  SET_VAL_RELATIVE(4),

  SET_VAL_ABSOLUTE(5);

  static final Map<Integer, SAValWhat> byCode = new HashMap<>();

  static {
        for(SAValWhat e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  SAValWhat(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static SAValWhat getByCode(int code) {
    return byCode.get(code);
  }
}
