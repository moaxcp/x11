package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum PolyEdge implements IntValue {
  SHARP(0),

  SMOOTH(1);

  static final Map<Integer, PolyEdge> byCode = new HashMap<>();

  static {
        for(PolyEdge e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  PolyEdge(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static PolyEdge getByCode(int code) {
    return byCode.get(code);
  }
}
