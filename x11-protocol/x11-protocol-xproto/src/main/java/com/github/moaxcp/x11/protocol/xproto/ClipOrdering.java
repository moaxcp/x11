package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ClipOrdering implements IntValue {
  UNSORTED(0),

  Y_SORTED(1),

  Y_X_SORTED(2),

  Y_X_BANDED(3);

  static final Map<Integer, ClipOrdering> byCode = new HashMap<>();

  static {
        for(ClipOrdering e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ClipOrdering(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ClipOrdering getByCode(int code) {
    return byCode.get(code);
  }
}
