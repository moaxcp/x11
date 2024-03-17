package com.github.moaxcp.x11.examples.tinywm;

import com.github.moaxcp.x11.keysym.KeySym;
import com.github.moaxcp.x11.protocol.DisplayName;
import com.github.moaxcp.x11.protocol.XEvent;
import com.github.moaxcp.x11.protocol.xproto.*;
import com.github.moaxcp.x11.x11client.X11Client;

import java.io.IOException;

public class TinyWindowManager {
  private final String display;
  public TinyWindowManager(String display) {
    this.display = display;
  }

  public void start() throws IOException {
    try(X11Client client = X11Client.connect(new DisplayName(display))) {
      client.send(GrabKey.builder()
        .key(client.keySymToKeyCodes(KeySym.XK_F1).get(0))
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
        .eventMaskEnable(EventMask.BUTTON_PRESS, EventMask.BUTTON_RELEASE, EventMask.POINTER_MOTION)
        .keyboardMode(GrabMode.ASYNC)
        .pointerMode(GrabMode.ASYNC)
        .build());
      client.send(GrabButton.builder()
        .button(ButtonIndex.THREE)
        .modifiersEnable(ModMask.ONE)
        .grabWindow(client.getRoot(0))
        .ownerEvents(true)
        .eventMaskEnable(EventMask.BUTTON_PRESS, EventMask.BUTTON_RELEASE, EventMask.POINTER_MOTION)
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
            client.raiseWindow(child);
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

  public static void main(String... args) throws IOException {
    TinyWindowManager wm = new TinyWindowManager(args[0]);
    wm.start();
  }
}
