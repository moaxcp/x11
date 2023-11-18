package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Connection implements IntValue {
  CONNECTED(0),

  DISCONNECTED(1),

  UNKNOWN(2);

  static final Map<Integer, Connection> byCode = new HashMap<>();

  static {
        for(Connection e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Connection(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Connection getByCode(int code) {
    return byCode.get(code);
  }
}
