package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Colormap implements IntValue {
  NONE(0);

  static final Map<Integer, Colormap> byCode = new HashMap<>();

  static {
        for(Colormap e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Colormap(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Colormap getByCode(int code) {
    return byCode.get(code);
  }
}
