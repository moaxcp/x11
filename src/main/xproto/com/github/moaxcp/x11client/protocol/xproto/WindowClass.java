package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum WindowClass implements IntValue {
  COPY_FROM_PARENT(0),

  INPUT_OUTPUT(1),

  INPUT_ONLY(2);

  static final Map<Integer, WindowClass> byCode = new HashMap<>();

  static {
        for(WindowClass e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  WindowClass(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static WindowClass getByCode(int code) {
    return byCode.get(code);
  }
}
