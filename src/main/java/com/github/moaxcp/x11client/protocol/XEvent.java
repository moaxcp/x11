package com.github.moaxcp.x11client.protocol;

import java.io.IOException;

public interface XEvent extends XObject {
  int getSize();
  byte getNumber();
  byte getEventDetail();
  short getSequenceNumber();
  void write(X11Output out) throws IOException;
}
