package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Device implements IntValue {
  ALL(0),

  ALL_MASTER(1);

  static final Map<Integer, Device> byCode = new HashMap<>();

  static {
        for(Device e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Device(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Device getByCode(int code) {
    return byCode.get(code);
  }
}
