package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum SymInterpMatch implements IntValue {
  LEVEL_ONE_ONLY(0b10000000),

  OP_MASK(127);

  static final Map<Integer, SymInterpMatch> byCode = new HashMap<>();

  static {
        for(SymInterpMatch e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  SymInterpMatch(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static SymInterpMatch getByCode(int code) {
    return byCode.get(code);
  }
}
