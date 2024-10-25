package com.github.moaxcp.x11.protocol;

import java.io.IOException;

public interface X11InputStream extends X11Input, AutoCloseable {

    void mark(int readLimit);

    void reset() throws IOException;

    void close() throws IOException;
}
