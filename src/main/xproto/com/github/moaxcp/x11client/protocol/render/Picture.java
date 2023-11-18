package com.github.moaxcp.x11client.protocol.render;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Picture implements IntValue {
  NONE(0);

  static final Map<Integer, Picture> byCode = new HashMap<>();

  static {
        for(Picture e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Picture(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Picture getByCode(int code) {
    return byCode.get(code);
  }
}
