package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum SetMode implements IntValue {
  INSERT(0),

  DELETE(1);

  static final Map<Integer, SetMode> byCode = new HashMap<>();

  static {
        for(SetMode e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  SetMode(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static SetMode getByCode(int code) {
    return byCode.get(code);
  }
}
