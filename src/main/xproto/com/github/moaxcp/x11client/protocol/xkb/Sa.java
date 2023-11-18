package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Sa implements IntValue {
  CLEAR_LOCKS(0b1),

  LATCH_TO_LOCK(0b10),

  USE_MOD_MAP_MODS(0b100),

  GROUP_ABSOLUTE(0b100);

  static final Map<Integer, Sa> byCode = new HashMap<>();

  static {
        for(Sa e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Sa(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Sa getByCode(int code) {
    return byCode.get(code);
  }
}
