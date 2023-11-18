package com.github.moaxcp.x11client.protocol.xevie;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Datatype implements IntValue {
  UNMODIFIED(0),

  MODIFIED(1);

  static final Map<Integer, Datatype> byCode = new HashMap<>();

  static {
        for(Datatype e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Datatype(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Datatype getByCode(int code) {
    return byCode.get(code);
  }
}
