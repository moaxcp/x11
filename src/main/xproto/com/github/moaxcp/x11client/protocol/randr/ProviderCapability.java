package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum ProviderCapability implements IntValue {
  SOURCE_OUTPUT(0b1),

  SINK_OUTPUT(0b10),

  SOURCE_OFFLOAD(0b100),

  SINK_OFFLOAD(0b1000);

  static final Map<Integer, ProviderCapability> byCode = new HashMap<>();

  static {
        for(ProviderCapability e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  ProviderCapability(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static ProviderCapability getByCode(int code) {
    return byCode.get(code);
  }
}
