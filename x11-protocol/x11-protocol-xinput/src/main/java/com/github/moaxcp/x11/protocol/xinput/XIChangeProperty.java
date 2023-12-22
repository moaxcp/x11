package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;

public interface XIChangeProperty extends OneWayRequest {
  byte OPCODE = 57;

  static XIChangeProperty readXIChangeProperty(X11Input in) {
    throw new UnsupportedOperationException();
  }
}
