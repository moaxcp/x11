package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ClassesReportedMask implements IntValue {
  OUT_OF_PROXIMITY(0b10000000),

  DEVICE_MODE_ABSOLUTE(0b1000000),

  REPORTING_VALUATORS(0b100),

  REPORTING_BUTTONS(0b10),

  REPORTING_KEYS(0b1);

  static final Map<Integer, ClassesReportedMask> byCode = new HashMap<>();

  static {
        for(ClassesReportedMask e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ClassesReportedMask(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ClassesReportedMask getByCode(int code) {
    return byCode.get(code);
  }
}
