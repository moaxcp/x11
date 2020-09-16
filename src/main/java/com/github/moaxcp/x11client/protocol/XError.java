package com.github.moaxcp.x11client.protocol;

public interface XError extends XObject {
  int getSize();
  byte getCode();
  short getSequenceNumber();
  short getMinorOpcode();
  byte getMajorOpcode();
}
