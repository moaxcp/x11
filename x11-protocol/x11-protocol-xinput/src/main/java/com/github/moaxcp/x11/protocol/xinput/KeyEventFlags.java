package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum KeyEventFlags implements IntValue {
  KEY_REPEAT(0b10000000000000000);

  static final Map<Integer, KeyEventFlags> byCode = new HashMap<>();

  static {
        for(KeyEventFlags e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  KeyEventFlags(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static KeyEventFlags getByCode(int code) {
    return byCode.get(code);
  }
}
