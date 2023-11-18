package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Cw implements IntValue {
  BACK_PIXMAP(0b1),

  BACK_PIXEL(0b10),

  BORDER_PIXMAP(0b100),

  BORDER_PIXEL(0b1000),

  BIT_GRAVITY(0b10000),

  WIN_GRAVITY(0b100000),

  BACKING_STORE(0b1000000),

  BACKING_PLANES(0b10000000),

  BACKING_PIXEL(0b100000000),

  OVERRIDE_REDIRECT(0b1000000000),

  SAVE_UNDER(0b10000000000),

  EVENT_MASK(0b100000000000),

  DONT_PROPAGATE(0b1000000000000),

  COLORMAP(0b10000000000000),

  CURSOR(0b100000000000000);

  static final Map<Integer, Cw> byCode = new HashMap<>();

  static {
        for(Cw e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Cw(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Cw getByCode(int code) {
    return byCode.get(code);
  }
}
