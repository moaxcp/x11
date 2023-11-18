package com.github.moaxcp.x11client.protocol.res;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Type implements XStruct, ResObject {
  private int resourceType;

  private int count;

  public static Type readType(X11Input in) throws IOException {
    Type.TypeBuilder javaBuilder = Type.builder();
    int resourceType = in.readCard32();
    int count = in.readCard32();
    javaBuilder.resourceType(resourceType);
    javaBuilder.count(count);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(resourceType);
    out.writeCard32(count);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class TypeBuilder {
    public int getSize() {
      return 8;
    }
  }
}
