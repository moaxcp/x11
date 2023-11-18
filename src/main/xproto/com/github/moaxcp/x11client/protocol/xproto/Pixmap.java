package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Pixmap implements IntValue {
  NONE(0);

  static final Map<Integer, Pixmap> byCode = new HashMap<>();

  static {
        for(Pixmap e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Pixmap(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Pixmap getByCode(int code) {
    return byCode.get(code);
  }
}
