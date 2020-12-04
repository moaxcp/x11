package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.*;
import com.github.moaxcp.x11client.protocol.xproto.ScreenStruct;
import com.github.moaxcp.x11client.protocol.xproto.SetupStruct;
import lombok.NonNull;

import java.io.IOException;

public class X11Client implements AutoCloseable {
  private final X11Connection connection;
  private final XProtocolService service;
  private int nextResourceId;
  private final DisplayConventions conventions;

  public static X11Client connect(@NonNull DisplayName displayName, @NonNull XAuthority xAuthority) throws IOException {
    return new X11Client(X11Connection.connect(displayName, xAuthority));
  }

  public static X11Client connect() throws IOException {
    return new X11Client(X11Connection.connect());
  }

  public static X11Client connect(@NonNull DisplayName name) throws IOException {
    return new X11Client(X11Connection.connect(name));
  }

  private X11Client(X11Connection connection) throws IOException {
    this.connection = connection;
    conventions = new DisplayConventions(connection.getSetupStruct());
    service = new XProtocolService(connection.getSetupStruct(), connection.getX11Input(), connection.getX11Output());
  }

  public SetupStruct getSetup() {
    return connection.getSetupStruct();
  }

  public ScreenStruct getDefaultScreen() {
    return conventions.getDefaultScreen();
  }

  public int getDefaultRoot() {
    return conventions.getDefaultRoot();
  }

  public byte getDefaultDepth() {
    return conventions.getDefaultDepth();
  }

  public int getDefaultVisualId() {
    return conventions.getDefaultVisualId();
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
    if (conventions.hasValidNextResourceFor(nextResourceId)) {
      return conventions.maskNextResourceId(nextResourceId++);
    }
    throw new UnsupportedOperationException("must use xc_misc to get resource id");
  }

  @Override
  public void close() throws IOException {
    connection.close();
  }
}
