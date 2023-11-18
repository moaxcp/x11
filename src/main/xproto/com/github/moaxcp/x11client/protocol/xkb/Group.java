package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Group implements IntValue {
  ONE(0),

  TWO(1),

  THREE(2),

  FOUR(3);

  static final Map<Integer, Group> byCode = new HashMap<>();

  static {
        for(Group e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Group(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Group getByCode(int code) {
    return byCode.get(code);
  }
}
