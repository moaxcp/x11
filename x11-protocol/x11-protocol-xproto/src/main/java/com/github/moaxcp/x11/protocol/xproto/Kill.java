package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Kill implements IntValue {
  ALL_TEMPORARY(0);

  static final Map<Integer, Kill> byCode = new HashMap<>();

  static {
        for(Kill e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Kill(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Kill getByCode(int code) {
    return byCode.get(code);
  }
}
