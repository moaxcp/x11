package com.github.moaxcp.x11client;

import java.util.HashMap;
import java.util.Map;

public enum BackingStore {
  NEVER(0),
  WHEN_MAPPED(1),
  ALWAYS(2);

  private final int code;

  private static Map<Integer, BackingStore> byCode = new HashMap<>();

  static {
    for(BackingStore b : values()) {
      byCode.put(b.code, b);
    }
  }

  BackingStore(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }

  public static BackingStore getByCode(int code) {
    return byCode.get(code);
  }
}
