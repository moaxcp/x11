package com.github.moaxcp.x11client.protocol;

import java.io.IOException;

@FunctionalInterface
public interface XReplySupplier {
  XReply get() throws IOException;
}
