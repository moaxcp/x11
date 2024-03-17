package com.github.moaxcp.x11.protocol.xv;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum AttributeFlag implements IntValue {
  GETTABLE(0b1),

  SETTABLE(0b10);

  static final Map<Integer, AttributeFlag> byCode = new HashMap<>();

  static {
        for(AttributeFlag e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  AttributeFlag(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static AttributeFlag getByCode(int code) {
    return byCode.get(code);
  }
}
