package com.github.moaxcp.x11.protocol;

import java.io.IOException;

@FunctionalInterface
public interface X11InputConsumer {
  void accept(X11Input in) throws IOException;
}
