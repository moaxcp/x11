package com.github.moaxcp.x11.x11client;

import com.github.moaxcp.x11.keysym.KeySym;
import com.github.moaxcp.x11.protocol.*;
import com.github.moaxcp.x11.protocol.xproto.FreeGC;
import com.github.moaxcp.x11.protocol.xproto.KeyPressEvent;
import com.github.moaxcp.x11.protocol.xproto.KeyReleaseEvent;
import com.github.moaxcp.x11.protocol.xproto.Setup;
import com.github.moaxcp.x11.x11client.api.XprotoApi;
import com.github.moaxcp.x11.x11client.api.record.RecordApi;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
public class X11Client implements AutoCloseable, RecordApi, XprotoApi {
  private final X11Connection connection;
  private final XProtocolService protocolService;
  private final ResourceIdService resourceIdService;
  private final AtomService atomService;
  private final KeyboardService keyboardService;
  private final Map<Integer, Integer> defaultGCs = new HashMap<>();
  private RecordApi recordApi;

  /**
   * Creates a client for the given {@link DisplayName} and {@link XAuthority}.
   * @param displayName to connect to. non-null
   * @param xAuthority to use. non-null
   * @return The connected client
   * @throws X11ClientException If connection to server could not be established
   */
  public static X11Client connect(DisplayName displayName, XAuthority xAuthority) {
    return connect(false, displayName, xAuthority);
  }

  public static X11Client connect(boolean bigEndian, DisplayName displayName, XAuthority xAuthority) {
    try {
      return new X11Client(X11Connection.connect(bigEndian, displayName, xAuthority));
    } catch (IOException e) {
      throw new X11ClientException("Could not connect with " + displayName, e);
    }
  }

  /**
   * Connects to the standard {@link DisplayName}.
   * @return The connected client
   * @see DisplayName#standard() for the standard display name.
   * @throws X11ClientException If connection to server could not be established
   */
  public static X11Client connect() {
    return connect(false);
  }

  public static X11Client connect(boolean bigEndian) {
    try {
      return new X11Client(X11Connection.connect(bigEndian));
    } catch (IOException e) {
      throw new X11ClientException("Could not connect", e);
    }
  }

  /**
   * Connects to the provided {@link DisplayName}
   * @param name of display
   * @return the connected client
   * @throws X11ClientException If connection to server could not be established
   */
  public static X11Client connect(DisplayName name) {
    return connect(false, name);
  }

  public static X11Client connect(boolean bigEndian, DisplayName name) {
    try {
      return new X11Client(X11Connection.connect(bigEndian, name));
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
   * @return setup created by x11 server
   */
  @Override
  public Setup getSetup() {
    return connection.getSetup();
  }

  @Override
  public boolean getBigEndian() {
    return connection.getBigEndian();
  }

  /**
   * Returns the default screen number.
   */
  @Override
  public int getDefaultScreenNumber() {
    return connection.getDisplayName().getScreenNumber();
  }

  @Override
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
  @Override
  public <T extends XReply> T send(TwoWayRequest<T> request) {
    return protocolService.send(request);
  }

  /**
   * Returns the next event. First, events from the event queue are returned. Then the request queue is flushed to the
   * server. Then the server is polled for the next event. If an error response is returned from the server an
   * {@link X11ErrorException} is thrown. If a {@link XReply} response is returned or an {@link IOException} is thrown
   * an {@link X11ClientException} is thrown.
   * @return the next event
   * @throws X11ErrorException if the server returns an {@link XError}
   * @throws X11ClientException if the server returns an {@link XReply} or an {@link IOException} is thrown
   */
  public XEvent getNextEvent() {
    return protocolService.getNextEvent();
  }

  @Override
  public <T extends XReply> T getNextReply(XReplyFunction<T> replyFunction) {
    return protocolService.readReply(replyFunction);
  }

  @Override
  public <T extends XRequest> T readRequest(X11Input in) throws IOException {
      return protocolService.readRequest(in);
  }

  public byte getMajorOpcode(XRequest request) {
    return protocolService.getMajorOpcode(request);
  }

  @Override
  public <T extends XReply> T readReply(X11Input in, XReplyFunction<T> replyFunction) {
    return protocolService.readReply(in, replyFunction);
  }

  /**
   * Reads an event from the given bytes.
   * @param in input for the event
   * @return the event
   */
  @Override
  public <T extends XEvent> T readEvent(X11Input in) {
    return protocolService.readEvent(in);
  }

  @Override
  public Optional<Byte> getFirstEvent(String pluginName) {
    return protocolService.getFirstEvent(pluginName);
  }

  @Override
  public <T extends XError> T readError(X11Input in) {
    return protocolService.readError(in);
  }

  /**
   * Returns true if the connection has a response available.
   */
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
   * discards all events in the event queue.
   */
  public void discard() {
    protocolService.discard();
  }

  /**
   * Closes the client.
   * @throws IOException on issues with closing connections
   */
  @Override
  public void close() throws IOException {
    defaultGCs.values().stream().forEach(i -> send(FreeGC.builder().gc(i).build()));
    connection.close();
  }

  /**
   * Returns the {@link KeySym} assiciated with the keyCode and state.
   * @param keyCode of KeySym
   * @param state of keyboard
   */
  public KeySym keyCodeToKeySym(byte keyCode, short state) {
    return keyboardService.keyCodeToKeySym(keyCode, state);
  }

  /**
   * Returns the {@link KeySym} for the {@link KeyPressEvent}.
   * @param event from server
   */
  public KeySym keyCodeToKeySym(KeyPressEvent event) {
    return keyboardService.keyCodeToKeySym(event);
  }

  /**
   * Returns the {@link KeySym} for the {@link KeyReleaseEvent}.
   * @param event from server
   */
  public KeySym keyCodeToKeySym(KeyReleaseEvent event) {
    return keyboardService.keyCodeToKeySym(event);
  }

  /**
   * Returns all keyCodes for the given {@link KeySym}.
   * @param keySym to lookup
   */
  public List<Byte> keySymToKeyCodes(KeySym keySym) {
    return keyboardService.keySymToKeyCodes(keySym);
  }

  /**
   * Returns the {@link KeySym} for the keyCode and col.
   * @param keyCode to lookup
   * @param col column
   */
  public KeySym getKeySym(byte keyCode, int col) {
    return keyboardService.getKeySym(keyCode, col);
  }

  /**
   * Returns the next resource id. The client will use the Xcmisc extension to get the id range if the plugin is loaded.
   */
  @Override
  public int nextResourceId() {
    return resourceIdService.nextResourceId();
  }

  /**
   * Returns the {@link AtomValue} of the named Atom. If the atom does not exist on the x11 server an InternAtom request is made.
   * @param name of atom
   */
  @Override
  public AtomValue getAtom(String name) {
    return atomService.getAtom(name);
  }
}
