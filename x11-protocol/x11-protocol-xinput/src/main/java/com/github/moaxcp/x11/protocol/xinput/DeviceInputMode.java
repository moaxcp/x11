package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum DeviceInputMode implements IntValue {
  ASYNC_THIS_DEVICE(0),

  SYNC_THIS_DEVICE(1),

  REPLAY_THIS_DEVICE(2),

  ASYNC_OTHER_DEVICES(3),

  ASYNC_ALL(4),

  SYNC_ALL(5);

  static final Map<Integer, DeviceInputMode> byCode = new HashMap<>();

  static {
        for(DeviceInputMode e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  DeviceInputMode(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static DeviceInputMode getByCode(int code) {
    return byCode.get(code);
  }
}
