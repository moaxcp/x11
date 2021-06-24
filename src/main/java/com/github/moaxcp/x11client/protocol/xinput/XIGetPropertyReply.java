package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.XReply;

public interface XIGetPropertyReply extends XReply {
  static XIGetPropertyReply readXIGetPropertyReply(byte field, short sequenceNumber, X11Input in) {
    return null;
  }
}
