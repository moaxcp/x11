package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum PropertyFlag implements IntValue {
  DELETED(0),

  CREATED(1),

  MODIFIED(2);

  static final Map<Integer, PropertyFlag> byCode = new HashMap<>();

  static {
        for(PropertyFlag e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  PropertyFlag(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static PropertyFlag getByCode(int code) {
    return byCode.get(code);
  }
}
