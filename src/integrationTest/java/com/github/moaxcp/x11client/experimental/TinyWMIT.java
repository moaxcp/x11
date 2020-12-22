package com.github.moaxcp.x11client.experimental;

import com.github.moaxcp.x11client.protocol.DisplayName;
import com.github.moaxcp.x11client.protocol.KeySym;
import com.github.moaxcp.x11client.X11Client;
import com.github.moaxcp.x11client.XephyrRunner;
import com.github.moaxcp.x11client.protocol.XEvent;
import com.github.moaxcp.x11client.protocol.xproto.*;
import com.github.moaxcp.x11client.protocol.xproto.Window;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11client.protocol.xproto.EventMask.*;

public class TinyWMIT {
  private XephyrRunner runner;

  @BeforeEach
  void setup() throws IOException {
    runner = XephyrRunner.builder()
      .ac(true)
      .br(true)
      .noreset(true)
      .screen("1200x1000")
      .softCursor(true)
      .arg(":1")
      .build();
    runner.start();
  }

  @AfterEach
  void teardown() throws InterruptedException {
    runner.stop();
  }

  @Test
  void wm() throws IOException {
    try(X11Client client = X11Client.connect(new DisplayName(":1"))) {
      int wid = client.nextResourceId();
      client.send(CreateWindow.builder()
        .depth(client.getDepth(0))
        .wid(wid)
        .parent(client.getRoot(0))
        .x((short) 0)
        .y((short) 0)
        .width((short) 30)
        .height((short) 30)
        .borderWidth((short) 0)
        .clazz(WindowClass.COPY_FROM_PARENT)
        .visual(client.getVisualId(0))
        .backgroundPixel(client.getWhitePixel(0))
        .borderPixel(client.getBlackPixel(0))
        .build());
      client.send(MapWindow.builder()
        .window(wid)
        .build());
      client.send(GrabKey.builder()
        .key((byte) client.keySymToKeyCode(KeySym.getByName("F1").get().getValue()))
        .modifiersEnable(ModMask.ONE)
        .grabWindow(client.getRoot(0))
        .ownerEvents(true)
        .keyboardMode(GrabMode.ASYNC)
        .pointerMode(GrabMode.ASYNC)
        .build());
      client.send(GrabButton.builder()
        .button(ButtonIndex.ONE)
        .modifiersEnable(ModMask.ONE)
        .grabWindow(client.getRoot(0))
        .ownerEvents(true)
        .eventMaskEnable(BUTTON_PRESS, BUTTON_RELEASE, POINTER_MOTION)
        .keyboardMode(GrabMode.ASYNC)
        .pointerMode(GrabMode.ASYNC)
        .build());
      client.send(GrabButton.builder()
        .button(ButtonIndex.THREE)
        .modifiersEnable(ModMask.ONE)
        .grabWindow(client.getRoot(0))
        .ownerEvents(true)
        .eventMaskEnable(BUTTON_PRESS, BUTTON_RELEASE, POINTER_MOTION)
        .keyboardMode(GrabMode.ASYNC)
        .pointerMode(GrabMode.ASYNC)
        .build());

      GetGeometryReply geometry = null;
      ButtonPressEvent start = null;

      while(true) {
        XEvent event = client.getNextEvent();
        if(event instanceof KeyPressEvent) {
          KeyPressEvent keyPress = (KeyPressEvent) event;
          int child = keyPress.getChild();
          if(child != Window.NONE.getValue()) {
            //XRaiseWindow https://github.com/mirror/libX11/blob/caa71668af7fd3ebdd56353c8f0ab90824773969/src/RaiseWin.c
            client.send(ConfigureWindow.builder()
              .window(child)
              .stackMode(StackMode.ABOVE)
              .build());
          }
        } else if(event instanceof ButtonPressEvent) {
          ButtonPressEvent buttonPress = (ButtonPressEvent) event;
          int child = buttonPress.getChild();
          if(child != Window.NONE.getValue()) {
            geometry = client.send(GetGeometry.builder()
              .drawable(child)
              .build());
            start = buttonPress;
          }
        } else if(event instanceof MotionNotifyEvent) {
          MotionNotifyEvent motionNotify = (MotionNotifyEvent) event;
          int child = motionNotify.getChild();
          if(child != Window.NONE.getValue()) {
            int xdiff = motionNotify.getRootX() - start.getRootX();
            int ydiff = motionNotify.getRootY() - start.getRootY();
            client.send(ConfigureWindow.builder()
              .window(child)
              .x(geometry.getX() + (start.getDetail() == ButtonIndex.ONE.getValue() ? xdiff : 0))
              .y(geometry.getY() + (start.getDetail() == ButtonIndex.ONE.getValue() ? ydiff : 0))
              .width(Math.max(1, geometry.getWidth() + (start.getDetail() == ButtonIndex.THREE.getValue() ? xdiff : 0)))
              .height(Math.max(1, geometry.getHeight() + (start.getDetail() == ButtonIndex.THREE.getValue() ? ydiff : 0)))
              .build());
          }
        } else if(event instanceof ButtonReleaseEvent) {
          start = null;
        }
      }
    }
  }
}
