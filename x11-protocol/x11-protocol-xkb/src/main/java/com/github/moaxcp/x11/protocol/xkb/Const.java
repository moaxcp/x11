package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Const implements IntValue {
  MAX_LEGAL_KEY_CODE(255),

  PER_KEY_BIT_ARRAY_SIZE(32),

  KEY_NAME_LENGTH(4);

  static final Map<Integer, Const> byCode = new HashMap<>();

  static {
        for(Const e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Const(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Const getByCode(int code) {
    return byCode.get(code);
  }
}
