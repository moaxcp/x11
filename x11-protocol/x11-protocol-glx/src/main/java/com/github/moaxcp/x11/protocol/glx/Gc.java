package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Gc implements IntValue {
  GL_CURRENT_BIT(0b1),

  GL_POINT_BIT(0b10),

  GL_LINE_BIT(0b100),

  GL_POLYGON_BIT(0b1000),

  GL_POLYGON_STIPPLE_BIT(0b10000),

  GL_PIXEL_MODE_BIT(0b100000),

  GL_LIGHTING_BIT(0b1000000),

  GL_FOG_BIT(0b10000000),

  GL_DEPTH_BUFFER_BIT(0b100000000),

  GL_ACCUM_BUFFER_BIT(0b1000000000),

  GL_STENCIL_BUFFER_BIT(0b10000000000),

  GL_VIEWPORT_BIT(0b100000000000),

  GL_TRANSFORM_BIT(0b1000000000000),

  GL_ENABLE_BIT(0b10000000000000),

  GL_COLOR_BUFFER_BIT(0b100000000000000),

  GL_HINT_BIT(0b1000000000000000),

  GL_EVAL_BIT(0b10000000000000000),

  GL_LIST_BIT(0b100000000000000000),

  GL_TEXTURE_BIT(0b1000000000000000000),

  GL_SCISSOR_BIT(0b10000000000000000000),

  GL_ALL_ATTRIB_BITS(16777215);

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
