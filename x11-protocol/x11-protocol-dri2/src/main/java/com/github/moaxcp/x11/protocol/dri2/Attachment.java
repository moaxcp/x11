package com.github.moaxcp.x11.protocol.dri2;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Attachment implements IntValue {
  BUFFER_FRONT_LEFT(0),

  BUFFER_BACK_LEFT(1),

  BUFFER_FRONT_RIGHT(2),

  BUFFER_BACK_RIGHT(3),

  BUFFER_DEPTH(4),

  BUFFER_STENCIL(5),

  BUFFER_ACCUM(6),

  BUFFER_FAKE_FRONT_LEFT(7),

  BUFFER_FAKE_FRONT_RIGHT(8),

  BUFFER_DEPTH_STENCIL(9),

  BUFFER_HIZ(10);

  static final Map<Integer, Attachment> byCode = new HashMap<>();

  static {
        for(Attachment e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Attachment(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Attachment getByCode(int code) {
    return byCode.get(code);
  }
}
