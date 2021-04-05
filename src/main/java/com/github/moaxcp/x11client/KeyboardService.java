package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.xproto.GetKeyboardMapping;
import com.github.moaxcp.x11client.protocol.xproto.GetKeyboardMappingReply;
import com.github.moaxcp.x11client.protocol.xproto.KeyButMask;
import com.github.moaxcp.x11client.protocol.xproto.Setup;
import java.util.ArrayList;
import java.util.List;

public class KeyboardService {
  private final GetKeyboardMappingReply keyboard;
  private final Setup setup;

  public KeyboardService(XProtocolService protocolService) {
    setup = protocolService.getSetup();
    keyboard = protocolService.send(GetKeyboardMapping.builder()
      .firstKeycode(setup.getMinKeycode())
      .count((byte) (setup.getMaxKeycode() - setup.getMinKeycode() + 1))
      .build());
  }

  /**
   * See https://tronche.com/gui/x/xlib/input/keyboard-encoding.html
   * https://github.com/mirror/libX11/blob/caa71668af7fd3ebdd56353c8f0ab90824773969/src/xkb/XKBBind.c#L96
   * @param keyCode
   * @param state
   * @return
   */
  public int keyCodeToKeySym(byte keyCode, short state) {
    if(Byte.toUnsignedInt(keyCode) > Byte.toUnsignedInt(setup.getMaxKeycode())) {
      throw new IllegalArgumentException("keyCode \"" + keyCode + "\" is greater than maxKeycode \"" + setup.getMaxKeycode() + "\"");
    }

    int modifier = 0;

    if((state & KeyButMask.SHIFT.getValue()) != 0) {
      modifier = 1;
    } else if((state & KeyButMask.MOD5.getValue()) != 0) {
      modifier = 2;
    }

    int index = (Byte.toUnsignedInt(keyCode) - Byte.toUnsignedInt(setup.getMinKeycode())) * Byte.toUnsignedInt(keyboard.getKeysymsPerKeycode()) + modifier;
    int code = keyboard.getKeysyms().get(index);
    return code;
  }

  /**
   * https://gitlab.freedesktop.org/xorg/lib/libxcb-keysyms/-/blob/master/keysyms/keysyms.c#L244
   * @param keySym
   * @return
   */
  public List<Byte> keySymToKeyCode(int keySym) {
    //from https://gitlab.freedesktop.org/xorg/lib/libxcb-keysyms/-/blob/master/keysyms/keysyms.c
    byte min = setup.getMinKeycode();
    byte max = setup.getMaxKeycode();
    List<Byte> result = new ArrayList<>();
    for(int i = min; i < max; i++) {
      for(int j = 0; j < keyboard.getKeysymsPerKeycode(); j++) {
        int test = getKeySym(i, j);
        if(test == keySym) {
          result.add((byte) i);
          break;
        }
      }
    }
    return result;
  }

  /**
   * https://gitlab.freedesktop.org/xorg/lib/libxcb-keysyms/-/blob/master/keysyms/keysyms.c#L189
   * @param keyCode
   * @param col
   * @return
   */
  private int getKeySym(int keyCode, int col) {
    byte XCB_NO_SYMBOL = 0;
    byte min = setup.getMinKeycode();
    byte max = setup.getMaxKeycode();
    if(keyCode < min || keyCode > max) {
      return XCB_NO_SYMBOL;
    }
    byte per = keyboard.getKeysymsPerKeycode();
    if(col < 0 || (col >= per && col > 3)) {
      return XCB_NO_SYMBOL;
    }

    List<Integer> keysyms = keyboard.getKeysyms();

    int keysymsStart = (keyCode - min) * per;

    if(col < 4) {
      if(col > 1) {
        while(per > 2 && keysyms.get(keysymsStart + per - 1) == XCB_NO_SYMBOL) {
          per--;
        }
        if(per < 3) {
          col -= 2;
        }
      }
      if(per <= (col | 1) || keysyms.get(keysymsStart + col | 1) == XCB_NO_SYMBOL) {
        int[] caseSym = convertCase(keysyms.get(keysymsStart + col &~ 1));
        if((col & 1) == 0) {
          return caseSym[0];
        } else if(caseSym[0] == caseSym[1]) {
          return XCB_NO_SYMBOL;
        } else {
          return caseSym[1];
        }
      }
    }
    return keysyms.get(keysymsStart + col);
  }

  private int[] convertCase(int keysym) {
    int lower = keysym;
    int upper = keysym;

    //todo need to implement keysym
    switch(keysym >>> 8) {
      case 0:
        break;
    }
    return null;
  }
}
