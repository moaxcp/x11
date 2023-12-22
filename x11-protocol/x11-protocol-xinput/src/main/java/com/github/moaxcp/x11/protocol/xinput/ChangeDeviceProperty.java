package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;

public interface ChangeDeviceProperty extends OneWayRequest {
  byte OPCODE = 37;


  static ChangeDeviceProperty readChangeDeviceProperty(X11Input in) {
    throw new UnsupportedOperationException();
  }
}
