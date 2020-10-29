package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Output;
import lombok.Value;

import java.io.IOException;
import java.util.List;

@Value
public class ClientMessageData32 implements ClientMessageDataUnion {
  List<Integer> data32;

  public ClientMessageData32(List<Integer> data32) {
    if(data32.size() != 20) {
      throw new IllegalArgumentException("data32 must have length of 20. Got: \"" + data32.size() + "\"");
    }
    this.data32 = data32;
  }

  @Override
  public int getSize() {
    return 20;
  }

  public void write(X11Output out) throws IOException {
    out.writeCard32(data32);
  }
}
