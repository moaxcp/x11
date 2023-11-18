package com.github.moaxcp.x11client.protocol.present;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum CompleteMode implements IntValue {
  COPY(0),

  FLIP(1),

  SKIP(2),

  SUBOPTIMAL_COPY(3);

  static final Map<Integer, CompleteMode> byCode = new HashMap<>();

  static {
        for(CompleteMode e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  CompleteMode(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static CompleteMode getByCode(int code) {
    return byCode.get(code);
  }
}
