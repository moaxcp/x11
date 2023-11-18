package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ChangeReason implements IntValue {
  SLAVE_SWITCH(1),

  DEVICE_CHANGE(2);

  static final Map<Integer, ChangeReason> byCode = new HashMap<>();

  static {
        for(ChangeReason e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ChangeReason(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ChangeReason getByCode(int code) {
    return byCode.get(code);
  }
}
