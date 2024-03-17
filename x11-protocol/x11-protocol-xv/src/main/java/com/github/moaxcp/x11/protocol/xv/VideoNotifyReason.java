package com.github.moaxcp.x11.protocol.xv;

import com.github.moaxcp.x11.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum VideoNotifyReason implements IntValue {
  STARTED(0),

  STOPPED(1),

  BUSY(2),

  PREEMPTED(3),

  HARD_ERROR(4);

  static final Map<Integer, VideoNotifyReason> byCode = new HashMap<>();

  static {
        for(VideoNotifyReason e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  VideoNotifyReason(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static VideoNotifyReason getByCode(int code) {
    return byCode.get(code);
  }
}
