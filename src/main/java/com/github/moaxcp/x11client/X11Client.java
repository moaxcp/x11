package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.XProtocolService;
import com.github.moaxcp.x11client.protocol.XRequest;
import com.github.moaxcp.x11client.protocol.XResponse;
import lombok.NonNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;

public class X11Client implements AutoCloseable {
  private final X11Connection connection;
  private short nextSequenceNumber = 1;
  private int nextResourceNumber = 1;
  private final XProtocolService service = new XProtocolService();

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

  public Optional<XResponse> read() throws IOException {
    X11Input in = connection.getX11Input();
    byte responseCode = in.readByte();
    if(responseCode == 0) {

    } else if(responseCode == 1) {

    } else if(responseCode > 1) {

    }
    return Optional.empty();
  }

  public int nextResource() {
    return nextResourceNumber++;
  }

  @Override
  public void close() throws Exception {
    connection.close();
  }
}
