package com.github.moaxcp.x11client.protocol;

import java.io.IOException;

public interface XProtocolReader {
  String getName();
  boolean supportedEvent(byte number);
  boolean supportedError(byte code);
  XEvent readEvent(byte number, boolean sentEvent, X11Input in) throws IOException;
  XError readError(byte code, X11Input in) throws IOException;
}
