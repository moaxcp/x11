package com.github.moaxcp.x11client.protocol.res;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ResourceSizeSpec implements XStruct, ResObject {
  @NonNull
  private ResourceIdSpec spec;

  private int bytes;

  private int refCount;

  private int useCount;

  public static ResourceSizeSpec readResourceSizeSpec(X11Input in) throws IOException {
    ResourceSizeSpec.ResourceSizeSpecBuilder javaBuilder = ResourceSizeSpec.builder();
    ResourceIdSpec spec = ResourceIdSpec.readResourceIdSpec(in);
    int bytes = in.readCard32();
    int refCount = in.readCard32();
    int useCount = in.readCard32();
    javaBuilder.spec(spec);
    javaBuilder.bytes(bytes);
    javaBuilder.refCount(refCount);
    javaBuilder.useCount(useCount);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    spec.write(out);
    out.writeCard32(bytes);
    out.writeCard32(refCount);
    out.writeCard32(useCount);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public static class ResourceSizeSpecBuilder {
    public int getSize() {
      return 20;
    }
  }
}
