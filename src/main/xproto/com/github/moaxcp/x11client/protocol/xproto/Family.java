package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Family implements IntValue {
  INTERNET(0),

  D_E_CNET(1),

  CHAOS(2),

  SERVER_INTERPRETED(5),

  INTERNET6(6);

  static final Map<Integer, Family> byCode = new HashMap<>();

  static {
        for(Family e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Family(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Family getByCode(int code) {
    return byCode.get(code);
  }
}
