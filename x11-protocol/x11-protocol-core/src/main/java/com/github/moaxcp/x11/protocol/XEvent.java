package com.github.moaxcp.x11.protocol;

import java.io.IOException;

public interface XEvent extends XObject, XResponse {
  byte getNumber();
  byte getFirstEventOffset();
  void write(X11Output out) throws IOException;
}
