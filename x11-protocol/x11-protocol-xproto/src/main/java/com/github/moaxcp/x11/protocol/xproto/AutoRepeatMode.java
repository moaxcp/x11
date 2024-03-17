package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum AutoRepeatMode implements IntValue {
  OFF(0),

  ON(1),

  DEFAULT(2);

  static final Map<Integer, AutoRepeatMode> byCode = new HashMap<>();

  static {
        for(AutoRepeatMode e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  AutoRepeatMode(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static AutoRepeatMode getByCode(int code) {
    return byCode.get(code);
  }
}
