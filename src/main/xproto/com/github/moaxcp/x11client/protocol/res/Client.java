package com.github.moaxcp.x11client.protocol.res;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Client implements XStruct, ResObject {
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

  public static class ClientBuilder {
    public int getSize() {
      return 8;
    }
  }
}
