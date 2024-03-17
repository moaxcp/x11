package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Control implements IntValue {
  GROUPS_WRAP(0b1000000000000000000000000000),

  INTERNAL_MODS(0b10000000000000000000000000000),

  IGNORE_LOCK_MODS(0b100000000000000000000000000000),

  PER_KEY_REPEAT(0b1000000000000000000000000000000),

  CONTROLS_ENABLED(0b10000000000000000000000000000000);

  static final Map<Integer, Control> byCode = new HashMap<>();

  static {
        for(Control e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Control(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Control getByCode(int code) {
    return byCode.get(code);
  }
}
