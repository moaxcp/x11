package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum SubwindowMode implements IntValue {
  CLIP_BY_CHILDREN(0),

  INCLUDE_INFERIORS(1);

  static final Map<Integer, SubwindowMode> byCode = new HashMap<>();

  static {
        for(SubwindowMode e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  SubwindowMode(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static SubwindowMode getByCode(int code) {
    return byCode.get(code);
  }
}
