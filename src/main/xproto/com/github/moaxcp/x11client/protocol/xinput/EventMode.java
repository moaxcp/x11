package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum EventMode implements IntValue {
  ASYNC_DEVICE(0),

  SYNC_DEVICE(1),

  REPLAY_DEVICE(2),

  ASYNC_PAIRED_DEVICE(3),

  ASYNC_PAIR(4),

  SYNC_PAIR(5),

  ACCEPT_TOUCH(6),

  REJECT_TOUCH(7);

  static final Map<Integer, EventMode> byCode = new HashMap<>();

  static {
        for(EventMode e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  EventMode(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static EventMode getByCode(int code) {
    return byCode.get(code);
  }
}
