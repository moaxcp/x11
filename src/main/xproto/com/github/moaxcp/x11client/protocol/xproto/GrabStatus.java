package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum GrabStatus implements IntValue {
  SUCCESS(0),

  ALREADY_GRABBED(1),

  INVALID_TIME(2),

  NOT_VIEWABLE(3),

  FROZEN(4);

  static final Map<Integer, GrabStatus> byCode = new HashMap<>();

  static {
        for(GrabStatus e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  GrabStatus(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static GrabStatus getByCode(int code) {
    return byCode.get(code);
  }
}
