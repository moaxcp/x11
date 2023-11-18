package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum SetOfGroup implements IntValue {
  GROUP1(0b1),

  GROUP2(0b10),

  GROUP3(0b100),

  GROUP4(0b1000);

  static final Map<Integer, SetOfGroup> byCode = new HashMap<>();

  static {
        for(SetOfGroup e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  SetOfGroup(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static SetOfGroup getByCode(int code) {
    return byCode.get(code);
  }
}
