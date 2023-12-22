package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Output;
import lombok.NonNull;
import lombok.Value;

import java.io.IOException;
import java.util.List;

@Value
public class ClientMessageData16 implements ClientMessageDataUnion {
  public static final String PLUGIN_NAME = "xproto";

  List<Short> data16;
  byte format = 16;

  public ClientMessageData16(@NonNull List<Short> data16) {
    if(data16.size() != 10) {
      throw new IllegalArgumentException("data16 must have length of 10. Got: \"" + data16.size() + "\"");
    }
    this.data16 = data16;
  }

  @Override
  public int getSize() {
    return 20;
  }

  @Override
  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public void write(X11Output out) throws IOException {
    out.writeCard16(data16);
  }
}
