package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum SAIsoLockFlag implements IntValue {
  NO_LOCK(0b1),

  NO_UNLOCK(0b10),

  USE_MOD_MAP_MODS(0b100),

  GROUP_ABSOLUTE(0b100),

  I_S_O_DFLT_IS_GROUP(0b1000);

  static final Map<Integer, SAIsoLockFlag> byCode = new HashMap<>();

  static {
        for(SAIsoLockFlag e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  SAIsoLockFlag(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static SAIsoLockFlag getByCode(int code) {
    return byCode.get(code);
  }
}
