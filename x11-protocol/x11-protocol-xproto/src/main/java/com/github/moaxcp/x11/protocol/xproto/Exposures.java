package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Exposures implements IntValue {
  NOT_ALLOWED(0),

  ALLOWED(1),

  DEFAULT(2);

  static final Map<Integer, Exposures> byCode = new HashMap<>();

  static {
        for(Exposures e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Exposures(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Exposures getByCode(int code) {
    return byCode.get(code);
  }
}
