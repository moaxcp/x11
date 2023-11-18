package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum SetConfig implements IntValue {
  SUCCESS(0),

  INVALID_CONFIG_TIME(1),

  INVALID_TIME(2),

  FAILED(3);

  static final Map<Integer, SetConfig> byCode = new HashMap<>();

  static {
        for(SetConfig e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  SetConfig(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static SetConfig getByCode(int code) {
    return byCode.get(code);
  }
}
