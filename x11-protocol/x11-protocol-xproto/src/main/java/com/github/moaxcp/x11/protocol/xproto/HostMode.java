package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum HostMode implements IntValue {
  INSERT(0),

  DELETE(1);

  static final Map<Integer, HostMode> byCode = new HashMap<>();

  static {
        for(HostMode e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  HostMode(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static HostMode getByCode(int code) {
    return byCode.get(code);
  }
}
