package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XEvent;
import com.github.moaxcp.x11client.protocol.bigreq.EnableReply;
import com.github.moaxcp.x11client.protocol.bigreq.EnableRequest;
import com.github.moaxcp.x11client.protocol.xproto.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class X11ConnectionIT {
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
  void test() throws IOException {
    try(X11Connection connection = X11Connection.connect(new DisplayName(":1"))) {
      X11Output out = connection.getX11Output();
      X11Input in = connection.getX11Input();
      QueryExtensionRequest extensionRequest = QueryExtensionRequest.builder()
        .name("XC-MISC")
        .build();
      extensionRequest.write((byte) 0, out);
      byte status = in.readByte();
      if(status == 0) {
        //error
      } else if(status == 1) {
        //success
        byte field = in.readByte();
        short sequenceNumber = in.readCard16();
        QueryExtensionReply reply = QueryExtensionReply.readQueryExtensionReply(field, sequenceNumber, in);
        System.out.println(reply);
      } else {
        //event
      }
    }
  }

  @Test
  void clientTest() throws IOException {
    try(X11Client x11Client = X11Client.connect(new DisplayName(":1"))) {
      SetupStruct setup = x11Client.getSetup();
      GetKeyboardMappingRequest keyboard = GetKeyboardMappingRequest.builder()
        .firstKeycode(setup.getMinKeycode())
        .count((byte) (setup.getMaxKeycode() - setup.getMinKeycode() + 1))
        .build();
      System.out.println(keyboard);
      GetKeyboardMappingReply keyboardReply = x11Client.send(keyboard);
      System.out.println(keyboardReply);

      EnableRequest enableRequest = EnableRequest.builder().build();
      System.out.println(enableRequest);
      EnableReply enableReply = x11Client.send(enableRequest);
      System.out.println(enableReply);

      CreateWindowRequest window = CreateWindowRequest.builder()
        .depth(x11Client.getDefaultDepth())
        .wid(x11Client.nextResourceId())
        .parent(x11Client.getDefaultRoot())
        .x((short) 10)
        .y((short) 10)
        .width((short) 600)
        .height((short) 480)
        .borderWidth((short) 5)
        .clazz(WindowClassEnum.COPY_FROM_PARENT)
        .visual(x11Client.getDefaultVisualId())
        .backgroundPixel(x11Client.getDefaultScreen().getWhitePixel())
        .borderPixel(x11Client.getDefaultScreen().getBlackPixel())
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
        .background(x11Client.getDefaultScreen().getWhitePixel())
        .foreground(x11Client.getDefaultScreen().getBlackPixel())
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
          x11Client.flush();
        } else if(event instanceof KeyPressEvent) {
          break;
        }
      }
    }
  }
}
