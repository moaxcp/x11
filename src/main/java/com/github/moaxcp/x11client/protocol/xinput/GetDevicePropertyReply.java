package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.XReply;

public interface GetDevicePropertyReply extends XReply {
  static GetDevicePropertyReply readGetDevicePropertyReply(byte field, short sequenceNumber, X11Input in) {
    return null;
  }
}
