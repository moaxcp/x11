package com.github.moaxcp.x11client;

import lombok.NonNull;

public class X11ClientException extends RuntimeException {
  public X11ClientException(@NonNull String message) {
    super(message);
  }
  public X11ClientException(@NonNull String message, @NonNull Throwable cause) {
    super(message, cause);
  }
}
