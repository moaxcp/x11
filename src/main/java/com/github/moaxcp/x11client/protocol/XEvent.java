package com.github.moaxcp.x11client.protocol;

import java.io.IOException;

public interface XEvent extends XObject, XResponse {
  void write(X11Output out) throws IOException;
}
