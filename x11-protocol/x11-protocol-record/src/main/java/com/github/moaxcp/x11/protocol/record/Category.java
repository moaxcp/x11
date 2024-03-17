package com.github.moaxcp.x11.protocol.record;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Category implements IntValue {
  FROM_SERVER(0),

  FROM_CLIENT(1),

  CLIENT_STARTED(2),

  CLIENT_DIED(3),

  START_OF_DATA(4),

  END_OF_DATA(5);

  static final Map<Integer, Category> byCode = new HashMap<>();

  static {
        for(Category e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Category(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Category getByCode(int code) {
    return byCode.get(code);
  }
}
