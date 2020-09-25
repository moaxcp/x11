package com.github.moaxcp.x11client.protocol;

public interface XRequest extends XObject {
  byte getOpCode();
  default int getLength() {
    return (short) ((getSize() + 4 - getSize() % 4) / 4);
  }
}
