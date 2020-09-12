package com.github.moaxcp.x11client.protocol;

public interface XError extends XObject {
  int getSize();
  byte getNumber();
}
