package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum AccessControl implements IntValue {
  DISABLE(0),

  ENABLE(1);

  static final Map<Integer, AccessControl> byCode = new HashMap<>();

  static {
        for(AccessControl e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  AccessControl(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static AccessControl getByCode(int code) {
    return byCode.get(code);
  }
}
