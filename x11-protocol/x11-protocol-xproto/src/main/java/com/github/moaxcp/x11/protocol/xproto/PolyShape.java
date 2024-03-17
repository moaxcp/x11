package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum PolyShape implements IntValue {
  COMPLEX(0),

  NONCONVEX(1),

  CONVEX(2);

  static final Map<Integer, PolyShape> byCode = new HashMap<>();

  static {
        for(PolyShape e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  PolyShape(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static PolyShape getByCode(int code) {
    return byCode.get(code);
  }
}
