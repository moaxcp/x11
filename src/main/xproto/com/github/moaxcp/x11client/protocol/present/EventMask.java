package com.github.moaxcp.x11client.protocol.present;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum EventMask implements IntValue {
  NO_EVENT(0),

  CONFIGURE_NOTIFY(0b1),

  COMPLETE_NOTIFY(0b10),

  IDLE_NOTIFY(0b100),

  REDIRECT_NOTIFY(0b1000);

  static final Map<Integer, EventMask> byCode = new HashMap<>();

  static {
        for(EventMask e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  EventMask(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static EventMask getByCode(int code) {
    return byCode.get(code);
  }
}
