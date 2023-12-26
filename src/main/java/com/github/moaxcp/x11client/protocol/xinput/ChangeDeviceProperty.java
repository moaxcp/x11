package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;

public interface ChangeDeviceProperty extends OneWayRequest {
  byte OPCODE = 37;


  static ChangeDeviceProperty readChangeDeviceProperty(X11Input in) {
    throw new UnsupportedOperationException();
  }
}
