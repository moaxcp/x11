package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Circulate implements IntValue {
  RAISE_LOWEST(0),

  LOWER_HIGHEST(1);

  static final Map<Integer, Circulate> byCode = new HashMap<>();

  static {
        for(Circulate e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Circulate(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Circulate getByCode(int code) {
    return byCode.get(code);
  }
}
