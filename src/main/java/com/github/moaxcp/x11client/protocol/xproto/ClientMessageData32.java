package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Output;
import lombok.Value;

import java.io.IOException;

@Value
public class ClientMessageData32 implements ClientMessageDataUnion {
  int[] data32;

  public ClientMessageData32(int[] data32) {
    if(data32.length != 20) {
      throw new IllegalArgumentException("data32 must have length of 20. Got: \"" + data32.length + "\"");
    }
    this.data32 = data32;
  }

  @Override
  public int getSize() {
    return 20;
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(data32);
  }
}
