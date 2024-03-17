package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum MoreEventsMask implements IntValue {
  MORE_EVENTS(0b10000000);

  static final Map<Integer, MoreEventsMask> byCode = new HashMap<>();

  static {
        for(MoreEventsMask e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  MoreEventsMask(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static MoreEventsMask getByCode(int code) {
    return byCode.get(code);
  }
}
