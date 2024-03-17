package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum InputFocus implements IntValue {
  NONE(0),

  POINTER_ROOT(1),

  PARENT(2),

  FOLLOW_KEYBOARD(3);

  static final Map<Integer, InputFocus> byCode = new HashMap<>();

  static {
        for(InputFocus e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  InputFocus(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static InputFocus getByCode(int code) {
    return byCode.get(code);
  }
}
