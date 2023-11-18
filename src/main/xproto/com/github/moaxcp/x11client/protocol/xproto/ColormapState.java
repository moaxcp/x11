package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ColormapState implements IntValue {
  UNINSTALLED(0),

  INSTALLED(1);

  static final Map<Integer, ColormapState> byCode = new HashMap<>();

  static {
        for(ColormapState e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ColormapState(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ColormapState getByCode(int code) {
    return byCode.get(code);
  }
}
