package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum StatePart implements IntValue {
  MODIFIER_STATE(0b1),

  MODIFIER_BASE(0b10),

  MODIFIER_LATCH(0b100),

  MODIFIER_LOCK(0b1000),

  GROUP_STATE(0b10000),

  GROUP_BASE(0b100000),

  GROUP_LATCH(0b1000000),

  GROUP_LOCK(0b10000000),

  COMPAT_STATE(0b100000000),

  GRAB_MODS(0b1000000000),

  COMPAT_GRAB_MODS(0b10000000000),

  LOOKUP_MODS(0b100000000000),

  COMPAT_LOOKUP_MODS(0b1000000000000),

  POINTER_BUTTONS(0b10000000000000);

  static final Map<Integer, StatePart> byCode = new HashMap<>();

  static {
        for(StatePart e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  StatePart(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static StatePart getByCode(int code) {
    return byCode.get(code);
  }
}
