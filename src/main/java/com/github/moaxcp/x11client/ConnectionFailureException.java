package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.xproto.SetupFailed;
import lombok.Getter;
import lombok.NonNull;

/**
 * An exception thrown when there is a failure response when connecting to the x11 server.
 */
public class ConnectionFailureException extends X11ClientException {
  /**
   * The failure info from the x11 server. non-null.
   */
  @Getter
  private final SetupFailed failure;

  /**
   * Creates a connection failure exception.
   * @param failure non-null failure information
   */
  ConnectionFailureException(@NonNull SetupFailed failure) {
    super(failure.toString());
    this.failure = failure;
  }
}
