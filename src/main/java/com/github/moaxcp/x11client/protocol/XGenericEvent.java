package com.github.moaxcp.x11client.protocol;

public interface XGenericEvent extends XEvent {
  byte getExtension();
  default int getLength() {
    return ((getSize() + 4 - getSize() % 4) / 4);
  }
  short getEventType();
}
