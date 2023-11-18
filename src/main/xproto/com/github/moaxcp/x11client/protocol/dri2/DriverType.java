package com.github.moaxcp.x11client.protocol.dri2;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum DriverType implements IntValue {
  DRI(0),

  VDPAU(1);

  static final Map<Integer, DriverType> byCode = new HashMap<>();

  static {
        for(DriverType e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  DriverType(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static DriverType getByCode(int code) {
    return byCode.get(code);
  }
}
