package com.github.moaxcp.x11.protocol.xprint;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Detail implements IntValue {
  START_JOB_NOTIFY(1),

  END_JOB_NOTIFY(2),

  START_DOC_NOTIFY(3),

  END_DOC_NOTIFY(4),

  START_PAGE_NOTIFY(5),

  END_PAGE_NOTIFY(6);

  static final Map<Integer, Detail> byCode = new HashMap<>();

  static {
        for(Detail e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Detail(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Detail getByCode(int code) {
    return byCode.get(code);
  }
}
