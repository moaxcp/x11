package com.github.moaxcp.x11client;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

public enum BitGravity {
  FORGET(0),
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

  private static final Map<Integer, BitGravity> byCode = new HashMap<>();

  static {
    for(BitGravity g : values()) {
      byCode.put(g.code, g);
    }
  }

  @Getter
  private final int code;

  BitGravity(int code) {
    this.code = code;
  }

  public static BitGravity getByCode(int code) {
    return byCode.get(code);
  }
}
