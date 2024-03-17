package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ColorFlag implements IntValue {
  RED(0b1),

  GREEN(0b10),

  BLUE(0b100);

  static final Map<Integer, ColorFlag> byCode = new HashMap<>();

  static {
        for(ColorFlag e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ColorFlag(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ColorFlag getByCode(int code) {
    return byCode.get(code);
  }
}
