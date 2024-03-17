package com.github.moaxcp.x11.examples;

import com.github.moaxcp.x11.keysym.KeySym;
import com.github.moaxcp.x11.x11client.X11Client;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.List;

@Log
public class KeySymMain {

  public static void main(String... args) throws IOException {
    try(X11Client client = X11Client.connect()) {
      List<Byte> keyCodes = client.keySymToKeyCodes(KeySym.XK_Escape);
      for(byte keyCode : keyCodes) {
        KeySym keySym = client.keyCodeToKeySym(keyCode, (short) 0);
        log.info(keySym.toString());
      }
    }
  }
}
