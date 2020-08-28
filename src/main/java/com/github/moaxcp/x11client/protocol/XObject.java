package com.github.moaxcp.x11client.protocol;

import java.io.IOException;

public interface XObject {
  /**
   * length of object expressed in units of 4 bytes
   * @return
   */
  int getSize();
  void write(X11Output out) throws IOException;
}
