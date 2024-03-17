package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum PropagateMode implements IntValue {
  ADD_TO_LIST(0),

  DELETE_FROM_LIST(1);

  static final Map<Integer, PropagateMode> byCode = new HashMap<>();

  static {
        for(PropagateMode e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  PropagateMode(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static PropagateMode getByCode(int code) {
    return byCode.get(code);
  }
}
