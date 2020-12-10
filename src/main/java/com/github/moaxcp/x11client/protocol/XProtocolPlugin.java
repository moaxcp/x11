package com.github.moaxcp.x11client.protocol;

import java.io.IOException;

public interface XProtocolPlugin {
  String getName();
  byte getOffset();
  void setOffset(byte offset);
  boolean supportedRequest(XRequest request);
  boolean supportedEvent(byte number);
  boolean supportedError(byte code);
  XEvent readEvent(byte number, boolean sentEvent, X11Input in) throws IOException;
  XError readError(byte code, X11Input in) throws IOException;
}
