package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum StackMode implements IntValue {
  ABOVE(0),

  BELOW(1),

  TOP_IF(2),

  BOTTOM_IF(3),

  OPPOSITE(4);

  static final Map<Integer, StackMode> byCode = new HashMap<>();

  static {
        for(StackMode e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  StackMode(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static StackMode getByCode(int code) {
    return byCode.get(code);
  }
}
