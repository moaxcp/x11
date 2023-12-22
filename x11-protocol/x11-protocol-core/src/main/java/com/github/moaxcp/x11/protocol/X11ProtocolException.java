package com.github.moaxcp.x11.protocol;

public class X11ProtocolException extends RuntimeException {

  public X11ProtocolException(String message) {
    super(message);
  }

  public X11ProtocolException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
