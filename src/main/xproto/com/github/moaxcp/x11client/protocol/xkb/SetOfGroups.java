package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum SetOfGroups implements IntValue {
  ANY(0b10000000);

  static final Map<Integer, SetOfGroups> byCode = new HashMap<>();

  static {
        for(SetOfGroups e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  SetOfGroups(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static SetOfGroups getByCode(int code) {
    return byCode.get(code);
  }
}
