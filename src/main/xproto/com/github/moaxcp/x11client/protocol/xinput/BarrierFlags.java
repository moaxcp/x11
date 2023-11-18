package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum BarrierFlags implements IntValue {
  POINTER_RELEASED(0b1),

  DEVICE_IS_GRABBED(0b10);

  static final Map<Integer, BarrierFlags> byCode = new HashMap<>();

  static {
        for(BarrierFlags e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  BarrierFlags(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static BarrierFlags getByCode(int code) {
    return byCode.get(code);
  }
}
