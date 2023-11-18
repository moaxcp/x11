package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum NotifyMask implements IntValue {
  SCREEN_CHANGE(0b1),

  CRTC_CHANGE(0b10),

  OUTPUT_CHANGE(0b100),

  OUTPUT_PROPERTY(0b1000),

  PROVIDER_CHANGE(0b10000),

  PROVIDER_PROPERTY(0b100000),

  RESOURCE_CHANGE(0b1000000),

  LEASE(0b10000000);

  static final Map<Integer, NotifyMask> byCode = new HashMap<>();

  static {
        for(NotifyMask e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  NotifyMask(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static NotifyMask getByCode(int code) {
    return byCode.get(code);
  }
}
