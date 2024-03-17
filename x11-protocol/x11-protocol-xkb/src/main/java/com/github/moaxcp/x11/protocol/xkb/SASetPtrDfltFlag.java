package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum SASetPtrDfltFlag implements IntValue {
  DFLT_BTN_ABSOLUTE(0b100),

  AFFECT_DFLT_BUTTON(0b1);

  static final Map<Integer, SASetPtrDfltFlag> byCode = new HashMap<>();

  static {
        for(SASetPtrDfltFlag e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  SASetPtrDfltFlag(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static SASetPtrDfltFlag getByCode(int code) {
    return byCode.get(code);
  }
}
