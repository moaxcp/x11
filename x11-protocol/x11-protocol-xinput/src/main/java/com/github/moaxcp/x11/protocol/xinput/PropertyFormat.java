package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum PropertyFormat implements IntValue {
  EIGHT_BITS(8),

  SIXTEEN_BITS(16),

  THIRTY_TWO_BITS(32);

  static final Map<Integer, PropertyFormat> byCode = new HashMap<>();

  static {
        for(PropertyFormat e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  PropertyFormat(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static PropertyFormat getByCode(int code) {
    return byCode.get(code);
  }
}
