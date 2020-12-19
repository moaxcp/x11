package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.XError;
import lombok.Getter;
import lombok.NonNull;

/**
 * An exception thrown when the x11 server returns an {@link XError} which has not been recovered from.
 */
@Getter
public class X11ErrorException extends RuntimeException {
  /**
   * The error returned from the server
   */
  private XError error;

  /**
   * Creates an X11Error exception.
   * @param error from server. non-null
   */
  X11ErrorException(@NonNull XError error) {
    super(error.toString());
    this.error = error;
  }
}
