package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ModifierMask implements IntValue {
  ANY(0b10000000000000000000000000000000);

  static final Map<Integer, ModifierMask> byCode = new HashMap<>();

  static {
        for(ModifierMask e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ModifierMask(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ModifierMask getByCode(int code) {
    return byCode.get(code);
  }
}
