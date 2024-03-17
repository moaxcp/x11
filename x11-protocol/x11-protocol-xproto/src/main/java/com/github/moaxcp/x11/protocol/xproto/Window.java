package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Window implements IntValue {
  NONE(0);

  static final Map<Integer, Window> byCode = new HashMap<>();

  static {
        for(Window e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Window(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Window getByCode(int code) {
    return byCode.get(code);
  }
}
