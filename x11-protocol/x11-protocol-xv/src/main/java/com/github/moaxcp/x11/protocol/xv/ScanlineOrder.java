package com.github.moaxcp.x11.protocol.xv;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ScanlineOrder implements IntValue {
  TOP_TO_BOTTOM(0),

  BOTTOM_TO_TOP(1);

  static final Map<Integer, ScanlineOrder> byCode = new HashMap<>();

  static {
        for(ScanlineOrder e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ScanlineOrder(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ScanlineOrder getByCode(int code) {
    return byCode.get(code);
  }
}
