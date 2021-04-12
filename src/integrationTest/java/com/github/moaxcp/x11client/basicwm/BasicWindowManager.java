package com.github.moaxcp.x11client.basicwm;

import com.github.moaxcp.x11client.X11Client;
import com.github.moaxcp.x11client.protocol.AtomValue;
import com.github.moaxcp.x11client.protocol.DisplayName;
import com.github.moaxcp.x11client.protocol.KeySym;
import com.github.moaxcp.x11client.protocol.XEvent;
import com.github.moaxcp.x11client.protocol.xproto.*;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.github.moaxcp.x11client.protocol.Utilities.toByteList;
import static com.github.moaxcp.x11client.protocol.xproto.EventMask.SUBSTRUCTURE_NOTIFY;
import static com.github.moaxcp.x11client.protocol.xproto.EventMask.SUBSTRUCTURE_REDIRECT;
import static java.lang.Math.max;

public class BasicWindowManager {

  private final String display;
  private final Map<Integer, Integer> windowToFrame = new LinkedHashMap<>();
  private X11Client client;

  private Point dragStartPointWindow;
  private GetGeometryReply dragStartPointFrame;


  public BasicWindowManager(String display) {
    this.display = display;
  }

  public void start() throws IOException {
    try(X11Client client = X11Client.connect(new DisplayName(display))) {
      this.client = client;
      client.getAtom("WM_PROTOCOLS");
      client.getAtom("WM_DELETE_WINDOW");
      client.send(ChangeWindowAttributes.builder()
        .window(client.getDefaultRoot())
        .eventMaskEnable(SUBSTRUCTURE_REDIRECT, SUBSTRUCTURE_NOTIFY)
        .build());

      client.sync();
      client.send(GrabServer.builder().build());

      QueryTreeReply tree = client.send(QueryTree.builder().window(client.getDefaultRoot()).build());
      for(int window : tree.getChildren()) {
        frameExisting(window);
      }

      client.send(UngrabServer.builder().build());

      while(true) {
        XEvent event = client.getNextEvent();
        if(event instanceof CreateNotifyEvent) {
          onCreateNotify((CreateNotifyEvent) event);
        } else if(event instanceof DestroyNotifyEvent) {
          onDestroyNotify((DestroyNotifyEvent) event);
        } else if(event instanceof ReparentNotifyEvent) {
          onReparentNotify((ReparentNotifyEvent) event);
        } else if(event instanceof MapNotifyEvent) {
          onMapNotify((MapNotifyEvent) event);
        } else if(event instanceof UnmapNotifyEvent) {
          onUnmapNotify((UnmapNotifyEvent) event);
        } else if(event instanceof ConfigureNotifyEvent) {
          onConfigureNotify((ConfigureNotifyEvent) event);
        } else if(event instanceof MapRequestEvent) {
          onMapRequest((MapRequestEvent) event);
        } else if(event instanceof ConfigureRequestEvent) {
          onConfigureRequest((ConfigureRequestEvent) event);
        } else if(event instanceof ButtonPressEvent) {
          onButtonPress((ButtonPressEvent) event);
        } else if(event instanceof ButtonReleaseEvent) {
          onButtonRelease((ButtonReleaseEvent) event);
        } else if(event instanceof MotionNotifyEvent) {
          onMotionNotify((MotionNotifyEvent) event);
        } else if(event instanceof KeyPressEvent) {
          onKeyPress((KeyPressEvent) event);
        } else if(event instanceof KeyReleaseEvent) {
          onKeyRelease((KeyReleaseEvent) event);
        } else {
          System.out.println("ignored event: " + event);
        }
      }
    }
  }

  private void onCreateNotify(CreateNotifyEvent event) {
  }

  private void onDestroyNotify(DestroyNotifyEvent event) {

  }

  private void onReparentNotify(ReparentNotifyEvent event) {

  }

  private void onMapNotify(MapNotifyEvent event) {

  }

  private void onUnmapNotify(UnmapNotifyEvent event) {
    if(event.getEvent() == client.getDefaultRoot()) {
      return;
    }
    unframe(event.getWindow());
  }

  private void onConfigureNotify(ConfigureNotifyEvent event) {
  }

  private void onMapRequest(MapRequestEvent event) {
    frame(event.getWindow());
    client.send(MapWindow.builder()
      .window(event.getWindow())
      .build());
  }

  private void onConfigureRequest(ConfigureRequestEvent event) {
    if(windowToFrame.containsKey(event.getWindow())) {
      client.send(ConfigureWindow.builder()
        .window(windowToFrame.get(event.getWindow()))
        .x(event.getX())
        .y(event.getY())
        .width(event.getWidth())
        .height(event.getHeight())
        .borderWidth(event.getBorderWidth())
        .sibling(event.getSibling())
        .stackMode(event.getStackMode())
        .build());
    }
    client.send(ConfigureWindow.builder()
      .window(event.getWindow())
      .x(event.getX())
      .y(event.getY())
      .width(event.getWidth())
      .height(event.getHeight())
      .borderWidth(event.getBorderWidth())
      .sibling(event.getSibling())
      .stackMode(event.getStackMode())
      .build());
  }

  private void onButtonPress(ButtonPressEvent event) {
    int frame = windowToFrame.get(event.getEvent());
    dragStartPointWindow = Point.builder()
      .x(event.getRootX())
      .y(event.getRootY())
      .build();

    dragStartPointFrame = client.send(GetGeometry.builder()
      .drawable(frame)
      .build());

    client.send(ConfigureWindow.builder()
      .window(frame)
      .stackMode(StackMode.TOP_IF)
      .build());
  }

  private void onButtonRelease(ButtonReleaseEvent event) {
  }

  private void onMotionNotify(MotionNotifyEvent event) {
    int frame = windowToFrame.get(event.getEvent());
    Point dragPosition = Point.builder()
      .x(event.getRootX())
      .y(event.getRootY())
      .build();
    Point delta = Point.builder()
      .x((short) (dragPosition.getX() - dragStartPointWindow.getX()))
      .y((short) (dragPosition.getY() - dragStartPointWindow.getY()))
      .build();

    if(event.isStateEnabled(KeyButMask.BUTTON1)) {
      client.send(ConfigureWindow.builder()
        .window(frame)
        .x(dragStartPointFrame.getX() + delta.getX())
        .y(dragStartPointFrame.getY() + delta.getY())
        .build());
    } else if(event.isStateEnabled(KeyButMask.BUTTON3)) {
      Point sizeDelta = Point.builder()
        .x((short) max(delta.getX(), -dragStartPointFrame.getWidth()))
        .y((short) max(delta.getY(), -dragStartPointFrame.getHeight()))
        .build();
      client.send(ConfigureWindow.builder()
        .window(frame)
        .width(dragStartPointFrame.getWidth() + sizeDelta.getX())
        .height(dragStartPointFrame.getHeight() + sizeDelta.getY())
        .build());
      client.send(ConfigureWindow.builder()
        .window(event.getEvent())
        .width(dragStartPointFrame.getWidth() + sizeDelta.getX())
        .height(dragStartPointFrame.getHeight() + sizeDelta.getY())
        .build());
    }
  }

  private void onKeyPress(KeyPressEvent event) {
    if(event.isStateEnabled(KeyButMask.MOD1) && client.keyCodeToKeySym(event.getDetail(), event.getState()) == KeySym.XK_F4) {
      List<Integer> wmProtocols = client.getWMProtocols(event.getEvent());
      AtomValue delete = client.getAtom("WM_DELETE_WINDOW");
      if(wmProtocols.contains(delete.getId())) {
        ClientMessageEvent message = ClientMessageEvent.builder()
          .type(client.getAtom("WM_PROTOCOLS").getId())
          .window(event.getEvent())
          .format((byte) 32)
          .data(new ClientMessageData32(delete.getId()))
          .build();
        client.send(SendEvent.builder()
          .event(toByteList(message))
          .build());
      } else {
        client.killClient(event.getEvent());
      }
    } else if(event.isStateEnabled(KeyButMask.MOD1) && client.keyCodeToKeySym(event.getDetail(), event.getState()) == KeySym.XK_Tab) {
      Iterator<Integer> iterator = windowToFrame.values().iterator();
      int nextWindow = windowToFrame.values().iterator().next();
      while(iterator.hasNext()) {
        int frame = iterator.next();
        if(frame == event.getEvent()) {
          if(iterator.hasNext()) {
            nextWindow = iterator.next();
          }
          break;
        }
      }

      client.raiseWindow(nextWindow);
      client.inputFocus(nextWindow);
    }
  }

  private void onKeyRelease(KeyReleaseEvent event) {
  }

  private void frameExisting(int window) {
    //check for redirect and visible
    GetWindowAttributesReply attributes = client.send(GetWindowAttributes.builder()
      .window(window)
      .build());
    if(attributes.isOverrideRedirect() || !MapState.VIEWABLE.isEnabled(attributes.getMapState())) {
      return;
    }
    frame(window);
  }

  private void frame(int window) {
    final short BORDER_WIDTH = 3;
    final int BORDER_COLOR = 0xff0000;
    final int BG_COLOR = 0x0000ff;

    GetGeometryReply geometry = client.send(GetGeometry.builder()
      .drawable(window)
      .build());
    int frame = client.nextResourceId();
    client.send(CreateWindow.builder()
      .parent(client.getDefaultRoot())
      .wid(frame)
      .x(geometry.getX())
      .y(geometry.getY())
      .width(geometry.getWidth())
      .height(geometry.getHeight())
      .borderWidth(BORDER_WIDTH)
      .borderPixel(BORDER_COLOR)
      .backgroundPixel(BG_COLOR)
      .eventMaskEnable(SUBSTRUCTURE_REDIRECT, SUBSTRUCTURE_NOTIFY)
      .build());

    client.send(ChangeSaveSet.builder()
      .window(window)
      .mode(SetMode.INSERT)
      .build());

    client.send(ReparentWindow.builder()
      .window(window)
      .parent(frame)
      .build());

    client.send(MapWindow.builder()
      .window(frame)
      .build());

    windowToFrame.put(window, frame);

    client.send(GrabButton.builder()
      .button(ButtonIndex.ONE)
      .modifiersEnable(ModMask.ONE)
      .grabWindow(window)
      .eventMaskEnable(EventMask.BUTTON_PRESS, EventMask.BUTTON_RELEASE, EventMask.BUTTON_MOTION)
      .pointerMode(GrabMode.ASYNC)
      .keyboardMode(GrabMode.ASYNC)
      .build());

    client.send(GrabButton.builder()
      .button(ButtonIndex.THREE)
      .modifiersEnable(ModMask.ONE)
      .grabWindow(window)
      .eventMaskEnable(EventMask.BUTTON_PRESS, EventMask.BUTTON_RELEASE, EventMask.BUTTON_MOTION)
      .pointerMode(GrabMode.ASYNC)
      .keyboardMode(GrabMode.ASYNC)
      .build());

    client.send(GrabKey.builder()
      .key(client.keySymToKeyCodes(KeySym.XK_F4).get(0))
      .modifiersEnable(ModMask.ONE)
      .grabWindow(window)
      .pointerMode(GrabMode.ASYNC)
      .keyboardMode(GrabMode.ASYNC)
      .build());

    client.send(GrabKey.builder()
      .key(client.keySymToKeyCodes(KeySym.XK_Tab).get(0))
      .modifiersEnable(ModMask.ONE)
      .grabWindow(window)
      .pointerMode(GrabMode.ASYNC)
      .keyboardMode(GrabMode.ASYNC)
      .build());
  }

  private void unframe(int window) {
    int frame = windowToFrame.get(window);
    client.send(UnmapWindow.builder()
      .window(frame)
      .build());
    client.send(ReparentWindow.builder()
      .window(window)
      .parent(client.getDefaultRoot())
      .build());
    client.send(ChangeSaveSet.builder()
      .window(window)
      .mode(SetMode.DELETE)
      .build());
    client.send(DestroyWindow.builder()
      .window(frame)
      .build());
    windowToFrame.remove(window);
  }

  public static void main(String... args) throws IOException {
    new BasicWindowManager(args[0]).start();
  }
}
