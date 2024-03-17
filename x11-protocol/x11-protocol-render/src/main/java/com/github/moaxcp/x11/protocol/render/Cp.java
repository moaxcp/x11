package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Cp implements IntValue {
  REPEAT(0b1),

  ALPHA_MAP(0b10),

  ALPHA_X_ORIGIN(0b100),

  ALPHA_Y_ORIGIN(0b1000),

  CLIP_X_ORIGIN(0b10000),

  CLIP_Y_ORIGIN(0b100000),

  CLIP_MASK(0b1000000),

  GRAPHICS_EXPOSURE(0b10000000),

  SUBWINDOW_MODE(0b100000000),

  POLY_EDGE(0b1000000000),

  POLY_MODE(0b10000000000),

  DITHER(0b100000000000),

  COMPONENT_ALPHA(0b1000000000000);

  static final Map<Integer, Cp> byCode = new HashMap<>();

  static {
        for(Cp e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Cp(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Cp getByCode(int code) {
    return byCode.get(code);
  }
}
