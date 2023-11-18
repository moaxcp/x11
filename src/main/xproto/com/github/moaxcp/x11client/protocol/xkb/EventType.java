package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum EventType implements IntValue {
  NEW_KEYBOARD_NOTIFY(0b1),

  MAP_NOTIFY(0b10),

  STATE_NOTIFY(0b100),

  CONTROLS_NOTIFY(0b1000),

  INDICATOR_STATE_NOTIFY(0b10000),

  INDICATOR_MAP_NOTIFY(0b100000),

  NAMES_NOTIFY(0b1000000),

  COMPAT_MAP_NOTIFY(0b10000000),

  BELL_NOTIFY(0b100000000),

  ACTION_MESSAGE(0b1000000000),

  ACCESS_X_NOTIFY(0b10000000000),

  EXTENSION_DEVICE_NOTIFY(0b100000000000);

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
