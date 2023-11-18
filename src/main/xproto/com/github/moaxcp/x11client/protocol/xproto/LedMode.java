package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum LedMode implements IntValue {
  OFF(0),

  ON(1);

  static final Map<Integer, LedMode> byCode = new HashMap<>();

  static {
        for(LedMode e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  LedMode(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static LedMode getByCode(int code) {
    return byCode.get(code);
  }
}
