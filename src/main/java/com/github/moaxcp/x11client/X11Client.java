package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XRequest;
import com.github.moaxcp.x11client.protocol.XResponse;
import com.github.moaxcp.x11client.protocol.xproto.ScreenStruct;
import com.github.moaxcp.x11client.protocol.xproto.SetupStruct;
import lombok.NonNull;

import java.io.IOException;

public class X11Client implements AutoCloseable {
  private final X11Connection connection;
  private final XProtocolService service;

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
    service = new XProtocolService(connection.getSetupStruct(), connection.getX11Input(), connection.getX11Output());
  }

  X11Input getX11Input() {
    return connection.getX11Input();
  }

  X11Output getX11Output() {
    return connection.getX11Output();
  }

  public SetupStruct getSetup() {
    return connection.getSetupStruct();
  }

  public int send(XRequest request) throws IOException {
    return service.send(request);
  }

  public <T extends XResponse> T read() throws IOException {
    return service.read();
  }

  public int nextResourceId() {
    return service.getNextResourceId();
  }

  @Override
  public void close() throws IOException {
    connection.close();
  }

  public ScreenStruct getDefaultScreen() {
    return service.getDefaultScreen();
  }

  public int getDefaultRoot() {
    return service.getDefaultRoot();
  }

  public byte getDefaultDepth() {
    return service.getDefaultDepth();
  }

  public int getDefaultVisualId() {
    return service.getDefaultVisualId();
  }
}
