package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ScreenSaver implements IntValue {
  RESET(0),

  ACTIVE(1);

  static final Map<Integer, ScreenSaver> byCode = new HashMap<>();

  static {
        for(ScreenSaver e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ScreenSaver(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ScreenSaver getByCode(int code) {
    return byCode.get(code);
  }
}
