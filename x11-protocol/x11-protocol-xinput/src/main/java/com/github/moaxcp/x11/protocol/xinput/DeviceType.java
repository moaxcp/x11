package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum DeviceType implements IntValue {
  MASTER_POINTER(1),

  MASTER_KEYBOARD(2),

  SLAVE_POINTER(3),

  SLAVE_KEYBOARD(4),

  FLOATING_SLAVE(5);

  static final Map<Integer, DeviceType> byCode = new HashMap<>();

  static {
        for(DeviceType e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  DeviceType(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static DeviceType getByCode(int code) {
    return byCode.get(code);
  }
}
