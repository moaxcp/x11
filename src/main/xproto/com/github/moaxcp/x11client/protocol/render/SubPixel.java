package com.github.moaxcp.x11client.protocol.render;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum SubPixel implements IntValue {
  UNKNOWN(0),

  HORIZONTAL_R_G_B(1),

  HORIZONTAL_B_G_R(2),

  VERTICAL_R_G_B(3),

  VERTICAL_B_G_R(4),

  NONE(5);

  static final Map<Integer, SubPixel> byCode = new HashMap<>();

  static {
        for(SubPixel e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  SubPixel(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static SubPixel getByCode(int code) {
    return byCode.get(code);
  }
}
