package com.github.moaxcp.x11client;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

public enum WinGravity {
  UNMAP(0),
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

  private static final Map<Integer, WinGravity> byCode = new HashMap<>();

  static {
    for(WinGravity g : values()) {
      byCode.put(g.code, g);
    }
  }

  @Getter
  private final int code;

  WinGravity(int code) {
    this.code = code;
  }

  public static WinGravity getByCode(int code) {
    return byCode.get(code);
  }
}
