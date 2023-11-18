package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum SymInterpretMatch implements IntValue {
  NONE_OF(0),

  ANY_OF_OR_NONE(1),

  ANY_OF(2),

  ALL_OF(3),

  EXACTLY(4);

  static final Map<Integer, SymInterpretMatch> byCode = new HashMap<>();

  static {
        for(SymInterpretMatch e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  SymInterpretMatch(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static SymInterpretMatch getByCode(int code) {
    return byCode.get(code);
  }
}
