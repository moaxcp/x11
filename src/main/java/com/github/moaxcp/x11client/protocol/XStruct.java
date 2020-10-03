package com.github.moaxcp.x11client.protocol;

import java.io.IOException;

public interface XStruct extends XObject {
  void write(X11Output out) throws IOException;
}
