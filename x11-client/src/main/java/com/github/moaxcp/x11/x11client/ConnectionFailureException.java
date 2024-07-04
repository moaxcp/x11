package com.github.moaxcp.x11.x11client;

import com.github.moaxcp.x11.protocol.xproto.SetupFailed;

/**
 * An exception thrown when there is a failure response when connecting to the x11 server.
 */
public class ConnectionFailureException extends X11ClientException {
  /**
   * The failure info from the x11 server. non-null.
   */
  private final SetupFailed failure;

  /**
   * Creates a connection failure exception.
   *
   * @param failure non-null failure information
   */
  ConnectionFailureException(SetupFailed failure) {
    super(failure.toString());
    this.failure = failure;
  }

  public SetupFailed getFailure() {
    return this.failure;
  }
}
