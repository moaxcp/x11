package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Output;
import lombok.Value;

import java.io.IOException;
import java.util.List;

@Value
public class ClientMessageData16 implements ClientMessageDataUnion {
  List<Short> data16;

  public ClientMessageData16(List<Short> data16) {
    if(data16.size() != 10) {
      throw new IllegalArgumentException("data16 must have length of 20. Got: \"" + data16.size() + "\"");
    }
    this.data16 = data16;
  }

  @Override
  public int getSize() {
    return 20;
  }

  public void write(X11Output out) throws IOException {
    out.writeCard16(data16);
  }
}
