package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum DoodadType implements IntValue {
  OUTLINE(1),

  SOLID(2),

  TEXT(3),

  INDICATOR(4),

  LOGO(5);

  static final Map<Integer, DoodadType> byCode = new HashMap<>();

  static {
        for(DoodadType e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  DoodadType(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static DoodadType getByCode(int code) {
    return byCode.get(code);
  }
}
