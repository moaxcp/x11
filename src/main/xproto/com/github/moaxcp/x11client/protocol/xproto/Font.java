package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Font implements IntValue {
  NONE(0);

  static final Map<Integer, Font> byCode = new HashMap<>();

  static {
        for(Font e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Font(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Font getByCode(int code) {
    return byCode.get(code);
  }
}
