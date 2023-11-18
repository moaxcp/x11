package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import java.io.IOException;

public interface ClientMessageDataUnion extends XObject, XprotoObject {
  static ClientMessageDataUnion readClientMessageDataUnion(X11Input in, byte format) throws
      IOException {
    switch(format) {
      case 8:
        return new ClientMessageData8(in.readCard8(20));
      case 16:
        return new ClientMessageData16(in.readCard16(10));
      case 32:
        return new ClientMessageData32(in.readCard32(5));
      default:
        throw new IllegalArgumentException("format must be 8, 16, or 32. Got: " + format + ".");
    }
  }

  void write(X11Output in) throws IOException;

  byte getFormat();
}
