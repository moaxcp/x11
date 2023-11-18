package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Rotation implements IntValue {
  ROTATE0(0b1),

  ROTATE90(0b10),

  ROTATE180(0b100),

  ROTATE270(0b1000),

  REFLECT_X(0b10000),

  REFLECT_Y(0b100000);

  static final Map<Integer, Rotation> byCode = new HashMap<>();

  static {
        for(Rotation e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Rotation(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Rotation getByCode(int code) {
    return byCode.get(code);
  }
}
