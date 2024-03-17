package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Allow implements IntValue {
  ASYNC_POINTER(0),

  SYNC_POINTER(1),

  REPLAY_POINTER(2),

  ASYNC_KEYBOARD(3),

  SYNC_KEYBOARD(4),

  REPLAY_KEYBOARD(5),

  ASYNC_BOTH(6),

  SYNC_BOTH(7);

  static final Map<Integer, Allow> byCode = new HashMap<>();

  static {
        for(Allow e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Allow(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Allow getByCode(int code) {
    return byCode.get(code);
  }
}
