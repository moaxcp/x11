package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum MapState implements IntValue {
  UNMAPPED(0),

  UNVIEWABLE(1),

  VIEWABLE(2);

  static final Map<Integer, MapState> byCode = new HashMap<>();

  static {
        for(MapState e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  MapState(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static MapState getByCode(int code) {
    return byCode.get(code);
  }
}
