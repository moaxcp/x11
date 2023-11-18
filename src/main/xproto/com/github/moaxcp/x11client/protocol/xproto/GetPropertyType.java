package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum GetPropertyType implements IntValue {
  ANY(0);

  static final Map<Integer, GetPropertyType> byCode = new HashMap<>();

  static {
        for(GetPropertyType e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  GetPropertyType(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static GetPropertyType getByCode(int code) {
    return byCode.get(code);
  }
}
