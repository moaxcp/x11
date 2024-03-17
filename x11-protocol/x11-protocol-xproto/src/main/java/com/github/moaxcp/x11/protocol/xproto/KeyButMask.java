package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum KeyButMask implements IntValue {
  SHIFT(0b1),

  LOCK(0b10),

  CONTROL(0b100),

  MOD1(0b1000),

  MOD2(0b10000),

  MOD3(0b100000),

  MOD4(0b1000000),

  MOD5(0b10000000),

  BUTTON1(0b100000000),

  BUTTON2(0b1000000000),

  BUTTON3(0b10000000000),

  BUTTON4(0b100000000000),

  BUTTON5(0b1000000000000);

  static final Map<Integer, KeyButMask> byCode = new HashMap<>();

  static {
        for(KeyButMask e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  KeyButMask(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static KeyButMask getByCode(int code) {
    return byCode.get(code);
  }
}
