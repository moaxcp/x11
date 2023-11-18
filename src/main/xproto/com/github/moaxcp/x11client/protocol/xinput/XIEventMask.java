package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum XIEventMask implements IntValue {
  DEVICE_CHANGED(0b10),

  KEY_PRESS(0b100),

  KEY_RELEASE(0b1000),

  BUTTON_PRESS(0b10000),

  BUTTON_RELEASE(0b100000),

  MOTION(0b1000000),

  ENTER(0b10000000),

  LEAVE(0b100000000),

  FOCUS_IN(0b1000000000),

  FOCUS_OUT(0b10000000000),

  HIERARCHY(0b100000000000),

  PROPERTY(0b1000000000000),

  RAW_KEY_PRESS(0b10000000000000),

  RAW_KEY_RELEASE(0b100000000000000),

  RAW_BUTTON_PRESS(0b1000000000000000),

  RAW_BUTTON_RELEASE(0b10000000000000000),

  RAW_MOTION(0b100000000000000000),

  TOUCH_BEGIN(0b1000000000000000000),

  TOUCH_UPDATE(0b10000000000000000000),

  TOUCH_END(0b100000000000000000000),

  TOUCH_OWNERSHIP(0b1000000000000000000000),

  RAW_TOUCH_BEGIN(0b10000000000000000000000),

  RAW_TOUCH_UPDATE(0b100000000000000000000000),

  RAW_TOUCH_END(0b1000000000000000000000000),

  BARRIER_HIT(0b10000000000000000000000000),

  BARRIER_LEAVE(0b100000000000000000000000000);

  static final Map<Integer, XIEventMask> byCode = new HashMap<>();

  static {
        for(XIEventMask e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  XIEventMask(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static XIEventMask getByCode(int code) {
    return byCode.get(code);
  }
}
