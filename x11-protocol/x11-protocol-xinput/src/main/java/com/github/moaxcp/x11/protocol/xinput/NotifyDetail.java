package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum NotifyDetail implements IntValue {
  ANCESTOR(0),

  VIRTUAL(1),

  INFERIOR(2),

  NONLINEAR(3),

  NONLINEAR_VIRTUAL(4),

  POINTER(5),

  POINTER_ROOT(6),

  NONE(7);

  static final Map<Integer, NotifyDetail> byCode = new HashMap<>();

  static {
        for(NotifyDetail e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  NotifyDetail(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static NotifyDetail getByCode(int code) {
    return byCode.get(code);
  }
}
