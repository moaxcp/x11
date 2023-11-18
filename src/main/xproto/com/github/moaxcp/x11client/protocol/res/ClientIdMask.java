package com.github.moaxcp.x11client.protocol.res;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ClientIdMask implements IntValue {
  CLIENT_X_I_D(0b1),

  LOCAL_CLIENT_P_I_D(0b10);

  static final Map<Integer, ClientIdMask> byCode = new HashMap<>();

  static {
        for(ClientIdMask e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ClientIdMask(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ClientIdMask getByCode(int code) {
    return byCode.get(code);
  }
}
