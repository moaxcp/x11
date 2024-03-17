package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Place implements IntValue {
  ON_TOP(0),

  ON_BOTTOM(1);

  static final Map<Integer, Place> byCode = new HashMap<>();

  static {
        for(Place e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Place(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Place getByCode(int code) {
    return byCode.get(code);
  }
}
