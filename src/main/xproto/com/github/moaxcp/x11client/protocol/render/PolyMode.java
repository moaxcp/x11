package com.github.moaxcp.x11client.protocol.render;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum PolyMode implements IntValue {
  PRECISE(0),

  IMPRECISE(1);

  static final Map<Integer, PolyMode> byCode = new HashMap<>();

  static {
        for(PolyMode e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  PolyMode(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static PolyMode getByCode(int code) {
    return byCode.get(code);
  }
}
