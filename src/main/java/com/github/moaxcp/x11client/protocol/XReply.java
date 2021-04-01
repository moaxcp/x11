package com.github.moaxcp.x11client.protocol;

import java.io.IOException;

public interface XReply extends XObject, XResponse {
  @Override
  default byte getResponseCode() {
    return 1;
  }
  default int getLength() {
    return (short) ((getSize() + 4 - getSize() % 4) / 4);
  }
  void write(X11Output out) throws IOException;
}
