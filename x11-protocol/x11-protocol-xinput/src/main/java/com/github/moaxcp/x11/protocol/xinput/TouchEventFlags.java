package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum TouchEventFlags implements IntValue {
  TOUCH_PENDING_END(0b10000000000000000),

  TOUCH_EMULATING_POINTER(0b100000000000000000);

  static final Map<Integer, TouchEventFlags> byCode = new HashMap<>();

  static {
        for(TouchEventFlags e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  TouchEventFlags(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static TouchEventFlags getByCode(int code) {
    return byCode.get(code);
  }
}
