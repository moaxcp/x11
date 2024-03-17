package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Alarmstate implements IntValue {
  ACTIVE(0),

  INACTIVE(1),

  DESTROYED(2);

  static final Map<Integer, Alarmstate> byCode = new HashMap<>();

  static {
        for(Alarmstate e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Alarmstate(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Alarmstate getByCode(int code) {
    return byCode.get(code);
  }
}
