package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Output;
import lombok.NonNull;
import lombok.Value;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Value
public class ClientMessageData32 implements ClientMessageDataUnion {
  public static final String PLUGIN_NAME = "xproto";

  List<Integer> data32;
  byte format = 32;

  public ClientMessageData32(int... data32) {
    if(data32.length > 5) {
      throw new IllegalArgumentException("data32 must have length < 20 bytes. Got: \"" + data32.length + "\"");
    }
    List<Integer> list = new ArrayList<>();
    for(int data : data32) {
      list.add(data);
    }

    for(int i = data32.length; i < 5; i++) {
      list.add(0);
    }

    this.data32 = list;
  }

  public ClientMessageData32(@NonNull List<Integer> data32) {
    if(data32.size() != 5) {
      throw new IllegalArgumentException("data32 must have length of 5. Got: \"" + data32.size() + "\"");
    }
    this.data32 = new ArrayList<>(data32);
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
