package com.github.moaxcp.x11client;

import lombok.Getter;

public class ConnectionFailureException extends X11ClientException {
  @Getter
  private final ConnectionFailure failure;
  public ConnectionFailureException(ConnectionFailure failure) {
    super(failure.toString());
    this.failure = failure;
  }
}
