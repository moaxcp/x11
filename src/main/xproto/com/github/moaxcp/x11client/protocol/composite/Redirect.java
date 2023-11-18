package com.github.moaxcp.x11client.protocol.composite;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Redirect implements IntValue {
  AUTOMATIC(0),

  MANUAL(1);

  static final Map<Integer, Redirect> byCode = new HashMap<>();

  static {
        for(Redirect e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Redirect(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Redirect getByCode(int code) {
    return byCode.get(code);
  }
}
