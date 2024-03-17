package com.github.moaxcp.x11.protocol.xprint;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum EvMask implements IntValue {
  NO_EVENT_MASK(0),

  PRINT_MASK(0b1),

  ATTRIBUTE_MASK(0b10);

  static final Map<Integer, EvMask> byCode = new HashMap<>();

  static {
        for(EvMask e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  EvMask(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static EvMask getByCode(int code) {
    return byCode.get(code);
  }
}
