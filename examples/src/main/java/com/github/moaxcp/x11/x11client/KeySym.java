package com.github.moaxcp.x11.x11client;

import lombok.extern.java.Log;

import java.io.IOException;
import java.util.List;

@Log
public class KeySym {

  public static void main(String... args) throws IOException {
    try(X11Client client = X11Client.connect()) {
      List<Byte> keyCodes = client.keySymToKeyCodes(com.github.moaxcp.x11.protocol.KeySym.XK_Escape);
      for(byte keyCode : keyCodes) {
        com.github.moaxcp.x11.protocol.KeySym keySym = client.keyCodeToKeySym(keyCode, (short) 0);
        log.info(keySym.toString());
      }
    }
  }
}
