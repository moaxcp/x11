package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.KeySym;
import com.github.moaxcp.x11client.protocol.xproto.*;

import java.util.ArrayList;
import java.util.List;

import static com.github.moaxcp.x11client.protocol.KeySym.*;

public class KeyboardService {
  private GetKeyboardMappingReply keyboard;
  private final Setup setup;
  private final XProtocolService protocolService;

  public KeyboardService(XProtocolService protocolService) {
    this.protocolService = protocolService;
    setup = protocolService.getSetup();
    keyboard = getKeyboardMapping();
  }

  private GetKeyboardMappingReply getKeyboardMapping() {
    return protocolService.send(GetKeyboardMapping.builder()
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
  public KeySym keyCodeToKeySym(byte keyCode, short state) {
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
    return KeySym.getByCode(code);
  }

  public KeySym keyCodeToKeySym(KeyPressEvent event) {
    return keyCodeToKeySym(event.getDetail(), event.getState());
  }

  public KeySym keyCodeToKeySym(KeyReleaseEvent event) {
    return keyCodeToKeySym(event.getDetail(), event.getState());
  }

  /**
   * https://gitlab.freedesktop.org/xorg/lib/libxcb-keysyms/-/blob/master/keysyms/keysyms.c#L244
   * @param keySym
   * @return
   */
  public List<Byte> keySymToKeyCodes(KeySym keySym) {
    int min = Byte.toUnsignedInt(setup.getMinKeycode());
    int max = Byte.toUnsignedInt(setup.getMaxKeycode());
    List<Byte> result = new ArrayList<>();
    for(int i = min; i < max; i++) {
      for(int j = 0; j < keyboard.getKeysymsPerKeycode(); j++) {
        KeySym test = getKeySym(i, j);
        if(test == keySym) {
          result.add((byte) i);
          break;
        }
      }
    }
    return result;
  }

  public KeySym getKeySym(KeyPressEvent event, int col) {
    return getKeySym(event.getDetail(), col);
  }

  public KeySym getKeySym(KeyReleaseEvent event, int col) {
    return getKeySym(event.getDetail(), col);
  }

  public void refreshKeyboardMapping(MappingNotifyEvent event) {
    if(event.getRequest() == Mapping.KEYBOARD.getValue()) {
      keyboard = getKeyboardMapping();
    }
  }

  /**
   * <a href="https://gitlab.freedesktop.org/xorg/lib/libxcb-keysyms/-/blob/master/keysyms/keysyms.c#L189">...</a>
   * @param keyCode
   * @param col
   * @return
   */
  public KeySym getKeySym(int keyCode, int col) {
    int min = Byte.toUnsignedInt(setup.getMinKeycode());
    int max = Byte.toUnsignedInt(setup.getMaxKeycode());
    if(keyCode < min || keyCode > max) {
      return KeySym.NoSymbol;
    }
    int per = Byte.toUnsignedInt(keyboard.getKeysymsPerKeycode());
    if(col < 0 || (col >= per && col > 3)) {
      return KeySym.NoSymbol;
    }

    List<Integer> keysyms = keyboard.getKeysyms();

    int keysymsStart = (keyCode - min) * per;

    if(col < 4) {
      if(col > 1) {
        while(per > 2 && keysyms.get(keysymsStart + per - 1) == KeySym.NoSymbol.getValue()) {
          per--;
        }
        if(per < 3) {
          col -= 2;
        }
      }
      if(per <= (col | 1) || keysyms.get(keysymsStart + col | 1) == KeySym.NoSymbol.getValue()) {
        int[] caseSym = convertCase(keysyms.get(keysymsStart + col &~ 1));
        if((col & 1) == 0) {
          return KeySym.getByCode(caseSym[0]);
        } else if(caseSym[0] == caseSym[1]) {
          return KeySym.NoSymbol;
        } else {
          return KeySym.getByCode(caseSym[1]);
        }
      }
    }
    return KeySym.getByCode(keysyms.get(keysymsStart + col));
  }

  /**
   * from https://gitlab.freedesktop.org/xorg/lib/libxcb-keysyms/-/blob/master/keysyms/keysyms.c#L381
   * @param sym
   * @return
   */
  private int[] convertCase(int sym) {
    int lower = sym;
    int upper = sym;

    switch (sym >>> 8) {
      case 0: /* Latin 1 */
        if ((sym >= XK_A.getValue()) && (sym <= XK_Z.getValue()))
          lower += (XK_a.getValue() - XK_A.getValue());
        else if ((sym >= XK_a.getValue()) && (sym <= XK_z.getValue()))
          upper -= (XK_a.getValue() - XK_A.getValue());
        else if ((sym >= XK_Agrave.getValue()) && (sym <= XK_Odiaeresis.getValue()))
          lower += (XK_agrave.getValue() - XK_Agrave.getValue());
        else if ((sym >= XK_agrave.getValue()) && (sym <= XK_odiaeresis.getValue()))
          upper -= (XK_agrave.getValue() - XK_Agrave.getValue());
        else if ((sym >= XK_Ooblique.getValue()) && (sym <= XK_Thorn.getValue()))
          lower += (XK_oslash.getValue() - XK_Ooblique.getValue());
        else if ((sym >= XK_oslash.getValue()) && (sym <= XK_thorn.getValue()))
          upper -= (XK_oslash.getValue() - XK_Ooblique.getValue());
        break;
      case 1: /* Latin 2 */
        /* Assume the KeySym is a legal value (ignore discontinuities) */
        if (sym == XK_Aogonek.getValue())
          lower = XK_aogonek.getValue();
        else if (sym >= XK_Lstroke.getValue() && sym <= XK_Sacute.getValue())
          lower += (XK_lstroke.getValue() - XK_Lstroke.getValue());
        else if (sym >= XK_Scaron.getValue() && sym <= XK_Zacute.getValue())
          lower += (XK_scaron.getValue() - XK_Scaron.getValue());
        else if (sym >= XK_Zcaron.getValue() && sym <= XK_Zabovedot.getValue())
          lower += (XK_zcaron.getValue() - XK_Zcaron.getValue());
        else if (sym == XK_aogonek.getValue())
          upper = XK_Aogonek.getValue();
        else if (sym >= XK_lstroke.getValue() && sym <= XK_sacute.getValue())
          upper -= (XK_lstroke.getValue() - XK_Lstroke.getValue());
        else if (sym >= XK_scaron.getValue() && sym <= XK_zacute.getValue())
          upper -= (XK_scaron.getValue() - XK_Scaron.getValue());
        else if (sym >= XK_zcaron.getValue() && sym <= XK_zabovedot.getValue())
          upper -= (XK_zcaron.getValue() - XK_Zcaron.getValue());
        else if (sym >= XK_Racute.getValue() && sym <= XK_Tcedilla.getValue())
          lower += (XK_racute.getValue() - XK_Racute.getValue());
        else if (sym >= XK_racute.getValue() && sym <= XK_tcedilla.getValue())
          upper -= (XK_racute.getValue() - XK_Racute.getValue());
        break;
      case 2: /* Latin 3 */
        /* Assume the KeySym is a legal value (ignore discontinuities) */
        if (sym >= XK_Hstroke.getValue() && sym <= XK_Hcircumflex.getValue())
          lower += (XK_hstroke.getValue() - XK_Hstroke.getValue());
        else if (sym >= XK_Gbreve.getValue() && sym <= XK_Jcircumflex.getValue())
          lower += (XK_gbreve.getValue() - XK_Gbreve.getValue());
        else if (sym >= XK_hstroke.getValue() && sym <= XK_hcircumflex.getValue())
          upper -= (XK_hstroke.getValue() - XK_Hstroke.getValue());
        else if (sym >= XK_gbreve.getValue() && sym <= XK_jcircumflex.getValue())
          upper -= (XK_gbreve.getValue() - XK_Gbreve.getValue());
        else if (sym >= XK_Cabovedot.getValue() && sym <= XK_Scircumflex.getValue())
          lower += (XK_cabovedot.getValue() - XK_Cabovedot.getValue());
        else if (sym >= XK_cabovedot.getValue() && sym <= XK_scircumflex.getValue())
          upper -= (XK_cabovedot.getValue() - XK_Cabovedot.getValue());
        break;
      case 3: /* Latin 4 */
        /* Assume the KeySym is a legal value (ignore discontinuities) */
        if (sym >= XK_Rcedilla.getValue() && sym <= XK_Tslash.getValue())
          lower += (XK_rcedilla.getValue() - XK_Rcedilla.getValue());
        else if (sym >= XK_rcedilla.getValue() && sym <= XK_tslash.getValue())
          upper -= (XK_rcedilla.getValue() - XK_Rcedilla.getValue());
        else if (sym == XK_ENG.getValue())
          lower = XK_eng.getValue();
        else if (sym == XK_eng.getValue())
          upper = XK_ENG.getValue();
        else if (sym >= XK_Amacron.getValue() && sym <= XK_Umacron.getValue())
          lower += (XK_amacron.getValue() - XK_Amacron.getValue());
        else if (sym >= XK_amacron.getValue() && sym <= XK_umacron.getValue())
          upper -= (XK_amacron.getValue() - XK_Amacron.getValue());
        break;
      case 6: /* Cyrillic */
        /* Assume the KeySym is a legal value (ignore discontinuities) */
        if (sym >= XK_Serbian_DJE.getValue() && sym <= XK_Serbian_DZE.getValue())
          lower -= (XK_Serbian_DJE.getValue() - XK_Serbian_dje.getValue());
        else if (sym >= XK_Serbian_dje.getValue() && sym <= XK_Serbian_dze.getValue())
          upper += (XK_Serbian_DJE.getValue() - XK_Serbian_dje.getValue());
        else if (sym >= XK_Cyrillic_YU.getValue() && sym <= XK_Cyrillic_HARDSIGN.getValue())
          lower -= (XK_Cyrillic_YU.getValue() - XK_Cyrillic_yu.getValue());
        else if (sym >= XK_Cyrillic_yu.getValue() && sym <= XK_Cyrillic_hardsign.getValue())
          upper += (XK_Cyrillic_YU.getValue() - XK_Cyrillic_yu.getValue());
        break;
      case 7: /* Greek */
        /* Assume the KeySym is a legal value (ignore discontinuities) */
        if (sym >= XK_Greek_ALPHAaccent.getValue() && sym <= XK_Greek_OMEGAaccent.getValue())
          lower += (XK_Greek_alphaaccent.getValue() - XK_Greek_ALPHAaccent.getValue());
        else if (sym >= XK_Greek_alphaaccent.getValue() && sym <= XK_Greek_omegaaccent.getValue() &&
          sym != XK_Greek_iotaaccentdieresis.getValue() &&
          sym != XK_Greek_upsilonaccentdieresis.getValue())
          upper -= (XK_Greek_alphaaccent.getValue() - XK_Greek_ALPHAaccent.getValue());
        else if (sym >= XK_Greek_ALPHA.getValue() && sym <= XK_Greek_OMEGA.getValue())
          lower += (XK_Greek_alpha.getValue() - XK_Greek_ALPHA.getValue());
        else if (sym >= XK_Greek_alpha.getValue() && sym <= XK_Greek_omega.getValue() &&
          sym != XK_Greek_finalsmallsigma.getValue())
          upper -= (XK_Greek_alpha.getValue() - XK_Greek_ALPHA.getValue());
        break;
      case 0x14: /* Armenian */
        if (sym >= XK_Armenian_AYB.getValue() && sym <= XK_Armenian_fe.getValue()) {
          lower = sym | 1;
          upper = sym & ~1;
        }
        break;
    }

    return new int[] { lower, upper };
  }
}
