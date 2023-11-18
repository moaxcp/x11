package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Motion implements IntValue {
  NORMAL(0),

  HINT(1);

  static final Map<Integer, Motion> byCode = new HashMap<>();

  static {
        for(Motion e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Motion(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Motion getByCode(int code) {
    return byCode.get(code);
  }
}
