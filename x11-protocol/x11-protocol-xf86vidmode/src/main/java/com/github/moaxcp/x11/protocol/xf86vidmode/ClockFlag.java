package com.github.moaxcp.x11.protocol.xf86vidmode;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ClockFlag implements IntValue {
  PROGRAMABLE(0b1);

  static final Map<Integer, ClockFlag> byCode = new HashMap<>();

  static {
        for(ClockFlag e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ClockFlag(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ClockFlag getByCode(int code) {
    return byCode.get(code);
  }
}
