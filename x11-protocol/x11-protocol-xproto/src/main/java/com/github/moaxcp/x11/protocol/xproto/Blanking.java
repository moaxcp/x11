package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Blanking implements IntValue {
  NOT_PREFERRED(0),

  PREFERRED(1),

  DEFAULT(2);

  static final Map<Integer, Blanking> byCode = new HashMap<>();

  static {
        for(Blanking e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Blanking(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Blanking getByCode(int code) {
    return byCode.get(code);
  }
}
