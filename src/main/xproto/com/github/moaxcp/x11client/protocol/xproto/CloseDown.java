package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum CloseDown implements IntValue {
  DESTROY_ALL(0),

  RETAIN_PERMANENT(1),

  RETAIN_TEMPORARY(2);

  static final Map<Integer, CloseDown> byCode = new HashMap<>();

  static {
        for(CloseDown e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  CloseDown(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static CloseDown getByCode(int code) {
    return byCode.get(code);
  }
}
