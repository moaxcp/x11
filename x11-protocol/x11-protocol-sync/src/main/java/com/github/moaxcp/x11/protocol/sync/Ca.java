package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Ca implements IntValue {
  COUNTER(0b1),

  VALUE_TYPE(0b10),

  VALUE(0b100),

  TEST_TYPE(0b1000),

  DELTA(0b10000),

  EVENTS(0b100000);

  static final Map<Integer, Ca> byCode = new HashMap<>();

  static {
        for(Ca e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Ca(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Ca getByCode(int code) {
    return byCode.get(code);
  }
}
