package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ChangeMode implements IntValue {
  ATTACH(1),

  FLOAT(2);

  static final Map<Integer, ChangeMode> byCode = new HashMap<>();

  static {
        for(ChangeMode e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ChangeMode(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ChangeMode getByCode(int code) {
    return byCode.get(code);
  }
}
