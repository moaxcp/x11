package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum CMDetail implements IntValue {
  SYM_INTERP(0b1),

  GROUP_COMPAT(0b10);

  static final Map<Integer, CMDetail> byCode = new HashMap<>();

  static {
        for(CMDetail e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  CMDetail(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static CMDetail getByCode(int code) {
    return byCode.get(code);
  }
}
