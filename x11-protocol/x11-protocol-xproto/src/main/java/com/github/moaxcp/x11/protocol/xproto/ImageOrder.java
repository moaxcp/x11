package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ImageOrder implements IntValue {
  L_S_B_FIRST(0),

  M_S_B_FIRST(1);

  static final Map<Integer, ImageOrder> byCode = new HashMap<>();

  static {
        for(ImageOrder e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ImageOrder(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ImageOrder getByCode(int code) {
    return byCode.get(code);
  }
}
