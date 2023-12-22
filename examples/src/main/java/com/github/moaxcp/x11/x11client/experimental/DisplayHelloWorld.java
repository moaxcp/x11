package com.github.moaxcp.x11.x11client.experimental;

import com.github.moaxcp.x11.protocol.DisplayName;
import com.github.moaxcp.x11.protocol.xproto.Rectangle;
import com.github.moaxcp.x11.toolkit.Display;
import com.github.moaxcp.x11.toolkit.Window;
import com.github.moaxcp.x11.x11client.X11Client;
import com.github.moaxcp.x11.xephyr.XephyrRunner;

import java.io.IOException;

public class DisplayHelloWorld {

  public static void main(String... args) throws IOException, InterruptedException {
    XephyrRunner runner = XephyrRunner.builder()
            .ac(true)
            .br(true)
            .noreset(true)
            .screen("1200x1000")
            .softCursor(true)
            .withXTerm(2)
            .build();
    runner.start();
    try (Display display = new Display(X11Client.connect(DisplayName.displayName(runner.getDisplay())))) {
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
      window.keyPressEvent((w, e) -> display.stop());
      display.start();
      runner.stop();
    }
  }
}
