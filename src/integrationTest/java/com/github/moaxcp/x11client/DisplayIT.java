package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.display.Display;
import com.github.moaxcp.x11client.display.GraphicsContext;
import com.github.moaxcp.x11client.display.Window;
import com.github.moaxcp.x11client.protocol.xproto.ImageText8;
import com.github.moaxcp.x11client.protocol.xproto.PolyFillRectangle;
import com.github.moaxcp.x11client.protocol.xproto.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11client.Utilities.stringToByteList;

public class DisplayIT {

  @Test
  void displayTest() throws IOException {
    try (Display display = new Display(X11Client.connect())) {
      Window window = display.createSimpleWindow(0, 10, 10, 600, 480, 5);
      window.map();
      GraphicsContext gc = window.createGC();
      window.exposeEvent((d, e) -> {
        List<Rectangle> rectangles = new ArrayList<>();
        rectangles.add(Rectangle.builder()
          .x((short) 20)
          .y((short) 20)
          .width((short) 10)
          .height((short) 10)
          .build());
        d.send(PolyFillRectangle.builder()
          .drawable(window.getId())
          .gc(gc.getId())
          .rectangles(rectangles)
          .build());
        d.send(ImageText8.builder()
          .drawable(window.getId())
          .gc(gc.getId())
          .stringLen((byte) "Hello World!".length())
          .string(stringToByteList("Hello World!"))
          .x((short) 10)
          .y((short) 50)
          .build());
      });
      window.keyPressEvent((d, e) -> {
        d.stop();
      });
      display.start();
    }
  }
}
