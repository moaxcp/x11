package com.github.moaxcp.x11client.protocol;

import java.io.IOException;
import java.util.Optional;

public interface XRequest extends XObject {
  Optional<XReplySupplier> getReplySupplier(X11Input in) throws IOException;
  byte getOpCode();
  default int getLength() {
    return (short) ((getSize() + 4 - getSize() % 4) / 4);
  }
  void write(X11Output out) throws IOException;
}
