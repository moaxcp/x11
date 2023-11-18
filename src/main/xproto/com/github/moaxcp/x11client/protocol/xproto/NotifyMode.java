package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum NotifyMode implements IntValue {
  NORMAL(0),

  GRAB(1),

  UNGRAB(2),

  WHILE_GRABBED(3);

  static final Map<Integer, NotifyMode> byCode = new HashMap<>();

  static {
        for(NotifyMode e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  NotifyMode(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static NotifyMode getByCode(int code) {
    return byCode.get(code);
  }
}
