package com.github.moaxcp.x11client.protocol;

import java.io.IOException;

public interface XProtocolPlugin {
  String getName();
  byte getMajorVersion();
  byte getMinorVersion();
  byte getMajorOpcode();
  void setMajorOpcode(byte firstOpcode);
  byte getFirstEvent();
  void setFirstEvent(byte firstEvent);
  byte getFirstError();
  void setFirstError(byte firstError);
  boolean supportedRequest(XRequest request);
  boolean supportedEvent(byte number);
  boolean supportedError(byte code);
  XEvent readEvent(byte number, boolean sentEvent, X11Input in) throws IOException;
  XError readError(byte code, X11Input in) throws IOException;
  XGenericEvent readGenericEvent(boolean sentEvent, byte extension, short sequenceNumber, int length, short eventType, X11Input in) throws IOException;
}
