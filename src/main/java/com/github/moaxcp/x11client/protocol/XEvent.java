package com.github.moaxcp.x11client.protocol;

public interface XEvent extends XObject {
  int getSize();
  byte getNumber();
  byte getEventDetail();
  short getSequenceNumber();
}
