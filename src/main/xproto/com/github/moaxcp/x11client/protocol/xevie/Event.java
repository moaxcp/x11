package com.github.moaxcp.x11client.protocol.xevie;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;

public class Event implements XStruct, XevieObject {
  public static Event readEvent(X11Input in) throws IOException {
    byte[] pad = in.readPad(32);
    return new Event();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writePad(32);
  }

  @Override
  public int getSize() {
    return 32;
  }
}
