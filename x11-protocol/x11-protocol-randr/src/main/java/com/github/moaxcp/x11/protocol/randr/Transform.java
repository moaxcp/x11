package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Transform implements IntValue {
  UNIT(0b1),

  SCALE_UP(0b10),

  SCALE_DOWN(0b100),

  PROJECTIVE(0b1000);

  static final Map<Integer, Transform> byCode = new HashMap<>();

  static {
        for(Transform e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Transform(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Transform getByCode(int code) {
    return byCode.get(code);
  }
}
