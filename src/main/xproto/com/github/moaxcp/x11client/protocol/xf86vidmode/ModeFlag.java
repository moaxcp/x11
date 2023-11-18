package com.github.moaxcp.x11client.protocol.xf86vidmode;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ModeFlag implements IntValue {
  POSITIVE_HSYNC(0b1),

  NEGATIVE_HSYNC(0b10),

  POSITIVE_VSYNC(0b100),

  NEGATIVE_VSYNC(0b1000),

  INTERLACE(0b10000),

  COMPOSITE_SYNC(0b100000),

  POSITIVE_CSYNC(0b1000000),

  NEGATIVE_CSYNC(0b10000000),

  H_SKEW(0b100000000),

  BROADCAST(0b1000000000),

  PIXMUX(0b10000000000),

  DOUBLE_CLOCK(0b100000000000),

  HALF_CLOCK(0b1000000000000);

  static final Map<Integer, ModeFlag> byCode = new HashMap<>();

  static {
        for(ModeFlag e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ModeFlag(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ModeFlag getByCode(int code) {
    return byCode.get(code);
  }
}
