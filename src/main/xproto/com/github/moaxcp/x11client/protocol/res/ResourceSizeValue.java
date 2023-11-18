package com.github.moaxcp.x11client.protocol.res;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ResourceSizeValue implements XStruct, ResObject {
  @NonNull
  private ResourceSizeSpec size;

  @NonNull
  private List<ResourceSizeSpec> crossReferences;

  public static ResourceSizeValue readResourceSizeValue(X11Input in) throws IOException {
    ResourceSizeValue.ResourceSizeValueBuilder javaBuilder = ResourceSizeValue.builder();
    ResourceSizeSpec size = ResourceSizeSpec.readResourceSizeSpec(in);
    int numCrossReferences = in.readCard32();
    List<ResourceSizeSpec> crossReferences = new ArrayList<>((int) (Integer.toUnsignedLong(numCrossReferences)));
    for(int i = 0; i < Integer.toUnsignedLong(numCrossReferences); i++) {
      crossReferences.add(ResourceSizeSpec.readResourceSizeSpec(in));
    }
    javaBuilder.size(size);
    javaBuilder.crossReferences(crossReferences);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    size.write(out);
    int numCrossReferences = crossReferences.size();
    out.writeCard32(numCrossReferences);
    for(ResourceSizeSpec t : crossReferences) {
      t.write(out);
    }
  }

  @Override
  public int getSize() {
    return 24 + XObject.sizeOf(crossReferences);
  }

  public static class ResourceSizeValueBuilder {
    public int getSize() {
      return 24 + XObject.sizeOf(crossReferences);
    }
  }
}
