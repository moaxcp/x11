package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.*;
import com.github.moaxcp.x11client.protocol.xproto.*;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.NonNull;

import static com.github.moaxcp.x11client.protocol.Utilities.byteArrayToList;
import static com.github.moaxcp.x11client.protocol.Utilities.stringToByteList;

/**
 * An x11 client.
 */
public class X11Client implements AutoCloseable {
  private final X11Connection connection;
  private final XProtocolService protocolService;
  private final ResourceIdService resourceIdService;
  private final AtomService atomService;
  private final Map<Integer, Integer> defaultGCs = new HashMap<>();

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
    protocolService = new XProtocolService(connection.getSetup(), connection.getX11Input(), connection.getX11Output());
    resourceIdService = new ResourceIdService(protocolService, connection.getSetup().getResourceIdMask(), connection.getSetup().getResourceIdBase());
    atomService = new AtomService(protocolService);
  }

  public boolean loadedPlugin(String name) {
    return protocolService.loadedPlugin(name);
  }

  public boolean activatedPlugin(String name) {
    return protocolService.activatedPlugin(name);
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
    protocolService.send(request);
  }

  public <T extends XReply> T send(TwoWayRequest<T> request) {
    return protocolService.send(request);
  }

  public XEvent getNextEvent() {
    return protocolService.getNextEvent();
  }

  public void flush() {
    protocolService.flush();
  }

  /**
   * Closes the connection.
   * @throws IOException
   */
  @Override
  public void close() throws IOException {
    defaultGCs.values().stream().forEach(i -> send(FreeGC.builder().gc(i).build()));
    connection.close();
  }

  public int keyCodeToKeySym(int keyCode, int state) {
    return protocolService.keyCodeToKeySym(keyCode, state);
  }

  public int keySymToKeyCode(int keyCode) {
    return protocolService.keySymToKeyCode(keyCode);
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
  public int createSimpleWindow(int x, int y, int width, int height, EventMask... events) {
    int wid = nextResourceId();
    send(CreateWindow.builder()
      .depth(getDepth(getDefaultScreen()))
      .wid(wid)
      .parent(getRoot(getDefaultScreen()))
      .x((short) x)
      .y((short) y)
      .width((short) width)
      .height((short) height)
      .borderWidth((short) 0)
      .clazz(WindowClass.COPY_FROM_PARENT)
      .visual(getVisualId(getDefaultScreen()))
      .backgroundPixel(getWhitePixel(getDefaultScreen()))
      .borderPixel(getBlackPixel(getDefaultScreen()))
      .eventMaskEnable(events)
      .build());
    return wid;
  }

  public int nextResourceId() {
    return resourceIdService.nextResourceId();
  }

  /**
   * Returns the id of the named Atom. If the atom does not exist on the x11 server an InternAtom request is made.
   * @param name
   * @return
   */
  public int getAtom(String name) {
    return atomService.getAtom(name).getId();
  }

  //XRaiseWindow https://github.com/mirror/libX11/blob/caa71668af7fd3ebdd56353c8f0ab90824773969/src/RaiseWin.c
  public void raiseWindow(int wid) {
    send(ConfigureWindow.builder()
        .window(wid)
        .stackMode(StackMode.ABOVE)
        .build());
  }

  public void storeName(int wid, String name) {
    send(ChangeProperty.builder()
      .window(wid)
      .property(Atom.WM_NAME.getValue())
      .type(Atom.STRING.getValue())
      .format((byte) 8)
      .dataLen(name.length())
      .data(stringToByteList(name))
      .build());
  }

  public void setWMProtocols(int wid, int atom) {
    int wmProtocols = getAtom("WM_PROTOCOLS");
    send(ChangeProperty.builder()
      .window(wid)
      .property(wmProtocols)
      .type(Atom.ATOM.getValue())
      .format((byte) 32)
      .mode(PropMode.REPLACE)
      .dataLen(1)
      .data(byteArrayToList(ByteBuffer.allocate(4).putInt(atom).array()))
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

  public int defaultGC(int screen) {
    int root = getRoot(screen);
    if(defaultGCs.containsKey(root)) {
      return defaultGCs.get(root);
    }
    int gc = createGC(screen, root);
    defaultGCs.put(root, gc);
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
