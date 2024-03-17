package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ScrollFlags implements IntValue {
  NO_EMULATION(0b1),

  PREFERRED(0b10);

  static final Map<Integer, ScrollFlags> byCode = new HashMap<>();

  static {
        for(ScrollFlags e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ScrollFlags(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ScrollFlags getByCode(int code) {
    return byCode.get(code);
  }
}
