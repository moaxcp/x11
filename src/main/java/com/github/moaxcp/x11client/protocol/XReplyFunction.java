package com.github.moaxcp.x11client.protocol;

import java.io.IOException;

@FunctionalInterface
public interface XReplyFunction {
  XReply get(byte field, short sequenceNumber, X11Input in) throws IOException;
}
