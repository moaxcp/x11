package com.github.moaxcp.x11client.protocol.glx;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Pbcdt implements IntValue {
  WINDOW(32793),

  PBUFFER(32794);

  static final Map<Integer, Pbcdt> byCode = new HashMap<>();

  static {
        for(Pbcdt e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Pbcdt(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Pbcdt getByCode(int code) {
    return byCode.get(code);
  }
}
