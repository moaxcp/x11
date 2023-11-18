package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ConfigWindow implements IntValue {
  X(0b1),

  Y(0b10),

  WIDTH(0b100),

  HEIGHT(0b1000),

  BORDER_WIDTH(0b10000),

  SIBLING(0b100000),

  STACK_MODE(0b1000000);

  static final Map<Integer, ConfigWindow> byCode = new HashMap<>();

  static {
        for(ConfigWindow e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ConfigWindow(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ConfigWindow getByCode(int code) {
    return byCode.get(code);
  }
}
