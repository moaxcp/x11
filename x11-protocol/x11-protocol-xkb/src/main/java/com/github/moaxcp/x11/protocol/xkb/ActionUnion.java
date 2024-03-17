package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;

public interface ActionUnion extends XObject {
  static ActionUnion readActionUnion(X11Input in) throws IOException {
    return null;
  }

  void write(X11Output out) throws IOException;
}
