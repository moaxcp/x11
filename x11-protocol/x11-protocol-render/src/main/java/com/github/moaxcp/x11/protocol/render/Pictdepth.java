package com.github.moaxcp.x11.protocol.render;

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
public class Pictdepth implements XStruct {
  public static final String PLUGIN_NAME = "render";

  private byte depth;

  @NonNull
  private ImmutableList<Pictvisual> visuals;

  public static Pictdepth readPictdepth(X11Input in) throws IOException {
    Pictdepth.PictdepthBuilder javaBuilder = Pictdepth.builder();
    byte depth = in.readCard8();
    byte[] pad1 = in.readPad(1);
    short numVisuals = in.readCard16();
    byte[] pad3 = in.readPad(4);
    MutableList<Pictvisual> visuals = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(numVisuals));
    for(int i = 0; i < Short.toUnsignedInt(numVisuals); i++) {
      visuals.add(Pictvisual.readPictvisual(in));
    }
    javaBuilder.depth(depth);
    javaBuilder.visuals(visuals.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(depth);
    out.writePad(1);
    short numVisuals = (short) visuals.size();
    out.writeCard16(numVisuals);
    out.writePad(4);
    for(Pictvisual t : visuals) {
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

  public static class PictdepthBuilder {
    public int getSize() {
      return 8 + XObject.sizeOf(visuals);
    }
  }
}
