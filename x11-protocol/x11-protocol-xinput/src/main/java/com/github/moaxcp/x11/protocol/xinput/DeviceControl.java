package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum DeviceControl implements IntValue {
  RESOLUTION(1),

  ABS_CALIB(2),

  CORE(3),

  ENABLE(4),

  ABS_AREA(5);

  static final Map<Integer, DeviceControl> byCode = new HashMap<>();

  static {
        for(DeviceControl e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  DeviceControl(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static DeviceControl getByCode(int code) {
    return byCode.get(code);
  }
}
