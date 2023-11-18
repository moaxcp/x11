package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ActionMessageFlag implements IntValue {
  ON_PRESS(0b1),

  ON_RELEASE(0b10),

  GEN_KEY_EVENT(0b100);

  static final Map<Integer, ActionMessageFlag> byCode = new HashMap<>();

  static {
        for(ActionMessageFlag e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ActionMessageFlag(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ActionMessageFlag getByCode(int code) {
    return byCode.get(code);
  }
}
