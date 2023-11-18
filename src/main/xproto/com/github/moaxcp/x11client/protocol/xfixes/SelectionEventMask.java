package com.github.moaxcp.x11client.protocol.xfixes;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum SelectionEventMask implements IntValue {
  SET_SELECTION_OWNER(0b1),

  SELECTION_WINDOW_DESTROY(0b10),

  SELECTION_CLIENT_CLOSE(0b100);

  static final Map<Integer, SelectionEventMask> byCode = new HashMap<>();

  static {
        for(SelectionEventMask e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  SelectionEventMask(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static SelectionEventMask getByCode(int code) {
    return byCode.get(code);
  }
}
