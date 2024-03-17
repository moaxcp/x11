package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum BackPixmap implements IntValue {
  NONE(0),

  PARENT_RELATIVE(1);

  static final Map<Integer, BackPixmap> byCode = new HashMap<>();

  static {
        for(BackPixmap e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  BackPixmap(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static BackPixmap getByCode(int code) {
    return byCode.get(code);
  }
}
