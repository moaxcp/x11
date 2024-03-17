package com.github.moaxcp.x11.protocol.xprint;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum Attr implements IntValue {
  JOB_ATTR(1),

  DOC_ATTR(2),

  PAGE_ATTR(3),

  PRINTER_ATTR(4),

  SERVER_ATTR(5),

  MEDIUM_ATTR(6),

  SPOOLER_ATTR(7);

  static final Map<Integer, Attr> byCode = new HashMap<>();

  static {
        for(Attr e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  Attr(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Attr getByCode(int code) {
    return byCode.get(code);
  }
}
