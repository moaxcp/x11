package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum NameDetail implements IntValue {
  KEYCODES(0b1),

  GEOMETRY(0b10),

  SYMBOLS(0b100),

  PHYS_SYMBOLS(0b1000),

  TYPES(0b10000),

  COMPAT(0b100000),

  KEY_TYPE_NAMES(0b1000000),

  K_T_LEVEL_NAMES(0b10000000),

  INDICATOR_NAMES(0b100000000),

  KEY_NAMES(0b1000000000),

  KEY_ALIASES(0b10000000000),

  VIRTUAL_MOD_NAMES(0b100000000000),

  GROUP_NAMES(0b1000000000000),

  R_G_NAMES(0b10000000000000);

  static final Map<Integer, NameDetail> byCode = new HashMap<>();

  static {
        for(NameDetail e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  NameDetail(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static NameDetail getByCode(int code) {
    return byCode.get(code);
  }
}
