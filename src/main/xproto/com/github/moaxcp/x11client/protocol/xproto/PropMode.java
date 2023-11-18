package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum PropMode implements IntValue {
  REPLACE(0),

  PREPEND(1),

  APPEND(2);

  static final Map<Integer, PropMode> byCode = new HashMap<>();

  static {
        for(PropMode e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  PropMode(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static PropMode getByCode(int code) {
    return byCode.get(code);
  }
}
