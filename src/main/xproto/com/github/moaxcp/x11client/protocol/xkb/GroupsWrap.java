package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum GroupsWrap implements IntValue {
  WRAP_INTO_RANGE(0),

  CLAMP_INTO_RANGE(0b1000000),

  REDIRECT_INTO_RANGE(0b10000000);

  static final Map<Integer, GroupsWrap> byCode = new HashMap<>();

  static {
        for(GroupsWrap e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  GroupsWrap(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static GroupsWrap getByCode(int code) {
    return byCode.get(code);
  }
}
