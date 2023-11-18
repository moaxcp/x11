package com.github.moaxcp.x11client.protocol.screensaver;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Event implements IntValue {
  NOTIFY_MASK(0b1),

  CYCLE_MASK(0b10);

  static final Map<Integer, Event> byCode = new HashMap<>();

  static {
        for(Event e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Event(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Event getByCode(int code) {
    return byCode.get(code);
  }
}
