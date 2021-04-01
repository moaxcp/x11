package com.github.moaxcp.x11client;

import lombok.NonNull;

/**
 * Thrown when there is a client exception. This could mean an IOException or unexpected results from the server. It is
 * not for XErrors returned by the Server. These exceptions could be caused by a closed connection to the server.
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
