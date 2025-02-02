package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Output;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;

import java.io.IOException;

@Value
public class ClientMessageData8 implements ClientMessageDataUnion {
  public static final String PLUGIN_NAME = "xproto";

  ByteList data8;
  byte format = 8;

  public ClientMessageData8(@NonNull ByteList data8) {
    if(data8.size() != 20) {
      throw new IllegalArgumentException("data8 must have length of 20. Got: \"" + data8.size() + "\"");
    }
    this.data8 = data8;
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
    out.writeCard8(data8);
  }
}
