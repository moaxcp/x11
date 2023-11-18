package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum LineStyle implements IntValue {
  SOLID(0),

  ON_OFF_DASH(1),

  DOUBLE_DASH(2);

  static final Map<Integer, LineStyle> byCode = new HashMap<>();

  static {
        for(LineStyle e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  LineStyle(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static LineStyle getByCode(int code) {
    return byCode.get(code);
  }
}
