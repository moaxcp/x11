package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Time implements IntValue {
  CURRENT_TIME(0);

  static final Map<Integer, Time> byCode = new HashMap<>();

  static {
        for(Time e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Time(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Time getByCode(int code) {
    return byCode.get(code);
  }
}
