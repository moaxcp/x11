package com.github.moaxcp.x11.protocol;

import java.io.IOException;

public interface X11OutputStream extends X11Output, AutoCloseable {
    void flush() throws IOException;

    void close() throws IOException;
}
