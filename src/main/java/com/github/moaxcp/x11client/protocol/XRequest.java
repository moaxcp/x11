package com.github.moaxcp.x11client.protocol;

import java.io.IOException;

public interface XRequest extends XObject {
  byte getOpCode();
  default int getLength() {
    if(getSize() % 4 == 0) {
      return getSize() / 4;
    }
    return (getSize() + 4 - getSize() % 4) / 4;
  }
  void write(byte offset, X11Output out) throws IOException;
}
