package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Valuetype implements IntValue {
  ABSOLUTE(0),

  RELATIVE(1);

  static final Map<Integer, Valuetype> byCode = new HashMap<>();

  static {
        for(Valuetype e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Valuetype(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Valuetype getByCode(int code) {
    return byCode.get(code);
  }
}
