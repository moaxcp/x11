package com.github.moaxcp.x11client.experimental;

import com.github.moaxcp.x11client.X11Client;
import com.github.moaxcp.x11client.protocol.XEvent;
import com.github.moaxcp.x11client.protocol.xproto.*;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collections;

import static com.github.moaxcp.x11client.Utilities.byteArrayToList;
import static com.github.moaxcp.x11client.Utilities.stringToByteList;

/**
 * Implementation of the xlib methods
 */
public class XDisplay implements AutoCloseable {
  private final X11Client client;
  public XDisplay(X11Client client) {
    this.client = client;
  }

  public int createSimpleWindow(int screen, short x, short y, short width, short height, EventMask... events) {
    int wid = client.nextResourceId();
    client.send(CreateWindow.builder()
      .depth(client.getDepth(screen))
      .wid(wid)
      .parent(client.getRoot(screen))
      .x(x)
      .y(y)
      .width(width)
      .height(height)
      .borderWidth((short) 0)
      .clazz(WindowClass.COPY_FROM_PARENT)
      .visual(client.getVisualId(screen))
      .backgroundPixel(client.getWhitePixel(screen))
      .borderPixel(client.getBlackPixel(screen))
      .eventMaskEnable(events)
      .build());
    return wid;
  }

  public int internAtom(String name) {
    return client.send(InternAtom.builder().name(stringToByteList(name)).nameLen((short) name.length()).build()).getAtom();
  }

  public void storeName(int wid, String name) {
    client.send(ChangeProperty.builder()
      .window(wid)
      .property(Atom.WM_NAME.getValue())
      .type(Atom.STRING.getValue())
      .format((byte) 8)
      .data(stringToByteList(name))
      .dataLen(name.length())
      .build());
  }

  public void setWMProtocols(int wid, int atom) {
    InternAtomReply wmProtocols = client.send(InternAtom.builder().name(stringToByteList("WM_PROTOCOLS")).nameLen((short) "WM_PROTOCOLS".length()).build());
    client.send(ChangeProperty.builder()
      .window(wid)
      .property(wmProtocols.getAtom())
      .type(Atom.ATOM.getValue())
      .format((byte) 32)
      .mode(PropMode.REPLACE)
      .data(byteArrayToList(ByteBuffer.allocate(4).putInt(atom).array()))
      .dataLen(1)
      .build());
  }

  public void mapWindow(int wid) {
    client.send(MapWindow.builder()
      .window(wid)
      .build());
  }

  public int createGC(int screen, int wid) {
    int gc = client.nextResourceId();
    client.send(CreateGC.builder()
      .cid(gc)
      .drawable(wid)
      .background(client.getWhitePixel(screen))
      .foreground(client.getBlackPixel(screen))
      .build());
    return gc;
  }

  public void drawString(int drawable, int gc, short x, short y, String string) {
    client.send(ImageText8.builder()
      .drawable(drawable)
      .gc(gc)
      .x(x)
      .y(y)
      .stringLen((byte) string.length())
      .string(stringToByteList(string))
      .build());
  }

  public void fillRectangle(int drawable, int gc, short x, short y, short width, short height) {
    client.send(PolyFillRectangle.builder()
      .drawable(drawable)
      .gc(gc)
      .rectangles(Collections.singletonList(Rectangle.builder()
        .x(x)
        .y(y)
        .width(width)
        .height(height)
        .build()))
      .build());
  }

  @Override
  public void close() throws IOException {
    client.close();
  }

  public XEvent getNextEvent() {
    return client.getNextEvent();
  }
}
