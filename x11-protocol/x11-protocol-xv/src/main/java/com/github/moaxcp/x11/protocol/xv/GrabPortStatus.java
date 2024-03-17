package com.github.moaxcp.x11.protocol.xv;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum GrabPortStatus implements IntValue {
  SUCCESS(0),

  BAD_EXTENSION(1),

  ALREADY_GRABBED(2),

  INVALID_TIME(3),

  BAD_REPLY(4),

  BAD_ALLOC(5);

  static final Map<Integer, GrabPortStatus> byCode = new HashMap<>();

  static {
        for(GrabPortStatus e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  GrabPortStatus(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static GrabPortStatus getByCode(int code) {
    return byCode.get(code);
  }
}
