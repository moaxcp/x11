package com.github.moaxcp.x11.x11client;

import static java.util.Objects.requireNonNull;

/**
 * Thrown when there is a client exception. This could mean an IOException or unexpected results from the server. It is
 * not for XErrors returned by the Server. These exceptions could be caused by a closed connection to the server.
 */
public class X11ClientException extends RuntimeException {
  /**
   * Creates an exception.
   * @param message of exception. non-null
   */
  X11ClientException(String message) {
    super(requireNonNull(message, "message"));
  }

  /**
   * Creates an exception.
   * @param message of exception. non-null
   * @param cause of exception. non-null
   */
  X11ClientException(String message, Throwable cause) {
    super(requireNonNull(message, "message"), requireNonNull(cause, "cause"));
  }
}
