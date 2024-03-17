package com.github.moaxcp.x11.protocol.res;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ResourceIdSpec implements XStruct {
  public static final String PLUGIN_NAME = "res";

  private int resource;

  private int type;

  public static ResourceIdSpec readResourceIdSpec(X11Input in) throws IOException {
    ResourceIdSpec.ResourceIdSpecBuilder javaBuilder = ResourceIdSpec.builder();
    int resource = in.readCard32();
    int type = in.readCard32();
    javaBuilder.resource(resource);
    javaBuilder.type(type);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(resource);
    out.writeCard32(type);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ResourceIdSpecBuilder {
    public int getSize() {
      return 8;
    }
  }
}
