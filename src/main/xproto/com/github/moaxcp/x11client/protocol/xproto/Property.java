package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Property implements IntValue {
  NEW_VALUE(0),

  DELETE(1);

  static final Map<Integer, Property> byCode = new HashMap<>();

  static {
        for(Property e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Property(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Property getByCode(int code) {
    return byCode.get(code);
  }
}
