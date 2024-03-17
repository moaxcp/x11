package com.github.moaxcp.x11.protocol.record;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Cs implements IntValue {
  CURRENT_CLIENTS(1),

  FUTURE_CLIENTS(2),

  ALL_CLIENTS(3);

  static final Map<Integer, Cs> byCode = new HashMap<>();

  static {
        for(Cs e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Cs(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Cs getByCode(int code) {
    return byCode.get(code);
  }
}
