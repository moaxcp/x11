package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum PointerEventFlags implements IntValue {
  POINTER_EMULATED(0b10000000000000000);

  static final Map<Integer, PointerEventFlags> byCode = new HashMap<>();

  static {
        for(PointerEventFlags e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  PointerEventFlags(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static PointerEventFlags getByCode(int code) {
    return byCode.get(code);
  }
}
