package com.github.moaxcp.x11.protocol.present;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum CompleteKind implements IntValue {
  PIXMAP(0),

  NOTIFY_M_S_C(1);

  static final Map<Integer, CompleteKind> byCode = new HashMap<>();

  static {
        for(CompleteKind e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  CompleteKind(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static CompleteKind getByCode(int code) {
    return byCode.get(code);
  }
}
