package com.github.moaxcp.x11client.protocol.sync;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Testtype implements IntValue {
  POSITIVE_TRANSITION(0),

  NEGATIVE_TRANSITION(1),

  POSITIVE_COMPARISON(2),

  NEGATIVE_COMPARISON(3);

  static final Map<Integer, Testtype> byCode = new HashMap<>();

  static {
        for(Testtype e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Testtype(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Testtype getByCode(int code) {
    return byCode.get(code);
  }
}
