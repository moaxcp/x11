package com.github.moaxcp.x11client.protocol.dpms;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum DPMSMode implements IntValue {
  ON(0),

  STANDBY(1),

  SUSPEND(2),

  OFF(3);

  static final Map<Integer, DPMSMode> byCode = new HashMap<>();

  static {
        for(DPMSMode e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  DPMSMode(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static DPMSMode getByCode(int code) {
    return byCode.get(code);
  }
}
