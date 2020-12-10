package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.xproto.SetupFailed;
import lombok.Getter;

public class ConnectionFailureException extends X11ClientException {
  @Getter
  private final SetupFailed failure;
  public ConnectionFailureException(SetupFailed failure) {
    super(failure.toString());
    this.failure = failure;
  }
}
