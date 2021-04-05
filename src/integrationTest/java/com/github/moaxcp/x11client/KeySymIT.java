package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.KeySym;
import java.io.IOException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class KeySymIT {

  @Test
  void keyCode() throws IOException {
    try(X11Client client = X11Client.connect()) {
      byte keyCode = client.keySymToKeyCode(KeySym.XK_Escape.getValue());
      int keySym = client.keyCodeToKeySym(keyCode, (short) 0);
      assertThat(keySym).isEqualTo(KeySym.XK_Escape.getValue());
    }
  }
}
