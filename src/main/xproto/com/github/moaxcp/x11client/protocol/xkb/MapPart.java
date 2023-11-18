package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum MapPart implements IntValue {
  KEY_TYPES(0b1),

  KEY_SYMS(0b10),

  MODIFIER_MAP(0b100),

  EXPLICIT_COMPONENTS(0b1000),

  KEY_ACTIONS(0b10000),

  KEY_BEHAVIORS(0b100000),

  VIRTUAL_MODS(0b1000000),

  VIRTUAL_MOD_MAP(0b10000000);

  static final Map<Integer, MapPart> byCode = new HashMap<>();

  static {
        for(MapPart e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  MapPart(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static MapPart getByCode(int code) {
    return byCode.get(code);
  }
}
