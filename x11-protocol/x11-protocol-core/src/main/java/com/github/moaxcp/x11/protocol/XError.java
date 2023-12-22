package com.github.moaxcp.x11.protocol;

import java.io.IOException;

public interface XError extends XObject, XResponse {
  default byte getResponseCode() {
    return 0;
  }
  byte getCode();
  byte getFirstErrorOffset();
  short getMinorOpcode();
  byte getMajorOpcode();
  void write(X11Output out) throws IOException;
}
