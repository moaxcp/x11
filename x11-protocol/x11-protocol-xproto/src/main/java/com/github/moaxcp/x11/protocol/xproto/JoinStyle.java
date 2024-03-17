package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum JoinStyle implements IntValue {
  MITER(0),

  ROUND(1),

  BEVEL(2);

  static final Map<Integer, JoinStyle> byCode = new HashMap<>();

  static {
        for(JoinStyle e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  JoinStyle(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static JoinStyle getByCode(int code) {
    return byCode.get(code);
  }
}
