package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.xproto.SetupFailedStruct;
import lombok.Getter;

public class ConnectionFailureException extends X11ClientException {
  @Getter
  private final SetupFailedStruct failure;
  public ConnectionFailureException(SetupFailedStruct failure) {
    super(failure.toString());
    this.failure = failure;
  }
}
