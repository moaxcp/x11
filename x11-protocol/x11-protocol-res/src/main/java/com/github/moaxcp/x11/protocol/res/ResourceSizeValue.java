package com.github.moaxcp.x11.protocol.res;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

@Value
@Builder
public class ResourceSizeValue implements XStruct {
  public static final String PLUGIN_NAME = "res";

  @NonNull
  private ResourceSizeSpec size;

  @NonNull
  private ImmutableList<ResourceSizeSpec> crossReferences;

  public static ResourceSizeValue readResourceSizeValue(X11Input in) throws IOException {
    ResourceSizeValue.ResourceSizeValueBuilder javaBuilder = ResourceSizeValue.builder();
    ResourceSizeSpec size = ResourceSizeSpec.readResourceSizeSpec(in);
    int numCrossReferences = in.readCard32();
    MutableList<ResourceSizeSpec> crossReferences = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(numCrossReferences)));
    for(int i = 0; i < Integer.toUnsignedLong(numCrossReferences); i++) {
      crossReferences.add(ResourceSizeSpec.readResourceSizeSpec(in));
    }
    javaBuilder.size(size);
    javaBuilder.crossReferences(crossReferences.toImmutable());
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ResourceSizeValueBuilder {
    public int getSize() {
      return 24 + XObject.sizeOf(crossReferences);
    }
  }
}
