package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum FeedbackClass implements IntValue {
  KEYBOARD(0),

  POINTER(1),

  STRING(2),

  INTEGER(3),

  LED(4),

  BELL(5);

  static final Map<Integer, FeedbackClass> byCode = new HashMap<>();

  static {
        for(FeedbackClass e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  FeedbackClass(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static FeedbackClass getByCode(int code) {
    return byCode.get(code);
  }
}
