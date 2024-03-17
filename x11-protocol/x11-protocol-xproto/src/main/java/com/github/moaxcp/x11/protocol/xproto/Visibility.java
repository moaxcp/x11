package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Visibility implements IntValue {
  UNOBSCURED(0),

  PARTIALLY_OBSCURED(1),

  FULLY_OBSCURED(2);

  static final Map<Integer, Visibility> byCode = new HashMap<>();

  static {
        for(Visibility e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Visibility(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Visibility getByCode(int code) {
    return byCode.get(code);
  }
}
