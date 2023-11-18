package com.github.moaxcp.x11client.protocol.glx;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Rm implements IntValue {
  GL_RENDER(7168),

  GL_FEEDBACK(7169),

  GL_SELECT(7170);

  static final Map<Integer, Rm> byCode = new HashMap<>();

  static {
        for(Rm e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Rm(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Rm getByCode(int code) {
    return byCode.get(code);
  }
}
