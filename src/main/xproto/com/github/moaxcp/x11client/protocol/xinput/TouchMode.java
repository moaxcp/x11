package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum TouchMode implements IntValue {
  DIRECT(1),

  DEPENDENT(2);

  static final Map<Integer, TouchMode> byCode = new HashMap<>();

  static {
        for(TouchMode e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  TouchMode(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static TouchMode getByCode(int code) {
    return byCode.get(code);
  }
}
