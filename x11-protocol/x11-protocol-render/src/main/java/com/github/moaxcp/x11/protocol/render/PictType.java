package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum PictType implements IntValue {
  INDEXED(0),

  DIRECT(1);

  static final Map<Integer, PictType> byCode = new HashMap<>();

  static {
        for(PictType e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  PictType(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static PictType getByCode(int code) {
    return byCode.get(code);
  }
}
