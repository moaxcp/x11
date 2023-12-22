package com.github.moaxcp.x11.protocol;

import java.io.IOException;

@FunctionalInterface
public interface XReplyFunction<T extends XReply> {
  T get(byte field, short sequenceNumber, X11Input in) throws IOException;
}
