package com.github.moaxcp.x11client.protocol;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.github.moaxcp.x11client.protocol.KeySystem.NONE;
import static com.github.moaxcp.x11client.protocol.KeySystem.XK_MISCELLANY;

/**
 * Taken from https://github.com/freedesktop/xorg-x11proto/blob/master/keysymdef.h
 *
 * The "X11 Window System Protocol" standard defines in Appendix A the
 * keysym codes. These 29-bit integer values identify characters or
 * functions associated with each key (e.g., via the visible
 * engraving) of a keyboard layout. This file assigns mnemonic macro
 * names for these keysyms.
 *
 * This file is also compiled (by src/util/makekeys.c in libX11) into
 * hash tables that can be accessed with X11 library functions such as
 * XStringToKeysym() and XKeysymToString().
 *
 * Where a keysym corresponds one-to-one to an ISO 10646 / Unicode
 * character, this is noted in a comment that provides both the U+xxxx
 * Unicode position, as well as the official Unicode name of the
 * character.
 *
 * Where the correspondence is either not one-to-one or semantically
 * unclear, the Unicode position and name are enclosed in
 * parentheses. Such legacy keysyms should be considered deprecated
 * and are not recommended for use in future keyboard mappings.
 *
 * For any future extension of the keysyms with characters already
 * found in ISO 10646 / Unicode, the following algorithm shall be
 * used. The new keysym code position will simply be the character's
 * Unicode number plus 0x01000000. The keysym values in the range
 * 0x01000100 to 0x0110ffff are reserved to represent Unicode
 * characters in the range U+0100 to U+10FFFF.
 *
 * While most newer Unicode-based X11 clients do already accept
 * Unicode-mapped keysyms in the range 0x01000100 to 0x0110ffff, it
 * will remain necessary for clients -- in the interest of
 * compatibility with existing servers -- to also understand the
 * existing legacy keysym values in the range 0x0100 to 0x20ff.
 *
 * Where several mnemonic names are defined for the same keysym in this
 * file, all but the first one listed should be considered deprecated.
 *
 * Mnemonic names for keysyms are defined in this file with lines
 * that match one of these Perl regular expressions:
 *
 *    /^\#define XK_([a-zA-Z_0-9]+)\s+0x([0-9a-f]+)\s*\/\* U+([0-9A-F]{4,6}) (.*) \*\/\s*$/
 *    /^\#define XK_([a-zA-Z_0-9]+)\s+0x([0-9a-f]+)\s*\/\*\(U+([0-9A-F]{4,6}) (.*)\)\*\/\s*$/
 *    /^\#define XK_([a-zA-Z_0-9]+)\s+0x([0-9a-f]+)\s*(\/\*\s*(.*)\s*\*\/)?\s*$/
 *
 * Before adding new keysyms, please do consider the following: In
 * addition to the keysym names defined in this file, the
 * XStringToKeysym() and XKeysymToString() functions will also handle
 * any keysym string of the form "U0020" to "U007E" and "U00A0" to
 * "U10FFFF" for all possible Unicode characters. In other words,
 * every possible Unicode character has already a keysym string
 * defined algorithmically, even if it is not listed here. Therefore,
 * defining an additional keysym macro is only necessary where a
 * non-hexadecimal mnemonic name is needed, or where the new keysym
 * does not represent any existing Unicode character.
 *
 * When adding new keysyms to this file, do not forget to also update the
 * following as needed:
 *
 *   - the mappings in src/KeyBind.c in the repo
 *     git://anongit.freedesktop.org/xorg/lib/libX11.git
 *
 *   - the protocol specification in specs/keysyms.xml
 *     in the repo git://anongit.freedesktop.org/xorg/proto/x11proto.git
 *
 */
public enum KeySym implements IntValue {
  XK_NoSymbol(NONE, "NoSymbol", 0),
  /**
   * Void symbol
   */
  XK_VoidSymbol(NONE, "VoidSymbol", 0xffffff),

  /**
   *  Back space, back char
   */
  XK_BackSpace(XK_MISCELLANY, "BackSpace", 0xff08),
  XK_Tab(XK_MISCELLANY, "Tab", 0xff09),
  /**
   * Linefeed, LF
   */
  XK_Linefeed(XK_MISCELLANY, "Linefeed", 0xff0a),
  XK_Clear(XK_MISCELLANY, "Clear", 0xff0b),
  /**
   * Return, enter
   */
  XK_Return(XK_MISCELLANY, "Return", 0xff0d),
  /**
   * Pause, hold
   */
  XK_Pause(XK_MISCELLANY, "Pause", 0xff13),
  XK_Scroll_Lock(XK_MISCELLANY, "Scroll_Lock", 0xff14),
  XK_Sys_Req(XK_MISCELLANY, "Sys_Req", 0xff15),
  XK_Escape(XK_MISCELLANY, "Escape", 0xff1b),
  /**
   * Delete, rubout
   */
  XK_Delete(XK_MISCELLANY, "Delete", 0xfff),

  /**
   * Multi-key character compose
   */
  XK_Multi_key(XK_MISCELLANY, "Multi_key", 0xff20),
  XK_Codeinput(XK_MISCELLANY, "Codeinput", 0xff37),
  XK_SingleCandidate(XK_MISCELLANY, "SingleCandidate", 0xff3c),
  XK_MultipleCandidate(XK_MISCELLANY, "MultipleCandidate", 0xff3d),
  XK_PreviousCandidate(XK_MISCELLANY, "PreviousCandidate", 0xff3e),

  XK_F1(XK_MISCELLANY, "F1", 0xffbe),
  XK_F4(XK_MISCELLANY, "F4", 0xffc1);

  static final Map<Integer, KeySym> byCode = new HashMap<>();

  static {
    for(KeySym e : values()) {
      byCode.put(e.value, e);
    }
  }

  static final Map<String, KeySym> byName = new HashMap<>();

  static {
    for(KeySym e : values()) {
      byName.put(e.name, e);
    }
  }

  private KeySystem system;
  private String name;
  private int value;

  KeySym(KeySystem system, String name, int value) {
    this.system = system;
    this.name = name;
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static Optional<KeySym> getByCode(int code) {
    return Optional.ofNullable(byCode.get(code));
  }

  public static Optional<KeySym> getByName(String name) {
    return Optional.ofNullable(byName.get(name));
  }
}
