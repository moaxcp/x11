package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum GrabType implements IntValue {
  BUTTON(0),

  KEYCODE(1),

  ENTER(2),

  FOCUS_IN(3),

  TOUCH_BEGIN(4);

  static final Map<Integer, GrabType> byCode = new HashMap<>();

  static {
        for(GrabType e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  GrabType(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static GrabType getByCode(int code) {
    return byCode.get(code);
  }
}
