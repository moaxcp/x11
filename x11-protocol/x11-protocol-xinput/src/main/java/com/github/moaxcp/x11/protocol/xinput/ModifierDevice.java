package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ModifierDevice implements IntValue {
  USE_X_KEYBOARD(255);

  static final Map<Integer, ModifierDevice> byCode = new HashMap<>();

  static {
        for(ModifierDevice e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ModifierDevice(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ModifierDevice getByCode(int code) {
    return byCode.get(code);
  }
}
