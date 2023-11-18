package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import java.io.IOException;

public interface ActionUnion extends XObject {
  static ActionUnion readActionUnion(X11Input in) throws IOException {
    return null;
  }

  void write(X11Output out) throws IOException;
}
