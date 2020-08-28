package com.github.moaxcp.x11client.protocol;

public interface XEvent extends XObject {
  default int getSize() {
    return 32;
  }
  byte getNumber();
}
