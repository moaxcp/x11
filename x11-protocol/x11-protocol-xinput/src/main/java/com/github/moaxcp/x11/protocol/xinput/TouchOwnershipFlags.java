package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum TouchOwnershipFlags implements IntValue {
  NONE(0);

  static final Map<Integer, TouchOwnershipFlags> byCode = new HashMap<>();

  static {
        for(TouchOwnershipFlags e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  TouchOwnershipFlags(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static TouchOwnershipFlags getByCode(int code) {
    return byCode.get(code);
  }
}
