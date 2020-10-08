package com.github.moaxcp.x11client.protocol;

import java.io.IOException;

public interface XEvent extends XObject, XResponse {
  int getSize();
  byte getEventDetail();
  short getSequenceNumber();
  void write(X11Output out) throws IOException;
}
