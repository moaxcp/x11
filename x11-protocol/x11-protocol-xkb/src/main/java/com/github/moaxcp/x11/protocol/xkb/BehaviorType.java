package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum BehaviorType implements IntValue {
  DEFAULT(0),

  LOCK(1),

  RADIO_GROUP(2),

  OVERLAY1(3),

  OVERLAY2(4),

  PERMAMENT_LOCK(129),

  PERMAMENT_RADIO_GROUP(130),

  PERMAMENT_OVERLAY1(131),

  PERMAMENT_OVERLAY2(132);

  static final Map<Integer, BehaviorType> byCode = new HashMap<>();

  static {
        for(BehaviorType e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  BehaviorType(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static BehaviorType getByCode(int code) {
    return byCode.get(code);
  }
}
