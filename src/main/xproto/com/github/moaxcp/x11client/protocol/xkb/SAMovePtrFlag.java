package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum SAMovePtrFlag implements IntValue {
  NO_ACCELERATION(0b1),

  MOVE_ABSOLUTE_X(0b10),

  MOVE_ABSOLUTE_Y(0b100);

  static final Map<Integer, SAMovePtrFlag> byCode = new HashMap<>();

  static {
        for(SAMovePtrFlag e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  SAMovePtrFlag(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static SAMovePtrFlag getByCode(int code) {
    return byCode.get(code);
  }
}
