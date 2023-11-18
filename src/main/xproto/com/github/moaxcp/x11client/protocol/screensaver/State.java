package com.github.moaxcp.x11client.protocol.screensaver;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum State implements IntValue {
  OFF(0),

  ON(1),

  CYCLE(2),

  DISABLED(3);

  static final Map<Integer, State> byCode = new HashMap<>();

  static {
        for(State e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  State(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static State getByCode(int code) {
    return byCode.get(code);
  }
}
