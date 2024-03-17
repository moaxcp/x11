package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Gc implements IntValue {
  FUNCTION(0b1),

  PLANE_MASK(0b10),

  FOREGROUND(0b100),

  BACKGROUND(0b1000),

  LINE_WIDTH(0b10000),

  LINE_STYLE(0b100000),

  CAP_STYLE(0b1000000),

  JOIN_STYLE(0b10000000),

  FILL_STYLE(0b100000000),

  FILL_RULE(0b1000000000),

  TILE(0b10000000000),

  STIPPLE(0b100000000000),

  TILE_STIPPLE_ORIGIN_X(0b1000000000000),

  TILE_STIPPLE_ORIGIN_Y(0b10000000000000),

  FONT(0b100000000000000),

  SUBWINDOW_MODE(0b1000000000000000),

  GRAPHICS_EXPOSURES(0b10000000000000000),

  CLIP_ORIGIN_X(0b100000000000000000),

  CLIP_ORIGIN_Y(0b1000000000000000000),

  CLIP_MASK(0b10000000000000000000),

  DASH_OFFSET(0b100000000000000000000),

  DASH_LIST(0b1000000000000000000000),

  ARC_MODE(0b10000000000000000000000);

  static final Map<Integer, Gc> byCode = new HashMap<>();

  static {
        for(Gc e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Gc(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Gc getByCode(int code) {
    return byCode.get(code);
  }
}
