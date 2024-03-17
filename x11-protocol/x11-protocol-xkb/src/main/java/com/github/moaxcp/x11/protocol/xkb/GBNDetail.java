package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum GBNDetail implements IntValue {
  TYPES(0b1),

  COMPAT_MAP(0b10),

  CLIENT_SYMBOLS(0b100),

  SERVER_SYMBOLS(0b1000),

  INDICATOR_MAPS(0b10000),

  KEY_NAMES(0b100000),

  GEOMETRY(0b1000000),

  OTHER_NAMES(0b10000000);

  static final Map<Integer, GBNDetail> byCode = new HashMap<>();

  static {
        for(GBNDetail e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  GBNDetail(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static GBNDetail getByCode(int code) {
    return byCode.get(code);
  }
}
