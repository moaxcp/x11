package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Id implements IntValue {
  USE_CORE_KBD(256),

  USE_CORE_PTR(512),

  DFLT_X_I_CLASS(768),

  DFLT_X_I_ID(1024),

  ALL_X_I_CLASS(1280),

  ALL_X_I_ID(1536),

  X_I_NONE(65280);

  static final Map<Integer, Id> byCode = new HashMap<>();

  static {
        for(Id e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Id(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Id getByCode(int code) {
    return byCode.get(code);
  }
}
