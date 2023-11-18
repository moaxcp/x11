package com.github.moaxcp.x11client.protocol.xfixes;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Region implements IntValue {
  NONE(0);

  static final Map<Integer, Region> byCode = new HashMap<>();

  static {
        for(Region e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Region(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Region getByCode(int code) {
    return byCode.get(code);
  }
}
