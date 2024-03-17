package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum CapStyle implements IntValue {
  NOT_LAST(0),

  BUTT(1),

  ROUND(2),

  PROJECTING(3);

  static final Map<Integer, CapStyle> byCode = new HashMap<>();

  static {
        for(CapStyle e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  CapStyle(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static CapStyle getByCode(int code) {
    return byCode.get(code);
  }
}
