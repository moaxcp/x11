package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ArcMode implements IntValue {
  CHORD(0),

  PIE_SLICE(1);

  static final Map<Integer, ArcMode> byCode = new HashMap<>();

  static {
        for(ArcMode e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ArcMode(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ArcMode getByCode(int code) {
    return byCode.get(code);
  }
}
