package com.github.moaxcp.x11client.protocol.xfixes;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum SaveSetMode implements IntValue {
  INSERT(0),

  DELETE(1);

  static final Map<Integer, SaveSetMode> byCode = new HashMap<>();

  static {
        for(SaveSetMode e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  SaveSetMode(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static SaveSetMode getByCode(int code) {
    return byCode.get(code);
  }
}
