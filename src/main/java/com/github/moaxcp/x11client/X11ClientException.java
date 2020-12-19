package com.github.moaxcp.x11client;

import lombok.NonNull;

/**
 * Thrown when there is an IOException and not an XError returned by the Server. These exceptions could be caused by
 * a closed connection to the server.
 */
public class X11ClientException extends RuntimeException {
  /**
   * Creates an exception.
   * @param message of exception. non-null
   */
  X11ClientException(@NonNull String message) {
    super(message);
  }

  /**
   * Creates an exception.
   * @param message of exception. non-null
   * @param cause of exception. non-null
   */
  X11ClientException(@NonNull String message, @NonNull Throwable cause) {
    super(message, cause);
  }
}
