package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum EventMask implements IntValue {
  NO_EVENT(0),

  KEY_PRESS(0b1),

  KEY_RELEASE(0b10),

  BUTTON_PRESS(0b100),

  BUTTON_RELEASE(0b1000),

  ENTER_WINDOW(0b10000),

  LEAVE_WINDOW(0b100000),

  POINTER_MOTION(0b1000000),

  POINTER_MOTION_HINT(0b10000000),

  BUTTON1_MOTION(0b100000000),

  BUTTON2_MOTION(0b1000000000),

  BUTTON3_MOTION(0b10000000000),

  BUTTON4_MOTION(0b100000000000),

  BUTTON5_MOTION(0b1000000000000),

  BUTTON_MOTION(0b10000000000000),

  KEYMAP_STATE(0b100000000000000),

  EXPOSURE(0b1000000000000000),

  VISIBILITY_CHANGE(0b10000000000000000),

  STRUCTURE_NOTIFY(0b100000000000000000),

  RESIZE_REDIRECT(0b1000000000000000000),

  SUBSTRUCTURE_NOTIFY(0b10000000000000000000),

  SUBSTRUCTURE_REDIRECT(0b100000000000000000000),

  FOCUS_CHANGE(0b1000000000000000000000),

  PROPERTY_CHANGE(0b10000000000000000000000),

  COLOR_MAP_CHANGE(0b100000000000000000000000),

  OWNER_GRAB_BUTTON(0b1000000000000000000000000);

  static final Map<Integer, EventMask> byCode = new HashMap<>();

  static {
        for(EventMask e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  EventMask(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static EventMask getByCode(int code) {
    return byCode.get(code);
  }
}
