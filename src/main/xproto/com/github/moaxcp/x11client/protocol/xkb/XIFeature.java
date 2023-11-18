package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum XIFeature implements IntValue {
  KEYBOARDS(0b1),

  BUTTON_ACTIONS(0b10),

  INDICATOR_NAMES(0b100),

  INDICATOR_MAPS(0b1000),

  INDICATOR_STATE(0b10000);

  static final Map<Integer, XIFeature> byCode = new HashMap<>();

  static {
        for(XIFeature e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  XIFeature(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static XIFeature getByCode(int code) {
    return byCode.get(code);
  }
}
