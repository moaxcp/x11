package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ValuatorStateModeMask implements IntValue {
  DEVICE_MODE_ABSOLUTE(0b1),

  OUT_OF_PROXIMITY(0b10);

  static final Map<Integer, ValuatorStateModeMask> byCode = new HashMap<>();

  static {
        for(ValuatorStateModeMask e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ValuatorStateModeMask(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ValuatorStateModeMask getByCode(int code) {
    return byCode.get(code);
  }
}
