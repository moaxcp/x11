package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.display.Display;
import com.github.moaxcp.x11client.display.Window;
import com.github.moaxcp.x11client.protocol.xproto.Rectangle;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class DisplayIT {

  @Test
  void displayTest() throws IOException {
    try (Display display = new Display(X11Client.connect())) {
      Window window = display.createSimpleWindow(0, 10, 10, 600, 480);
      window.map();
      window.exposeEvent((w, e) -> {
        w.getGc().polyFillRectangle(Rectangle.builder()
          .x((short) 20)
          .y((short) 20)
          .width((short) 10)
          .height((short) 10)
          .build());
        w.getGc().imageText8((short) 10, (short) 50, "Hello World!");
      });
      window.keyPressEvent((w, e) -> {
        display.stop();
      });
      display.start();
    }
  }
}
