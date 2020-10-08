package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.XRequest;
import lombok.NonNull;

import java.io.IOException;

public class X11Client implements AutoCloseable {
  private final X11Connection connection;
  private short nextSequenceNumber = 1;
  private int nextResourceNumber = 1;

  public static X11Client connect(@NonNull DisplayName displayName, @NonNull XAuthority xAuthority) throws IOException {
    return new X11Client(X11Connection.connect(displayName, xAuthority));
  }

  public static X11Client connect() throws IOException {
    return new X11Client(X11Connection.connect());
  }

  public static X11Client connect(@NonNull DisplayName name) throws IOException {
    return new X11Client(X11Connection.connect(name));
  }

  X11Client(X11Connection connection) {
    this.connection = connection;
  }

  public int send(XRequest request) throws IOException {
    request.write(connection.getX11Output());
    return nextSequenceNumber++;
  }

  public int nextResource() {
    return nextResourceNumber++;
  }

  @Override
  public void close() throws Exception {
    connection.close();
  }
}
