package com.github.moaxcp.x11.protocol.shape;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum So implements IntValue {
  SET(0),

  UNION(1),

  INTERSECT(2),

  SUBTRACT(3),

  INVERT(4);

  static final Map<Integer, So> byCode = new HashMap<>();

  static {
        for(So e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  So(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static So getByCode(int code) {
    return byCode.get(code);
  }
}
