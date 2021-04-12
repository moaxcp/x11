package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.KeySym;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class KeySymIT {

  @Test
  void keyCode() throws IOException {
    try(X11Client client = X11Client.connect()) {
      List<Byte> keyCodes = client.keySymToKeyCodes(KeySym.XK_Escape);
      assertThat(keyCodes).isNotEmpty();
      for(byte keyCode : keyCodes) {
        KeySym keySym = client.keyCodeToKeySym(keyCode, (short) 0);
        assertThat(keySym).isEqualTo(KeySym.XK_Escape);
      }
    }
  }
}
