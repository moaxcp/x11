package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum VMod implements IntValue {
  FIFTEEN(0b1000000000000000),

  FOURTEEN(0b100000000000000),

  THIRTEEN(0b10000000000000),

  TWELVE(0b1000000000000),

  ELEVEN(0b100000000000),

  TEN(0b10000000000),

  NINE(0b1000000000),

  EIGHT(0b100000000),

  SEVEN(0b10000000),

  SIX(0b1000000),

  FIVE(0b100000),

  FOUR(0b10000),

  THREE(0b1000),

  TWO(0b100),

  ONE(0b10),

  ZERO(0b1);

  static final Map<Integer, VMod> byCode = new HashMap<>();

  static {
        for(VMod e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  VMod(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static VMod getByCode(int code) {
    return byCode.get(code);
  }
}
