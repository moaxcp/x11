package com.github.moaxcp.x11client.protocol.xtest;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Cursor implements IntValue {
  NONE(0),

  CURRENT(1);

  static final Map<Integer, Cursor> byCode = new HashMap<>();

  static {
        for(Cursor e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Cursor(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Cursor getByCode(int code) {
    return byCode.get(code);
  }
}
