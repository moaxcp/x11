package com.github.moaxcp.x11.protocol;

import java.io.IOException;

@FunctionalInterface
public interface XReadFunction<T> {
  T read(X11Input in) throws IOException;
}
