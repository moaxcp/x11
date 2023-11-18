package com.github.moaxcp.x11client.protocol.record;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum HType implements IntValue {
  FROM_SERVER_TIME(0b1),

  FROM_CLIENT_TIME(0b10),

  FROM_CLIENT_SEQUENCE(0b100);

  static final Map<Integer, HType> byCode = new HashMap<>();

  static {
        for(HType e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  HType(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static HType getByCode(int code) {
    return byCode.get(code);
  }
}
