package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Gravity implements IntValue {
  BIT_FORGET(0),

  WIN_UNMAP(0),

  NORTH_WEST(1),

  NORTH(2),

  NORTH_EAST(3),

  WEST(4),

  CENTER(5),

  EAST(6),

  SOUTH_WEST(7),

  SOUTH(8),

  SOUTH_EAST(9),

  STATIC(10);

  static final Map<Integer, Gravity> byCode = new HashMap<>();

  static {
        for(Gravity e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Gravity(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Gravity getByCode(int code) {
    return byCode.get(code);
  }
}
