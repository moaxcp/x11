package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum DeviceClassType implements IntValue {
  KEY(0),

  BUTTON(1),

  VALUATOR(2),

  SCROLL(3),

  TOUCH(8);

  static final Map<Integer, DeviceClassType> byCode = new HashMap<>();

  static {
        for(DeviceClassType e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  DeviceClassType(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static DeviceClassType getByCode(int code) {
    return byCode.get(code);
  }
}
