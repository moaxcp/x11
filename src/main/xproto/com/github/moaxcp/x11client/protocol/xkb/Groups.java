package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Groups implements IntValue {
  ANY(254),

  ALL(255);

  static final Map<Integer, Groups> byCode = new HashMap<>();

  static {
        for(Groups e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Groups(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Groups getByCode(int code) {
    return byCode.get(code);
  }
}
