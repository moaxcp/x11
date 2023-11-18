package com.github.moaxcp.x11client.protocol.present;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Option implements IntValue {
  NONE(0),

  ASYNC(0b1),

  COPY(0b10),

  UST(0b100),

  SUBOPTIMAL(0b1000);

  static final Map<Integer, Option> byCode = new HashMap<>();

  static {
        for(Option e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Option(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Option getByCode(int code) {
    return byCode.get(code);
  }
}
