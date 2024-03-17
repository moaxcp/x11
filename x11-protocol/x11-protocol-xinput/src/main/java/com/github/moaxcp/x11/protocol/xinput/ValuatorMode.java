package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ValuatorMode implements IntValue {
  RELATIVE(0),

  ABSOLUTE(1);

  static final Map<Integer, ValuatorMode> byCode = new HashMap<>();

  static {
        for(ValuatorMode e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ValuatorMode(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ValuatorMode getByCode(int code) {
    return byCode.get(code);
  }
}
