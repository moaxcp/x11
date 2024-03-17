package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum BarrierDirections implements IntValue {
  POSITIVE_X(0b1),

  POSITIVE_Y(0b10),

  NEGATIVE_X(0b100),

  NEGATIVE_Y(0b1000);

  static final Map<Integer, BarrierDirections> byCode = new HashMap<>();

  static {
        for(BarrierDirections e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  BarrierDirections(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static BarrierDirections getByCode(int code) {
    return byCode.get(code);
  }
}
