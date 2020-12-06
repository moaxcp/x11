package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.XEvent;
import com.github.moaxcp.x11client.protocol.xproto.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class X11ClientIT {
  private XephyrRunner runner;

  @BeforeEach
  void setup() throws IOException {
    runner = XephyrRunner.builder()
      .ac(true)
      .br(true)
      .noreset(true)
      .arg(":1")
      .build();
    runner.start();
  }

  @AfterEach
  void teardown() throws InterruptedException {
    runner.stop();
  }

  @Test
  void clientTest() throws IOException {
    try(X11Client x11Client = X11Client.connect(new DisplayName(":1"))) {
      CreateWindowRequest window = CreateWindowRequest.builder()
        .depth(x11Client.getDepth(0))
        .wid(x11Client.nextResourceId())
        .parent(x11Client.getRoot(0))
        .x((short) 10)
        .y((short) 10)
        .width((short) 600)
        .height((short) 480)
        .borderWidth((short) 5)
        .clazz(WindowClassEnum.COPY_FROM_PARENT)
        .visual(x11Client.getVisualId(0))
        .backgroundPixel(x11Client.getWhitePixel(0))
        .borderPixel(x11Client.getBlackPixel(0))
        .eventMaskEnable(EventMaskEnum.EXPOSURE)
        .eventMaskEnable(EventMaskEnum.KEY_PRESS)
        .build();
      System.out.println(window);
      x11Client.send(window);
      MapWindowRequest mapWindow = MapWindowRequest.builder()
        .window(window.getWid())
        .build();
      System.out.println(mapWindow);
      x11Client.send(mapWindow);
      CreateGCRequest gc = CreateGCRequest.builder()
        .cid(x11Client.nextResourceId())
        .drawable(window.getWid())
        .background(x11Client.getWhitePixel(0))
        .foreground(x11Client.getBlackPixel(0))
        .build();
      x11Client.send(gc);
      while(true) {
        XEvent event = x11Client.getNextEvent();
        System.out.println(event);
        if(event instanceof ExposeEvent) {
          List<RectangleStruct> rectangles = new ArrayList<>();
          rectangles.add(RectangleStruct.builder()
            .x((short) 20)
            .y((short) 20)
            .width((short) 10)
            .height((short) 10)
            .build());
          x11Client.send(PolyFillRectangleRequest.builder()
            .drawable(window.getWid())
            .gc(gc.getCid())
            .rectangles(rectangles)
            .build());
          x11Client.send(ImageText8Request.builder()
            .drawable(window.getWid())
            .gc(gc.getCid())
            .string("Hello World!")
            .x((short) 10)
            .y((short) 50)
            .build());
        } else if(event instanceof KeyPressEvent) {
          break;
        }
      }
    }
  }
}
