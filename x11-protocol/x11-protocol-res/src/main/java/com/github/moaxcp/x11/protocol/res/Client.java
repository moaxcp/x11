package com.github.moaxcp.x11.protocol.res;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Client implements XStruct {
  public static final String PLUGIN_NAME = "res";

  private int resourceBase;

  private int resourceMask;

  public static Client readClient(X11Input in) throws IOException {
    Client.ClientBuilder javaBuilder = Client.builder();
    int resourceBase = in.readCard32();
    int resourceMask = in.readCard32();
    javaBuilder.resourceBase(resourceBase);
    javaBuilder.resourceMask(resourceMask);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(resourceBase);
    out.writeCard32(resourceMask);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ClientBuilder {
    public int getSize() {
      return 8;
    }
  }
}
