package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum SaveSetMapping implements IntValue {
  MAP(0),

  UNMAP(1);

  static final Map<Integer, SaveSetMapping> byCode = new HashMap<>();

  static {
        for(SaveSetMapping e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  SaveSetMapping(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static SaveSetMapping getByCode(int code) {
    return byCode.get(code);
  }
}
