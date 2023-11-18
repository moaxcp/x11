package com.github.moaxcp.x11client.protocol.glx;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Pbcet implements IntValue {
  DAMAGED(32791),

  SAVED(32792);

  static final Map<Integer, Pbcet> byCode = new HashMap<>();

  static {
        for(Pbcet e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Pbcet(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Pbcet getByCode(int code) {
    return byCode.get(code);
  }
}
