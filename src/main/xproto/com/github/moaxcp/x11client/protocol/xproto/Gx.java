package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Gx implements IntValue {
  CLEAR(0),

  AND(1),

  AND_REVERSE(2),

  COPY(3),

  AND_INVERTED(4),

  NOOP(5),

  XOR(6),

  OR(7),

  NOR(8),

  EQUIV(9),

  INVERT(10),

  OR_REVERSE(11),

  COPY_INVERTED(12),

  OR_INVERTED(13),

  NAND(14),

  SET(15);

  static final Map<Integer, Gx> byCode = new HashMap<>();

  static {
        for(Gx e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Gx(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Gx getByCode(int code) {
    return byCode.get(code);
  }
}
