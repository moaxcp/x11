package com.github.moaxcp.x11client.protocol.xfixes;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum SaveSetTarget implements IntValue {
  NEAREST(0),

  ROOT(1);

  static final Map<Integer, SaveSetTarget> byCode = new HashMap<>();

  static {
        for(SaveSetTarget e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  SaveSetTarget(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static SaveSetTarget getByCode(int code) {
    return byCode.get(code);
  }
}
