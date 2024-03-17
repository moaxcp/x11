package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum LockDeviceFlags implements IntValue {
  NO_LOCK(0b1),

  NO_UNLOCK(0b10);

  static final Map<Integer, LockDeviceFlags> byCode = new HashMap<>();

  static {
        for(LockDeviceFlags e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  LockDeviceFlags(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static LockDeviceFlags getByCode(int code) {
    return byCode.get(code);
  }
}
