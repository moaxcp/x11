package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Depth implements XStruct {
  public static final String PLUGIN_NAME = "xproto";

  private byte depth;

  @NonNull
  private List<Visualtype> visuals;

  public static Depth readDepth(X11Input in) throws IOException {
    Depth.DepthBuilder javaBuilder = Depth.builder();
    byte depth = in.readCard8();
    byte[] pad1 = in.readPad(1);
    short visualsLen = in.readCard16();
    byte[] pad3 = in.readPad(4);
    List<Visualtype> visuals = new ArrayList<>(Short.toUnsignedInt(visualsLen));
    for(int i = 0; i < Short.toUnsignedInt(visualsLen); i++) {
      visuals.add(Visualtype.readVisualtype(in));
    }
    javaBuilder.depth(depth);
    javaBuilder.visuals(visuals);
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
