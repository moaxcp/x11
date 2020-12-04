package com.github.moaxcp.x11client.protocol;

import com.github.moaxcp.x11client.DisplayName;
import com.github.moaxcp.x11client.X11Client;
import com.github.moaxcp.x11client.XAuthority;
import java.io.IOException;
import lombok.NonNull;

public class Display implements AutoCloseable {
  private final X11Client client;

  public static Display connect(@NonNull DisplayName displayName, @NonNull XAuthority xAuthority) throws IOException {
    return new Display(X11Client.connect(displayName, xAuthority));
  }

  public static Display connect() throws IOException {
    return new Display(X11Client.connect());
  }

  public static Display connect(@NonNull DisplayName name) throws IOException {
    return new Display(X11Client.connect(name));
  }

  public Display(X11Client client) {
    this.client = client;
  }

  @Override
  public void close() throws IOException {
    client.close();
  }
}
