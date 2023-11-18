package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum SAType implements IntValue {
  NO_ACTION(0),

  SET_MODS(1),

  LATCH_MODS(2),

  LOCK_MODS(3),

  SET_GROUP(4),

  LATCH_GROUP(5),

  LOCK_GROUP(6),

  MOVE_PTR(7),

  PTR_BTN(8),

  LOCK_PTR_BTN(9),

  SET_PTR_DFLT(10),

  I_S_O_LOCK(11),

  TERMINATE(12),

  SWITCH_SCREEN(13),

  SET_CONTROLS(14),

  LOCK_CONTROLS(15),

  ACTION_MESSAGE(16),

  REDIRECT_KEY(17),

  DEVICE_BTN(18),

  LOCK_DEVICE_BTN(19),

  DEVICE_VALUATOR(20);

  static final Map<Integer, SAType> byCode = new HashMap<>();

  static {
        for(SAType e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  SAType(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static SAType getByCode(int code) {
    return byCode.get(code);
  }
}
