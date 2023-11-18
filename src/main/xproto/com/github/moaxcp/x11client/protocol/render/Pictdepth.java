package com.github.moaxcp.x11client.protocol.render;

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
public class Pictdepth implements XStruct, RenderObject {
  private byte depth;

  @NonNull
  private List<Pictvisual> visuals;

  public static Pictdepth readPictdepth(X11Input in) throws IOException {
    Pictdepth.PictdepthBuilder javaBuilder = Pictdepth.builder();
    byte depth = in.readCard8();
    byte[] pad1 = in.readPad(1);
    short numVisuals = in.readCard16();
    byte[] pad3 = in.readPad(4);
    List<Pictvisual> visuals = new ArrayList<>(Short.toUnsignedInt(numVisuals));
    for(int i = 0; i < Short.toUnsignedInt(numVisuals); i++) {
      visuals.add(Pictvisual.readPictvisual(in));
    }
    javaBuilder.depth(depth);
    javaBuilder.visuals(visuals);
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

  public static class PictdepthBuilder {
    public int getSize() {
      return 8 + XObject.sizeOf(visuals);
    }
  }
}
