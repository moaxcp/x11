package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Output;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.factory.primitive.IntLists;
import org.eclipse.collections.api.list.primitive.IntList;

import java.io.IOException;

@Value
public class ClientMessageData32 implements ClientMessageDataUnion {
  public static final String PLUGIN_NAME = "xproto";

  IntList data32;
  byte format = 32;

  public ClientMessageData32(int... data32) {
    if(data32.length > 5) {
      throw new IllegalArgumentException("data32 must have length < 20 bytes. Got: \"" + data32.length + "\"");
    }
    var list = IntLists.mutable.withInitialCapacity(data32.length);
    for(int data : data32) {
      list.add(data);
    }

    for(int i = data32.length; i < 5; i++) {
      list.add(0);
    }

    this.data32 = list.toImmutable();
  }

  public ClientMessageData32(@NonNull IntList data32) {
    if(data32.size() != 5) {
      throw new IllegalArgumentException("data32 must have length of 5. Got: \"" + data32.size() + "\"");
    }
    this.data32 = data32;
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
    out.writeCard32(data32);
  }
}
