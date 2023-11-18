package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ColormapAlloc implements IntValue {
  NONE(0),

  ALL(1);

  static final Map<Integer, ColormapAlloc> byCode = new HashMap<>();

  static {
        for(ColormapAlloc e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ColormapAlloc(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ColormapAlloc getByCode(int code) {
    return byCode.get(code);
  }
}
