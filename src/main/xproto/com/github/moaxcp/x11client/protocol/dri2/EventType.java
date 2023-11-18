package com.github.moaxcp.x11client.protocol.dri2;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum EventType implements IntValue {
  EXCHANGE_COMPLETE(1),

  BLIT_COMPLETE(2),

  FLIP_COMPLETE(3);

  static final Map<Integer, EventType> byCode = new HashMap<>();

  static {
        for(EventType e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  EventType(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static EventType getByCode(int code) {
    return byCode.get(code);
  }
}
