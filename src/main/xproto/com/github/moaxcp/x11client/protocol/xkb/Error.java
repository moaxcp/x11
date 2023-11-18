package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Error implements IntValue {
  BAD_DEVICE(255),

  BAD_CLASS(254),

  BAD_ID(253);

  static final Map<Integer, Error> byCode = new HashMap<>();

  static {
        for(Error e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Error(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Error getByCode(int code) {
    return byCode.get(code);
  }
}
