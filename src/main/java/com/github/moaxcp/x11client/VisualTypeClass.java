package com.github.moaxcp.x11client;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

public enum VisualTypeClass {
  STATIC_GRAY(0),
  GRAY_SCALE(1),
  STATIC_COLOR(2),
  PSEUDO_COLOR(3),
  TRUE_COLOR(4),
  DIRECT_COLOR(5);

  @Getter
  private final int code;

  private static Map<Integer, VisualTypeClass> forCode = new HashMap<>();

  static {
    for(VisualTypeClass v : values()) {
      forCode.put(v.getCode(), v);
    }
  }

  VisualTypeClass(int code) {
    this.code = code;
  }

  public static VisualTypeClass getByCode(int code) {
    return forCode.get(code);
  }
}
