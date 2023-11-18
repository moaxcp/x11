package com.github.moaxcp.x11client.protocol.damage;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ReportLevel implements IntValue {
  RAW_RECTANGLES(0),

  DELTA_RECTANGLES(1),

  BOUNDING_BOX(2),

  NON_EMPTY(3);

  static final Map<Integer, ReportLevel> byCode = new HashMap<>();

  static {
        for(ReportLevel e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ReportLevel(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ReportLevel getByCode(int code) {
    return byCode.get(code);
  }
}
