package com.github.moaxcp.x11client.protocol.xv;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Type implements IntValue {
  INPUT_MASK(0b1),

  OUTPUT_MASK(0b10),

  VIDEO_MASK(0b100),

  STILL_MASK(0b1000),

  IMAGE_MASK(0b10000);

  static final Map<Integer, Type> byCode = new HashMap<>();

  static {
        for(Type e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Type(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Type getByCode(int code) {
    return byCode.get(code);
  }
}
