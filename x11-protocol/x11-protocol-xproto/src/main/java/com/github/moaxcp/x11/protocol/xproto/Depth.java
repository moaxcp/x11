package com.github.moaxcp.x11.protocol.xproto;

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
public class Depth implements XStruct {
  public static final String PLUGIN_NAME = "xproto";

  private byte depth;

  @NonNull
  private ImmutableList<Visualtype> visuals;

  public static Depth readDepth(X11Input in) throws IOException {
    Depth.DepthBuilder javaBuilder = Depth.builder();
    byte depth = in.readCard8();
    byte[] pad1 = in.readPad(1);
    short visualsLen = in.readCard16();
    byte[] pad3 = in.readPad(4);
    MutableList<Visualtype> visuals = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(visualsLen));
    for(int i = 0; i < Short.toUnsignedInt(visualsLen); i++) {
      visuals.add(Visualtype.readVisualtype(in));
    }
    javaBuilder.depth(depth);
    javaBuilder.visuals(visuals.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(depth);
    out.writePad(1);
    short visualsLen = (short) visuals.size();
    out.writeCard16(visualsLen);
    out.writePad(4);
    for(Visualtype t : visuals) {
      t.write(out);
    }
  }

  @Override
  public int getSize() {
    return 8 + XObject.sizeOf(visuals);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DepthBuilder {
    public int getSize() {
      return 8 + XObject.sizeOf(visuals);
    }
  }
}
