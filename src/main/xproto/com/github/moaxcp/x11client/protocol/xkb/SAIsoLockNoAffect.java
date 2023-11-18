package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum SAIsoLockNoAffect implements IntValue {
  CTRLS(0b1000),

  PTR(0b10000),

  GROUP(0b100000),

  MODS(0b1000000);

  static final Map<Integer, SAIsoLockNoAffect> byCode = new HashMap<>();

  static {
        for(SAIsoLockNoAffect e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  SAIsoLockNoAffect(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static SAIsoLockNoAffect getByCode(int code) {
    return byCode.get(code);
  }
}
