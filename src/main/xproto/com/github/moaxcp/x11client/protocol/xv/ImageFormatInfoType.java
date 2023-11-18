package com.github.moaxcp.x11client.protocol.xv;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ImageFormatInfoType implements IntValue {
  RGB(0),

  YUV(1);

  static final Map<Integer, ImageFormatInfoType> byCode = new HashMap<>();

  static {
        for(ImageFormatInfoType e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ImageFormatInfoType(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ImageFormatInfoType getByCode(int code) {
    return byCode.get(code);
  }
}
