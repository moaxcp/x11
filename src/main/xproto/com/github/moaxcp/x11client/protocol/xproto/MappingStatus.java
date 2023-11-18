package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum MappingStatus implements IntValue {
  SUCCESS(0),

  BUSY(1),

  FAILURE(2);

  static final Map<Integer, MappingStatus> byCode = new HashMap<>();

  static {
        for(MappingStatus e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  MappingStatus(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static MappingStatus getByCode(int code) {
    return byCode.get(code);
  }
}
