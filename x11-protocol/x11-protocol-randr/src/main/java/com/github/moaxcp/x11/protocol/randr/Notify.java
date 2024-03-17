package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Notify implements IntValue {
  CRTC_CHANGE(0),

  OUTPUT_CHANGE(1),

  OUTPUT_PROPERTY(2),

  PROVIDER_CHANGE(3),

  PROVIDER_PROPERTY(4),

  RESOURCE_CHANGE(5),

  LEASE(6);

  static final Map<Integer, Notify> byCode = new HashMap<>();

  static {
        for(Notify e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Notify(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Notify getByCode(int code) {
    return byCode.get(code);
  }
}
