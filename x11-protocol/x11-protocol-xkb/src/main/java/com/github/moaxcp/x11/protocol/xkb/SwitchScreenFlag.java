package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum SwitchScreenFlag implements IntValue {
  APPLICATION(0b1),

  ABSOLUTE(0b100);

  static final Map<Integer, SwitchScreenFlag> byCode = new HashMap<>();

  static {
        for(SwitchScreenFlag e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  SwitchScreenFlag(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static SwitchScreenFlag getByCode(int code) {
    return byCode.get(code);
  }
}
