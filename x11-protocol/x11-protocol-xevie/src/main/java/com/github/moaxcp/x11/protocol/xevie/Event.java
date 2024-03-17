package com.github.moaxcp.x11.protocol.xevie;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public class Event implements XStruct {
  public static final String PLUGIN_NAME = "xevie";

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

  public String getPluginName() {
    return PLUGIN_NAME;
  }
}
