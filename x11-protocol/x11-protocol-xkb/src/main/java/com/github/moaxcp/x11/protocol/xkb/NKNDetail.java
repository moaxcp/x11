package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum NKNDetail implements IntValue {
  KEYCODES(0b1),

  GEOMETRY(0b10),

  DEVICE_I_D(0b100);

  static final Map<Integer, NKNDetail> byCode = new HashMap<>();

  static {
        for(NKNDetail e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  NKNDetail(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static NKNDetail getByCode(int code) {
    return byCode.get(code);
  }
}
