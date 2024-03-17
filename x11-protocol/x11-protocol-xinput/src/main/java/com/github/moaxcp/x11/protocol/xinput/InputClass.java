package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum InputClass implements IntValue {
  KEY(0),

  BUTTON(1),

  VALUATOR(2),

  FEEDBACK(3),

  PROXIMITY(4),

  FOCUS(5),

  OTHER(6);

  static final Map<Integer, InputClass> byCode = new HashMap<>();

  static {
        for(InputClass e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  InputClass(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static InputClass getByCode(int code) {
    return byCode.get(code);
  }
}
