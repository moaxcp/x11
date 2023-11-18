package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Kb implements IntValue {
  KEY_CLICK_PERCENT(0b1),

  BELL_PERCENT(0b10),

  BELL_PITCH(0b100),

  BELL_DURATION(0b1000),

  LED(0b10000),

  LED_MODE(0b100000),

  KEY(0b1000000),

  AUTO_REPEAT_MODE(0b10000000);

  static final Map<Integer, Kb> byCode = new HashMap<>();

  static {
        for(Kb e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Kb(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Kb getByCode(int code) {
    return byCode.get(code);
  }
}
