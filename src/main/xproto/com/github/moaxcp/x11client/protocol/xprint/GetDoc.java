package com.github.moaxcp.x11client.protocol.xprint;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum GetDoc implements IntValue {
  FINISHED(0),

  SECOND_CONSUMER(1);

  static final Map<Integer, GetDoc> byCode = new HashMap<>();

  static {
        for(GetDoc e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  GetDoc(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static GetDoc getByCode(int code) {
    return byCode.get(code);
  }
}
