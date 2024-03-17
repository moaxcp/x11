package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum DeviceChange implements IntValue {
  ADDED(0),

  REMOVED(1),

  ENABLED(2),

  DISABLED(3),

  UNRECOVERABLE(4),

  CONTROL_CHANGED(5);

  static final Map<Integer, DeviceChange> byCode = new HashMap<>();

  static {
        for(DeviceChange e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  DeviceChange(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static DeviceChange getByCode(int code) {
    return byCode.get(code);
  }
}
