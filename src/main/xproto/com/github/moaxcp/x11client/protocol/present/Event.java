package com.github.moaxcp.x11client.protocol.present;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Event implements IntValue {
  CONFIGURE_NOTIFY(0),

  COMPLETE_NOTIFY(1),

  IDLE_NOTIFY(2),

  REDIRECT_NOTIFY(3);

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
