package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum VisualClass implements IntValue {
  STATIC_GRAY(0),

  GRAY_SCALE(1),

  STATIC_COLOR(2),

  PSEUDO_COLOR(3),

  TRUE_COLOR(4),

  DIRECT_COLOR(5);

  static final Map<Integer, VisualClass> byCode = new HashMap<>();

  static {
        for(VisualClass e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  VisualClass(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static VisualClass getByCode(int code) {
    return byCode.get(code);
  }
}
