package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.KeySym;
import com.github.moaxcp.x11client.protocol.Utilities;
import com.github.moaxcp.x11client.protocol.XEvent;
import com.github.moaxcp.x11client.protocol.xproto.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static com.github.moaxcp.x11client.protocol.Utilities.toList;

public class X11ClientIT {

  @Test
  void simpleHelloWorldMouse() throws IOException {
    try(X11Client client = X11Client.connect()) {
      CreateWindow window = CreateWindow.builder()
        .depth(client.getDepth(0))
        .wid(client.nextResourceId())
        .parent(client.getRoot(0))
        .x((short) 10)
        .y((short) 10)
        .width((short) 600)
        .height((short) 480)
        .borderWidth((short) 5)
        .clazz(WindowClass.COPY_FROM_PARENT)
        .visual(client.getVisualId(0))
        .backgroundPixel(client.getWhitePixel(0))
        .borderPixel(client.getBlackPixel(0))
        .eventMaskEnable(EventMask.EXPOSURE, EventMask.KEY_PRESS)
        .build();
      client.send(window);

      //XStoreName sets window title
      client.send(ChangeProperty.builder()
        .window(window.getWid())
        .property(Atom.WM_NAME.getValue())
        .type(Atom.STRING.getValue())
        .format((byte) 8)
        .dataLen("Hello World!".length())
        .data(Utilities.toByteList("Hello World!"))
        .build());

      //XSetWMProtocols for adding delete atom
      InternAtomReply wmProtocols = client.send(InternAtom.builder().name(Utilities.toByteList("WM_PROTOCOLS")).build());
      InternAtomReply deleteAtom = client.send(InternAtom.builder().name(Utilities.toByteList("WM_DELETE_WINDOW")).build());
      client.send(ChangeProperty.builder()
        .window(window.getWid())
        .property(wmProtocols.getAtom())
        .type(Atom.ATOM.getValue())
        .format((byte) 32)
        .mode(PropMode.REPLACE)
        .data(toList(ByteBuffer.allocate(4).putInt(deleteAtom.getAtom()).array()))
        .dataLen(1)
        .build());

      client.send(MapWindow.builder()
        .window(window.getWid())
        .build());
      CreateGC gc = CreateGC.builder()
        .cid(client.nextResourceId())
        .drawable(window.getWid())
        .background(client.getWhitePixel(0))
        .foreground(client.getBlackPixel(0))
        .build();
      client.send(gc);
      while(true) {
        XEvent event = client.getNextEvent();
        if(event instanceof ExposeEvent) {
          List<Rectangle> rectangles = new ArrayList<>();
          rectangles.add(Rectangle.builder()
            .x((short) 20)
            .y((short) 20)
            .width((short) 10)
            .height((short) 10)
            .build());
          client.send(PolyFillRectangle.builder()
            .drawable(window.getWid())
            .gc(gc.getCid())
            .rectangles(rectangles)
            .build());
          client.send(ImageText8.builder()
            .drawable(window.getWid())
            .gc(gc.getCid())
            .string(Utilities.toByteList("Hello World!"))
            .x((short) 10)
            .y((short) 50)
            .build());
          GetGeometryReply geometry = client.send(GetGeometry.builder()
            .drawable(window.getWid())
            .build());
          String attributeText = String.format("Size: %dx%d", geometry.getWidth(), geometry.getHeight());
          client.send(ImageText8.builder()
            .drawable(window.getWid())
            .gc(gc.getCid())
            .string(Utilities.toByteList(attributeText))
            .x((short) 10)
            .y((short) 80)
            .build());
        } else if(event instanceof KeyPressEvent) {
          KeyPressEvent keyPress = (KeyPressEvent) event;
          KeySym keysym = client.keyCodeToKeySym(keyPress.getDetail(), keyPress.getState());
          if(keysym == KeySym.XK_Escape) {
            break;
          }
        } else if(event instanceof ClientMessageEvent) {
          ClientMessageEvent clientMessage = (ClientMessageEvent) event;
          if(clientMessage.getFormat() == 32) {
            ClientMessageData32 data = (ClientMessageData32) clientMessage.getData();
            if(data.getData32().get(0) == deleteAtom.getAtom()) {
              break;
            }
          }
        } else {
          throw new IllegalStateException(event.toString());
        }
      }
    }
  }

  @Test
  void simpleHelloWorld() throws IOException {
    try(X11Client x11Client = X11Client.connect()) {
      CreateWindow window = CreateWindow.builder()
        .depth(x11Client.getDepth(0))
        .wid(x11Client.nextResourceId())
        .parent(x11Client.getRoot(0))
        .x((short) 10)
        .y((short) 10)
        .width((short) 600)
        .height((short) 480)
        .borderWidth((short) 5)
        .clazz(WindowClass.COPY_FROM_PARENT)
        .visual(x11Client.getVisualId(0))
        .backgroundPixel(x11Client.getWhitePixel(0))
        .borderPixel(x11Client.getBlackPixel(0))
        .eventMaskEnable(EventMask.EXPOSURE, EventMask.KEY_PRESS)
        .build();
      x11Client.send(window);
      x11Client.send(MapWindow.builder()
        .window(window.getWid())
        .build());
      CreateGC gc = CreateGC.builder()
        .cid(x11Client.nextResourceId())
        .drawable(window.getWid())
        .background(x11Client.getWhitePixel(0))
        .foreground(x11Client.getBlackPixel(0))
        .build();
      x11Client.send(gc);
      while(true) {
        XEvent event = x11Client.getNextEvent();
        if(event instanceof ExposeEvent) {
          List<Rectangle> rectangles = new ArrayList<>();
          rectangles.add(Rectangle.builder()
            .x((short) 20)
            .y((short) 20)
            .width((short) 10)
            .height((short) 10)
            .build());
          x11Client.send(PolyFillRectangle.builder()
            .drawable(window.getWid())
            .gc(gc.getCid())
            .rectangles(rectangles)
            .build());
          x11Client.send(ImageText8.builder()
            .drawable(window.getWid())
            .gc(gc.getCid())
            .string(Utilities.toByteList("Hello World!"))
            .x((short) 10)
            .y((short) 50)
            .build());
        } else if(event instanceof KeyPressEvent) {
          break;
        }
      }
    }
  }

  @Test
  void clientTestXFunctions() throws IOException {
    try(X11Client client = X11Client.connect()) {
      int wid = client.createSimpleWindow((short) 10, (short) 10, (short) 600, (short) 480, EventMask.EXPOSURE, EventMask.KEY_PRESS);
      client.storeName(wid, "Hello World!");
      int deleteAtom = client.getAtom("WM_DELETE_WINDOW").getId();
      client.setWMProtocols(wid, deleteAtom);
      client.mapWindow(wid);
      int gc = client.createGC(0, wid);
      while(true) {
        XEvent event = client.getNextEvent();
        if(event instanceof ExposeEvent) {
          client.fillRectangle(wid, gc, (short) 20, (short) 20, (short) 10, (short) 10);
          client.imageText8(wid, gc, (short) 10, (short) 50, "Hello World!");
        } else if(event instanceof KeyPressEvent) {
          break;
        } else if(event instanceof ClientMessageEvent) {
          ClientMessageEvent clientMessage = (ClientMessageEvent) event;
          if(clientMessage.getFormat() == 32) {
            ClientMessageData32 data = (ClientMessageData32) clientMessage.getData();
            if(data.getData32().get(0) == deleteAtom) {
              break;
            }
          }
        }
      }
    }
  }
}
