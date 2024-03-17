package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum CoordMode implements IntValue {
  ORIGIN(0),

  PREVIOUS(1);

  static final Map<Integer, CoordMode> byCode = new HashMap<>();

  static {
        for(CoordMode e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  CoordMode(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static CoordMode getByCode(int code) {
    return byCode.get(code);
  }
}
