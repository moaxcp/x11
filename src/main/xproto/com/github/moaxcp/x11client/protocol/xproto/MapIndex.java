package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum MapIndex implements IntValue {
  SHIFT(0),

  LOCK(1),

  CONTROL(2),

  ONE(3),

  TWO(4),

  THREE(5),

  FOUR(6),

  FIVE(7);

  static final Map<Integer, MapIndex> byCode = new HashMap<>();

  static {
        for(MapIndex e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  MapIndex(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static MapIndex getByCode(int code) {
    return byCode.get(code);
  }
}
