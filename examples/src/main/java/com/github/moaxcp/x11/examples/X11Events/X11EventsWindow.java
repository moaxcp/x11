package com.github.moaxcp.x11.examples.X11Events;

import com.github.moaxcp.x11.keysym.KeySym;
import com.github.moaxcp.x11.protocol.DisplayName;
import com.github.moaxcp.x11.protocol.Utilities;
import com.github.moaxcp.x11.protocol.XEvent;
import com.github.moaxcp.x11.protocol.xproto.*;
import com.github.moaxcp.x11.x11client.X11Client;

import java.io.IOException;

public class X11EventsWindow {
  private final String display;
  private X11Client client;
  private int windowId;
  private int gc;

  private int events = 0;
  private KeyPressEvent keyPress;
  private KeyReleaseEvent keyRelease;
  private boolean button1;
  private boolean button2;
  private boolean button3;
  private boolean button4;
  private boolean button5;


  public X11EventsWindow(String display) {
    this.display = display;
  }

  private void sendExposeEvent() {
    client.send(ClearArea.builder()
        .window(windowId)
        .build());
    client.send(SendEvent.builder()
        .event(Utilities.toByteList(ExposeEvent.builder()
            .window(windowId)
            .build()))
        .eventMaskEnable(EventMask.EXPOSURE)
        .build());
  }

  public void start() throws IOException {
    try (X11Client client = X11Client.connect(DisplayName.displayName(display))) {
      this.client = client;
      windowId = client.createSimpleWindow((short) 10, (short) 10, (short) 1000, (short) 1000, EventMask.EXPOSURE, EventMask.KEY_PRESS, EventMask.KEY_RELEASE, EventMask.BUTTON_PRESS, EventMask.BUTTON_RELEASE);
      client.setWindowName(windowId, "Hello World!");
      int deleteAtom = client.getAtom("WM_DELETE_WINDOW").getId();
      client.setWMProtocols(windowId, deleteAtom);
      client.mapWindow(windowId);
      gc = client.createGC(0, windowId);
      while (true) {
        XEvent event = client.getNextEvent();
        events++;
        if (event instanceof ExposeEvent) {
          exposeWindow();
          continue;
        } else if (event instanceof KeyPressEvent) {
          keyPress = (KeyPressEvent) event;
          KeySym keysym = client.keyCodeToKeySym(keyPress.getDetail(), keyPress.getState());
          if (keysym == KeySym.XK_Escape) {
            break;
          }
        } else if(event instanceof KeyReleaseEvent) {
          keyRelease = (KeyReleaseEvent) event;
        } else if(event instanceof ButtonPressEvent) {
          ButtonPressEvent buttonPress = (ButtonPressEvent) event;
          switch(buttonPress.getDetail()) {
            case 1:
              button1 = true;
              break;
            case 2:
              button2 = true;
              break;
            case 3:
              button3 = true;
              break;
            case 4:
              button4 = true;
              break;
            case 5:
              button5 = true;
              break;
          }
        } else if(event instanceof ButtonReleaseEvent) {
          ButtonReleaseEvent buttonRelease = (ButtonReleaseEvent) event;
          switch(buttonRelease.getDetail()) {
            case 1:
              button1 = false;
              break;
            case 2:
              button2 = false;
              break;
            case 3:
              button3 = false;
              break;
            case 4:
              button4 = false;
              break;
            case 5:
              button5 = false;
              break;
          }
        } else if (event instanceof ClientMessageEvent) {
          ClientMessageEvent clientMessage = (ClientMessageEvent) event;
          if (clientMessage.getFormat() == 32) {
            ClientMessageData32 data = (ClientMessageData32) clientMessage.getData();
            if (data.getData32().get(0) == deleteAtom) {
              break;
            }
          }
        }

        sendExposeEvent();
      }
    }
  }

  private void exposeWindow() {
    int y = 10;
    int increment = 15;
    client.imageText8(windowId, gc, (short) 10, (short) y, "Event Count: " + events);
    y += increment;
    if (keyPress != null) {
      KeySym keySym = client.keyCodeToKeySym(keyPress);
      client.imageText8(windowId, gc, (short) 10, (short) y, "Key Press: keycode=" + keyPress.getDetail() + " keysym=[" + keySym + ", " + keySym.getValue() + "], " + keyPress);
    }
    y += increment;
    if (keyRelease != null) {
      KeySym keySym = client.keyCodeToKeySym(keyRelease);
      client.imageText8(windowId, gc, (short) 10, (short) y, "Key Release: keycode=" + keyPress.getDetail() + ", keysym=[" + keySym + ", " + keySym.getValue() + "], " + keyRelease);
    }
    y += increment;
    if(button1) {
      client.fillRectangle(windowId, gc, (short) 10, (short) y, (short) 10, (short) 10);
    }
    if(button2) {
      client.fillRectangle(windowId, gc, (short) 20, (short) y, (short) 10, (short) 10);
    }
    if(button3) {
      client.fillRectangle(windowId, gc, (short) 30, (short) y, (short) 10, (short) 10);
    }
    if(button4) {
      client.fillRectangle(windowId, gc, (short) 40, (short) y, (short) 10, (short) 10);
    }
    if(button5) {
      client.fillRectangle(windowId, gc, (short) 50, (short) y, (short) 10, (short) 10);
    }
  }
}
