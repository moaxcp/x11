package com.github.moaxcp.x11client.protocol.present;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Capability implements IntValue {
  NONE(0),

  ASYNC(0b1),

  FENCE(0b10),

  UST(0b100);

  static final Map<Integer, Capability> byCode = new HashMap<>();

  static {
        for(Capability e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Capability(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Capability getByCode(int code) {
    return byCode.get(code);
  }
}
