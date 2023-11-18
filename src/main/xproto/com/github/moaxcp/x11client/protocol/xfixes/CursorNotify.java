package com.github.moaxcp.x11client.protocol.xfixes;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum CursorNotify implements IntValue {
  DISPLAY_CURSOR(0);

  static final Map<Integer, CursorNotify> byCode = new HashMap<>();

  static {
        for(CursorNotify e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  CursorNotify(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static CursorNotify getByCode(int code) {
    return byCode.get(code);
  }
}
