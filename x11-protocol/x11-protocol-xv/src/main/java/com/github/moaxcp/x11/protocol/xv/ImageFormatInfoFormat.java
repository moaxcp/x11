package com.github.moaxcp.x11.protocol.xv;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ImageFormatInfoFormat implements IntValue {
  PACKED(0),

  PLANAR(1);

  static final Map<Integer, ImageFormatInfoFormat> byCode = new HashMap<>();

  static {
        for(ImageFormatInfoFormat e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ImageFormatInfoFormat(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ImageFormatInfoFormat getByCode(int code) {
    return byCode.get(code);
  }
}
