package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum QueryShapeOf implements IntValue {
  LARGEST_CURSOR(0),

  FASTEST_TILE(1),

  FASTEST_STIPPLE(2);

  static final Map<Integer, QueryShapeOf> byCode = new HashMap<>();

  static {
        for(QueryShapeOf e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  QueryShapeOf(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static QueryShapeOf getByCode(int code) {
    return byCode.get(code);
  }
}
