package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum FillStyle implements IntValue {
  SOLID(0),

  TILED(1),

  STIPPLED(2),

  OPAQUE_STIPPLED(3);

  static final Map<Integer, FillStyle> byCode = new HashMap<>();

  static {
        for(FillStyle e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  FillStyle(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static FillStyle getByCode(int code) {
    return byCode.get(code);
  }
}
