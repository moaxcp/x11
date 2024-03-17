package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum DeviceUse implements IntValue {
  IS_X_POINTER(0),

  IS_X_KEYBOARD(1),

  IS_X_EXTENSION_DEVICE(2),

  IS_X_EXTENSION_KEYBOARD(3),

  IS_X_EXTENSION_POINTER(4);

  static final Map<Integer, DeviceUse> byCode = new HashMap<>();

  static {
        for(DeviceUse e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  DeviceUse(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static DeviceUse getByCode(int code) {
    return byCode.get(code);
  }
}
