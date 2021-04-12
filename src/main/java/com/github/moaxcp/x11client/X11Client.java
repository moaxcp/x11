package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.*;
import com.github.moaxcp.x11client.protocol.xproto.*;
import lombok.NonNull;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.moaxcp.x11client.protocol.Utilities.*;

/**
 * An x11 client. The client handles two types of requests: {@link OneWayRequest} and {@link TwoWayRequest}.
 * <p>
 *   {@link OneWayRequest}s are never sent directly to the server. Instead, they are added to a request queue. Requests
 *   in this queue are sent to the server when any "flushing" method is called. These are {@link #flush()},
 *   {@link #sync()}, {@link #getNextEvent()}, or {@link #send(TwoWayRequest)}.
 * </p>
 * <p>
 *   {@link TwoWayRequest}s are sent to the server immediately. Input from the server is read until the reply is found
 *   and returned. While searching for the reply {@link XEvent} objects may be found. These events are added to to the
 *   event queue. {@link XError}s may also be found while searching for a reply. These objects cause an
 *   {@link X11ErrorException} to be thrown.
 * </p>
 * <p>
 *   The event queue holds {@link XEvent}s that were read from the server but not yet sent to the user. Methods such as
 *   {@link #getNextEvent()} read from this queue until it is empty before reading events from the server.
 * </p>
 */
public class X11Client implements AutoCloseable {
  private final X11Connection connection;
  private final XProtocolService protocolService;
  private final ResourceIdService resourceIdService;
  private final AtomService atomService;
  private final KeyboardService keyboardService;
  private final Map<Integer, Integer> defaultGCs = new HashMap<>();

  /**
   * Creates a client for the given {@link DisplayName} and {@link XAuthority}.
   * @param displayName to connect to. non-null
   * @param xAuthority to use. non-null
   * @return The connected client
   * @throws X11ClientException
   */
  public static X11Client connect(@NonNull DisplayName displayName, @NonNull XAuthority xAuthority) {
    try {
      return new X11Client(X11Connection.connect(displayName, xAuthority));
    } catch (IOException e) {
      throw new X11ClientException("Could not connect with " + displayName, e);
    }
  }

  /**
   * Connects to the standard {@link DisplayName}.
   * @return The connected client
   * @see DisplayName#standard() for the standard display name.
   * @throws X11ClientException
   */
  public static X11Client connect() {
    try {
      return new X11Client(X11Connection.connect());
    } catch (IOException e) {
      throw new X11ClientException("Could not connect", e);
    }
  }

  /**
   * Connects to the provided {@link DisplayName}
   * @param name
   * @return
   */
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
    keyboardService = new KeyboardService(protocolService);
  }

  /**
   * Checks if a plugin is loaded. Plugins generated from xcb use the extension-xname attribute. For example
   * "BIG-REQUEST".
   * @param name of plugin
   * @return true if plugin is loaded.
   */
  public boolean loadedPlugin(String name) {
    return protocolService.loadedPlugin(name);
  }

  /**
   * Returns the connection setup.
   * @return
   */
  public Setup getSetup() {
    return connection.getSetup();
  }

  /**
   * Returns the default screen number.
   * @return
   */
  public int getDefaultScreenNumber() {
    return connection.getDisplayName().getScreenNumber();
  }

  /**
   * Returns the sceen for the number provided.
   * @param screen
   * @return
   */
  public Screen getScreen(int screen) {
    return getSetup().getRoots().get(screen);
  }

  /**
   * Returns the default {@link Screen} object.
   * @return
   */
  public Screen getDefaultScreen() {
    return getScreen(getDefaultScreenNumber());
  }

  /**
   * Returns the root for the provided screen.
   * @param screen
   * @return
   */
  public int getRoot(int screen) {
    return getScreen(screen).getRoot();
  }

  /**
   * Returns the default root.
   * @return
   */
  public int getDefaultRoot() {
    return getRoot(getDefaultScreenNumber());
  }

  /**
   * Returns the white pixel for the provided screen.
   * @param screen
   * @return
   */
  public int getWhitePixel(int screen) {
    return getScreen(screen).getWhitePixel();
  }

  /**
   * Returns the default white pixel.
   * @return
   */
  public int getDefaultWhitePixel() {
    return getWhitePixel(getDefaultScreenNumber());
  }

  /**
   * Returns the black pixel for the provided screen.
   * @param screen
   * @return
   */
  public int getBlackPixel(int screen) {
    return getScreen(screen).getBlackPixel();
  }

  /**
   * Returns the default black pixel.
   * @return
   */
  public int getDefaultBlackPixel() {
    return getBlackPixel(getDefaultScreenNumber());
  }

  /**
   * Returns the depth for the provided screen.
   * @param screen
   * @return
   */
  public byte getDepth(int screen) {
    return getScreen(screen).getRootDepth();
  }

  /**
   * Returns the default depth.
   * @return
   */
  public byte getDefaultDepth() {
    return getDepth(getDefaultScreenNumber());
  }

  /**
   * Returns the visualId for the provided screen.
   * @param screen
   * @return
   */
  public int getVisualId(int screen) {
    return getScreen(screen).getRootVisual();
  }

  /**
   * Returns the default visualId.
   * @return
   */
  public int getDefaultVisualId() {
    return getVisualId(getDefaultScreenNumber());
  }

  /**
   * Adds a {@link OneWayRequest} request to the request queue.
   * @param request for server to execute
   */
  public void send(OneWayRequest request) {
    protocolService.send(request);
  }

  /**
   * Calls {@link #flush()} then sends the {@link TwoWayRequest} to the server returning the reply to the request. If
   * any events are found while reading the reply from the server they are saved in the event queue.
   * @param request for the server to execute
   * @param <T> expected type of reply
   * @return the reply for the request
   * @throws X11ErrorException if the server had an error with any of the requests sent.
   * @throws X11ClientException for any {@link IOException} encountered while processing the requests.
   */
  public <T extends XReply> T send(TwoWayRequest<T> request) {
    return protocolService.send(request);
  }

  /**
   * Reads the next event from the server and returns it.
   * @return
   */
  public XEvent getNextEvent() {
    return protocolService.getNextEvent();
  }

  public boolean hasResponse() {
    return connection.inputAvailable() >= 32; //events and errors are always 32 bytes
  }

  /**
   * Sends all {@link OneWayRequest}s from the request queue to the server.
   */
  public void flush() {
    protocolService.flush();
  }

  /**
   * https://github.com/mirror/libX11/blob/caa71668af7fd3ebdd56353c8f0ab90824773969/src/Sync.c
   */
  public void sync() {
    GetInputFocusReply reply = send(GetInputFocus.builder().build());
  }

  /**
   * discards all events in the event queue.
   */
  public void discard() {
    protocolService.discard();
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

  public KeySym keyCodeToKeySym(byte keyCode, short state) {
    return keyboardService.keyCodeToKeySym(keyCode, state);
  }

  public KeySym keyCodeToKeySym(KeyPressEvent event) {
    return keyboardService.keyCodeToKeySym(event);
  }

  public KeySym keyCodeToKeySym(KeyReleaseEvent event) {
    return keyboardService.keyCodeToKeySym(event);
  }

  public List<Byte> keySymToKeyCodes(KeySym keySym) {
    return keyboardService.keySymToKeyCodes(keySym);
  }

  public KeySym getKeySym(byte keyCode, int col) {
    return keyboardService.getKeySym(keyCode, col);
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
      .depth(getDefaultDepth())
      .wid(wid)
      .parent(getDefaultRoot())
      .x((short) x)
      .y((short) y)
      .width((short) width)
      .height((short) height)
      .borderWidth((short) 0)
      .clazz(WindowClass.COPY_FROM_PARENT)
      .visual(getDefaultVisualId())
      .backgroundPixel(getDefaultWhitePixel())
      .borderPixel(getDefaultBlackPixel())
      .eventMaskEnable(events)
      .build());
    return wid;
  }

  public int nextResourceId() {
    return resourceIdService.nextResourceId();
  }

  /**
   * Returns the {@link AtomValue} of the named Atom. If the atom does not exist on the x11 server an InternAtom request is made.
   * @param name
   * @return
   */
  public AtomValue getAtom(String name) {
    return atomService.getAtom(name);
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

  public List<Integer> getWMProtocols(int wid) {
    GetPropertyReply property = send(GetProperty.builder()
      .window(wid)
      .property(getAtom("WM_PROTOCOLS").getId())
      .longOffset(0)
      .longLength(1000000)
      .delete(false)
      .build());
    if(property.getFormat() != 32) {
      throw new X11ClientException("expected format for property \"WM_PROTOCOLS\" to be 32 but was \"" + property.getFormat() + "\"");
    }
    if(property.getType() != Atom.ATOM.getValue()) {
      throw new X11ClientException("expected type for property \"WM_PROTOCOLS\" to be \"" + Atom.ATOM.getValue() + "\" but was \"" + property.getType() + "\"");
    }
    return toIntegers(property.getValue());
  }

  public void setWMProtocols(int wid, int atom) {
    send(ChangeProperty.builder()
      .window(wid)
      .property(getAtom("WM_PROTOCOLS").getId())
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

  public int defaultGC() {
    return defaultGC(getDefaultScreenNumber());
  }

  /**
   * see https://github.com/mirror/libX11/blob/master/src/Text.c
   * @param drawable
   * @param gc
   * @param x
   * @param y
   * @param string
   */
  public void imageText8(int drawable, int gc, short x, short y, String string) {
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

  public void killClient(int resource) {
    send(KillClient.builder()
      .resource(resource)
      .build());
  }

  public void inputFocus(int wid) {
    send(SetInputFocus.builder()
      .focus(wid)
      .revertTo(InputFocus.POINTER_ROOT)
      .build());
  }
}
