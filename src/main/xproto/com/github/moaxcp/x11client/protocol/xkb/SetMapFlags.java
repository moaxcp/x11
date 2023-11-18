package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum SetMapFlags implements IntValue {
  RESIZE_TYPES(0b1),

  RECOMPUTE_ACTIONS(0b10);

  static final Map<Integer, SetMapFlags> byCode = new HashMap<>();

  static {
        for(SetMapFlags e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  SetMapFlags(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static SetMapFlags getByCode(int code) {
    return byCode.get(code);
  }
}
