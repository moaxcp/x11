package com.github.moaxcp.x11.examples;

import com.github.moaxcp.x11.keysym.KeySym;
import com.github.moaxcp.x11.x11client.X11Client;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class KeySymMain {

  private static final Logger log = Logger.getLogger(KeySymMain.class.getName());

  public static void main(String... args) throws IOException {
    try (X11Client client = X11Client.connect()) {
      List<Byte> keyCodes = client.keySymToKeyCodes(KeySym.XK_Escape);
      for (byte keyCode : keyCodes) {
        KeySym keySym = client.keyCodeToKeySym(keyCode, (short) 0);
        log.info(keySym.toString());
      }
    }
  }
}
