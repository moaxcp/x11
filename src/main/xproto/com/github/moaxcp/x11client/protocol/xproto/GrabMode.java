package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum GrabMode implements IntValue {
  SYNC(0),

  ASYNC(1);

  static final Map<Integer, GrabMode> byCode = new HashMap<>();

  static {
        for(GrabMode e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  GrabMode(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static GrabMode getByCode(int code) {
    return byCode.get(code);
  }
}
