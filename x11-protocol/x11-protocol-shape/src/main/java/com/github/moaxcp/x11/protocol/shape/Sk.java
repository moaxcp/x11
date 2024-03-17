package com.github.moaxcp.x11.protocol.shape;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Sk implements IntValue {
  BOUNDING(0),

  CLIP(1),

  INPUT(2);

  static final Map<Integer, Sk> byCode = new HashMap<>();

  static {
        for(Sk e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Sk(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Sk getByCode(int code) {
    return byCode.get(code);
  }
}
