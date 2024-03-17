package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ButtonIndex implements IntValue {
  ANY(0),

  ONE(1),

  TWO(2),

  THREE(3),

  FOUR(4),

  FIVE(5);

  static final Map<Integer, ButtonIndex> byCode = new HashMap<>();

  static {
        for(ButtonIndex e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ButtonIndex(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ButtonIndex getByCode(int code) {
    return byCode.get(code);
  }
}
