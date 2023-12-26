package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;

public interface XIChangeProperty extends OneWayRequest {
  byte OPCODE = 57;

  static XIChangeProperty readXIChangeProperty(X11Input in) {
    throw new UnsupportedOperationException();
  }
}
