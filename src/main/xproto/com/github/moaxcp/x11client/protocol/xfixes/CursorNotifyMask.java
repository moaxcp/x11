package com.github.moaxcp.x11client.protocol.xfixes;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum CursorNotifyMask implements IntValue {
  DISPLAY_CURSOR(0b1);

  static final Map<Integer, CursorNotifyMask> byCode = new HashMap<>();

  static {
        for(CursorNotifyMask e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  CursorNotifyMask(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static CursorNotifyMask getByCode(int code) {
    return byCode.get(code);
  }
}
