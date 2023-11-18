package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ButtonMask implements IntValue {
  ONE(0b100000000),

  TWO(0b1000000000),

  THREE(0b10000000000),

  FOUR(0b100000000000),

  FIVE(0b1000000000000),

  ANY(0b1000000000000000);

  static final Map<Integer, ButtonMask> byCode = new HashMap<>();

  static {
        for(ButtonMask e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ButtonMask(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ButtonMask getByCode(int code) {
    return byCode.get(code);
  }
}
