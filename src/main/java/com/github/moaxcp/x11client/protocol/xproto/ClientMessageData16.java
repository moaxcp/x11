package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Output;
import lombok.Value;

import java.io.IOException;

@Value
public class ClientMessageData16 implements ClientMessageDataUnion {
  short[] data16;

  public ClientMessageData16(short[] data16) {
    if(data16.length != 10) {
      throw new IllegalArgumentException("data16 must have length of 20. Got: \"" + data16.length + "\"");
    }
    this.data16 = data16;
  }

  @Override
  public int getSize() {
    return 5;
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(data16);
  }
}
