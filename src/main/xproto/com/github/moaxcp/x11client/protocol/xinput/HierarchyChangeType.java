package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum HierarchyChangeType implements IntValue {
  ADD_MASTER(1),

  REMOVE_MASTER(2),

  ATTACH_SLAVE(3),

  DETACH_SLAVE(4);

  static final Map<Integer, HierarchyChangeType> byCode = new HashMap<>();

  static {
        for(HierarchyChangeType e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  HierarchyChangeType(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static HierarchyChangeType getByCode(int code) {
    return byCode.get(code);
  }
}
