package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum BackingStore implements IntValue {
  NOT_USEFUL(0),

  WHEN_MAPPED(1),

  ALWAYS(2);

  static final Map<Integer, BackingStore> byCode = new HashMap<>();

  static {
        for(BackingStore e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  BackingStore(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static BackingStore getByCode(int code) {
    return byCode.get(code);
  }
}
