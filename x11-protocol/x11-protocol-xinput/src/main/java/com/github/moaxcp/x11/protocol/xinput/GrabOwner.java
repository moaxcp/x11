package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum GrabOwner implements IntValue {
  NO_OWNER(0),

  OWNER(1);

  static final Map<Integer, GrabOwner> byCode = new HashMap<>();

  static {
        for(GrabOwner e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  GrabOwner(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static GrabOwner getByCode(int code) {
    return byCode.get(code);
  }
}
