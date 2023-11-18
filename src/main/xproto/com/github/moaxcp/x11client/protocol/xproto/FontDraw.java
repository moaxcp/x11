package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum FontDraw implements IntValue {
  LEFT_TO_RIGHT(0),

  RIGHT_TO_LEFT(1);

  static final Map<Integer, FontDraw> byCode = new HashMap<>();

  static {
        for(FontDraw e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  FontDraw(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static FontDraw getByCode(int code) {
    return byCode.get(code);
  }
}
