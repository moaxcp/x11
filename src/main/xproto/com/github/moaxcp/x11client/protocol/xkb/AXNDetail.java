package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum AXNDetail implements IntValue {
  S_K_PRESS(0b1),

  S_K_ACCEPT(0b10),

  S_K_REJECT(0b100),

  S_K_RELEASE(0b1000),

  B_K_ACCEPT(0b10000),

  B_K_REJECT(0b100000),

  A_X_K_WARNING(0b1000000);

  static final Map<Integer, AXNDetail> byCode = new HashMap<>();

  static {
        for(AXNDetail e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  AXNDetail(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static AXNDetail getByCode(int code) {
    return byCode.get(code);
  }
}
