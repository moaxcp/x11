package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ImageFormat implements IntValue {
  X_Y_BITMAP(0),

  X_Y_PIXMAP(1),

  Z_PIXMAP(2);

  static final Map<Integer, ImageFormat> byCode = new HashMap<>();

  static {
        for(ImageFormat e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ImageFormat(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ImageFormat getByCode(int code) {
    return byCode.get(code);
  }
}
