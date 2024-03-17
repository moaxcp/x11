package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ChangeFeedbackControlMask implements IntValue {
  KEY_CLICK_PERCENT(0b1),

  PERCENT(0b10),

  PITCH(0b100),

  DURATION(0b1000),

  LED(0b10000),

  LED_MODE(0b100000),

  KEY(0b1000000),

  AUTO_REPEAT_MODE(0b10000000),

  STRING(0b1),

  INTEGER(0b1),

  ACCEL_NUM(0b1),

  ACCEL_DENOM(0b10),

  THRESHOLD(0b100);

  static final Map<Integer, ChangeFeedbackControlMask> byCode = new HashMap<>();

  static {
        for(ChangeFeedbackControlMask e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ChangeFeedbackControlMask(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ChangeFeedbackControlMask getByCode(int code) {
    return byCode.get(code);
  }
}
