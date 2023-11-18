package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Grab implements IntValue {
  ANY(0);

  static final Map<Integer, Grab> byCode = new HashMap<>();

  static {
        for(Grab e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Grab(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Grab getByCode(int code) {
    return byCode.get(code);
  }
}
