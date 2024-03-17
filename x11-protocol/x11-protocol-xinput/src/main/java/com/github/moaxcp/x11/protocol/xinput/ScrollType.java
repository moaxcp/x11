package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ScrollType implements IntValue {
  VERTICAL(1),

  HORIZONTAL(2);

  static final Map<Integer, ScrollType> byCode = new HashMap<>();

  static {
        for(ScrollType e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ScrollType(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ScrollType getByCode(int code) {
    return byCode.get(code);
  }
}
