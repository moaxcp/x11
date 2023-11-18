package com.github.moaxcp.x11client.protocol.xfixes;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum SelectionEvent implements IntValue {
  SET_SELECTION_OWNER(0),

  SELECTION_WINDOW_DESTROY(1),

  SELECTION_CLIENT_CLOSE(2);

  static final Map<Integer, SelectionEvent> byCode = new HashMap<>();

  static {
        for(SelectionEvent e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  SelectionEvent(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static SelectionEvent getByCode(int code) {
    return byCode.get(code);
  }
}
