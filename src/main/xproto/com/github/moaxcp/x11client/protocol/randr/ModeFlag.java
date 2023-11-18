package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ModeFlag implements IntValue {
  HSYNC_POSITIVE(0b1),

  HSYNC_NEGATIVE(0b10),

  VSYNC_POSITIVE(0b100),

  VSYNC_NEGATIVE(0b1000),

  INTERLACE(0b10000),

  DOUBLE_SCAN(0b100000),

  CSYNC(0b1000000),

  CSYNC_POSITIVE(0b10000000),

  CSYNC_NEGATIVE(0b100000000),

  HSKEW_PRESENT(0b1000000000),

  BCAST(0b10000000000),

  PIXEL_MULTIPLEX(0b100000000000),

  DOUBLE_CLOCK(0b1000000000000),

  HALVE_CLOCK(0b10000000000000);

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
