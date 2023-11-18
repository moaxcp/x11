package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum SendEventDest implements IntValue {
  POINTER_WINDOW(0),

  ITEM_FOCUS(1);

  static final Map<Integer, SendEventDest> byCode = new HashMap<>();

  static {
        for(SendEventDest e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  SendEventDest(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static SendEventDest getByCode(int code) {
    return byCode.get(code);
  }
}
