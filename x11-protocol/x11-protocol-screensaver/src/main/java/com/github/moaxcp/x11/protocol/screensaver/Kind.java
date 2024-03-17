package com.github.moaxcp.x11.protocol.screensaver;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Kind implements IntValue {
  BLANKED(0),

  INTERNAL(1),

  EXTERNAL(2);

  static final Map<Integer, Kind> byCode = new HashMap<>();

  static {
        for(Kind e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Kind(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Kind getByCode(int code) {
    return byCode.get(code);
  }
}
