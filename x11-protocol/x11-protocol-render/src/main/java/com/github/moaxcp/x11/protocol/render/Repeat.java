package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Repeat implements IntValue {
  NONE(0),

  NORMAL(1),

  PAD(2),

  REFLECT(3);

  static final Map<Integer, Repeat> byCode = new HashMap<>();

  static {
        for(Repeat e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Repeat(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Repeat getByCode(int code) {
    return byCode.get(code);
  }
}
