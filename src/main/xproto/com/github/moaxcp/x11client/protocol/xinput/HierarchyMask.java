package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum HierarchyMask implements IntValue {
  MASTER_ADDED(0b1),

  MASTER_REMOVED(0b10),

  SLAVE_ADDED(0b100),

  SLAVE_REMOVED(0b1000),

  SLAVE_ATTACHED(0b10000),

  SLAVE_DETACHED(0b100000),

  DEVICE_ENABLED(0b1000000),

  DEVICE_DISABLED(0b10000000);

  static final Map<Integer, HierarchyMask> byCode = new HashMap<>();

  static {
        for(HierarchyMask e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  HierarchyMask(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static HierarchyMask getByCode(int code) {
    return byCode.get(code);
  }
}
