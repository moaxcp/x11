package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.*;
import com.github.moaxcp.x11client.protocol.xproto.*;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collections;
import lombok.NonNull;

import static com.github.moaxcp.x11client.protocol.Utilities.byteArrayToList;
import static com.github.moaxcp.x11client.protocol.Utilities.stringToByteList;

/**
 * An x11 client.
 */
public class X11Client implements AutoCloseable {
  private final X11Connection connection;
  private final XProtocolService service;
  private int nextResourceId;

  /**
   * Creates a client for the given displayName and authority.
   * @param displayName to connect to. non-null
   * @param xAuthority to use. non-null
   * @return
   * @throws X11ClientException
   */
  public static X11Client connect(@NonNull DisplayName displayName, @NonNull XAuthority xAuthority) {
    try {
      return new X11Client(X11Connection.connect(displayName, xAuthority));
    } catch (IOException e) {
      throw new X11ClientException("Could not connect with " + displayName, e);
    }
  }

  public static X11Client connect() {
    try {
      return new X11Client(X11Connection.connect());
    } catch (IOException e) {
      throw new X11ClientException("Could not connect", e);
    }
  }

  public static X11Client connect(@NonNull DisplayName name) {
    try {
      return new X11Client(X11Connection.connect(name));
    } catch (IOException e) {
      throw new X11ClientException("Could not connect with " + name, e);
    }
  }

  private X11Client(X11Connection connection) {
    this.connection = connection;
    service = new XProtocolService(connection.getSetup(), connection.getX11Input(), connection.getX11Output());
  }

  public boolean loadedPlugin(String name) {
    return service.loadedPlugin(name);
  }

  public boolean activatedPlugin(String name) {
    return service.activatedPlugin(name);
  }

  public Setup getSetup() {
    return connection.getSetup();
  }

  public int getDefaultScreen() {
    return 0;
  }

  public Screen getScreen(int screen) {
    return getSetup().getRoots().get(screen);
  }

  public int getRoot(int screen) {
    return getScreen(screen).getRoot();
  }

  public int getWhitePixel(int screen) {
    return getScreen(screen).getWhitePixel();
  }

  public int getBlackPixel(int screen) {
    return getScreen(screen).getBlackPixel();
  }

  public byte getDepth(int screen) {
    return getScreen(screen).getRootDepth();
  }

  public int getVisualId(int screen) {
    return getScreen(screen).getRootVisual();
  }

  public void send(OneWayRequest request) {
    service.send(request);
  }

  public <T extends XReply> T send(TwoWayRequest<T> request) {
    return service.send(request);
  }

  public XEvent getNextEvent() {
    return service.getNextEvent();
  }

  public void flush() {
    service.flush();
  }

  public int nextResourceId() {
    if (hasValidNextResourceFor(nextResourceId)) {
      return maskNextResourceId(nextResourceId++);
    }
    throw new UnsupportedOperationException("must use xc_misc to get resource id");
  }

  private boolean hasValidNextResourceFor(int resourceId) {
    return (resourceId + 1 & ~getSetup().getResourceIdMask()) == 0;
  }

  private int maskNextResourceId(int resourceId) {
    return resourceId | getSetup().getResourceIdBase();
  }

  /**
   * Closes the connection.
   * @throws IOException
   */
  @Override
  public void close() throws IOException {
    connection.close();
  }

  public int keyCodeToKeySym(int keyCode, int state) {
    return service.keyCodeToKeySym(keyCode, state);
  }

  public int keySymToKeyCode(int keyCode) {
    return service.keySymToKeyCode(keyCode);
  }

  /**
   * Creates a simple window on the default screen.
   * @param x
   * @param y
   * @param width
   * @param height
   * @param events
   * @return
   */
  public int createSimpleWindow(short x, short y, short width, short height, EventMask... events) {
    int wid = nextResourceId();
    send(CreateWindow.builder()
      .depth(getDepth(getDefaultScreen()))
      .wid(wid)
      .parent(getRoot(getDefaultScreen()))
      .x(x)
      .y(y)
      .width(width)
      .height(height)
      .borderWidth((short) 0)
      .clazz(WindowClass.COPY_FROM_PARENT)
      .visual(getVisualId(getDefaultScreen()))
      .backgroundPixel(getWhitePixel(getDefaultScreen()))
      .borderPixel(getBlackPixel(getDefaultScreen()))
      .eventMaskEnable(events)
      .build());
    return wid;
  }

  public int internAtom(String name) {
    return send(InternAtom.builder().name(stringToByteList(name)).nameLen((short) name.length()).build()).getAtom();
  }

  public void storeName(int wid, String name) {
    send(ChangeProperty.builder()
      .window(wid)
      .property(Atom.WM_NAME.getValue())
      .type(Atom.STRING.getValue())
      .format((byte) 8)
      .data(stringToByteList(name))
      .dataLen(name.length())
      .build());
  }

  public void setWMProtocols(int wid, int atom) {
    int wmProtocols = internAtom("WM_PROTOCOLS");
    send(ChangeProperty.builder()
      .window(wid)
      .property(wmProtocols)
      .type(Atom.ATOM.getValue())
      .format((byte) 32)
      .mode(PropMode.REPLACE)
      .data(byteArrayToList(ByteBuffer.allocate(4).putInt(atom).array()))
      .dataLen(1)
      .build());
  }

  public void mapWindow(int wid) {
    send(MapWindow.builder()
      .window(wid)
      .build());
  }

  public int createGC(int screen, int wid) {
    int gc = nextResourceId();
    send(CreateGC.builder()
      .cid(gc)
      .drawable(wid)
      .background(getWhitePixel(screen))
      .foreground(getBlackPixel(screen))
      .build());
    return gc;
  }

  /**
   * see https://github.com/mirror/libX11/blob/master/src/Text.c
   * @param drawable
   * @param gc
   * @param x
   * @param y
   * @param string
   */
  public void drawString(int drawable, int gc, short x, short y, String string) {
    send(ImageText8.builder()
      .drawable(drawable)
      .gc(gc)
      .x(x)
      .y(y)
      .stringLen((byte) string.length())
      .string(stringToByteList(string))
      .build());
  }

  public void fillRectangle(int drawable, int gc, short x, short y, short width, short height) {
    send(PolyFillRectangle.builder()
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
}
