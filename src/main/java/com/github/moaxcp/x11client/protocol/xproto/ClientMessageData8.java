package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Output;
import lombok.Value;

import java.io.IOException;

@Value
public class ClientMessageData8 implements ClientMessageDataUnion {
  byte[] data8;

  public ClientMessageData8(byte[] data8) {
    if(data8.length != 20) {
      throw new IllegalArgumentException("data8 must have length of 20. Got: \"" + data8.length + "\"");
    }
    this.data8 = data8;
  }

  @Override
  public int getSize() {
    return 5;
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(data8);
  }
}
