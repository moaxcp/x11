package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum BellClassResult implements IntValue {
  KBD_FEEDBACK_CLASS(0),

  BELL_FEEDBACK_CLASS(5);

  static final Map<Integer, BellClassResult> byCode = new HashMap<>();

  static {
        for(BellClassResult e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  BellClassResult(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static BellClassResult getByCode(int code) {
    return byCode.get(code);
  }
}
