package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum GrabMode22 implements IntValue {
  SYNC(0),

  ASYNC(1),

  TOUCH(2);

  static final Map<Integer, GrabMode22> byCode = new HashMap<>();

  static {
        for(GrabMode22 e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  GrabMode22(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static GrabMode22 getByCode(int code) {
    return byCode.get(code);
  }
}
