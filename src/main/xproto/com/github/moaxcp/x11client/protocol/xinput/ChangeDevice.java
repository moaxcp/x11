package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ChangeDevice implements IntValue {
  NEW_POINTER(0),

  NEW_KEYBOARD(1);

  static final Map<Integer, ChangeDevice> byCode = new HashMap<>();

  static {
        for(ChangeDevice e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ChangeDevice(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ChangeDevice getByCode(int code) {
    return byCode.get(code);
  }
}
