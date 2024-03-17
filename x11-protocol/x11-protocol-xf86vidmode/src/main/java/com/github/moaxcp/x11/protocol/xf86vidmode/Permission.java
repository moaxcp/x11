package com.github.moaxcp.x11.protocol.xf86vidmode;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Permission implements IntValue {
  READ(0b1),

  WRITE(0b10);

  static final Map<Integer, Permission> byCode = new HashMap<>();

  static {
        for(Permission e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Permission(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Permission getByCode(int code) {
    return byCode.get(code);
  }
}
