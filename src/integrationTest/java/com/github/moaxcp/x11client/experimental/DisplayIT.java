package com.github.moaxcp.x11client.experimental;

import com.github.moaxcp.x11client.X11Client;
import com.github.moaxcp.x11client.XephyrRunner;
import com.github.moaxcp.x11client.protocol.DisplayName;
import com.github.moaxcp.x11client.protocol.xproto.Rectangle;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class DisplayIT {
  private XephyrRunner runner;

  @BeforeEach
  void setup() throws IOException {
    runner = XephyrRunner.builder()
            .ac(true)
            .br(true)
            .noreset(true)
            .screen("1200x1000")
            .softCursor(true)
            .withXTerm(2)
            .build();
    runner.start();
  }

  @AfterEach
  void teardown() throws InterruptedException {
    runner.stop();
  }

  @Test
  void displayTest() throws IOException {
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
    }
  }
}
