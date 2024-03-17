package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Mapping implements IntValue {
  MODIFIER(0),

  KEYBOARD(1),

  POINTER(2);

  static final Map<Integer, Mapping> byCode = new HashMap<>();

  static {
        for(Mapping e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Mapping(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Mapping getByCode(int code) {
    return byCode.get(code);
  }
}
