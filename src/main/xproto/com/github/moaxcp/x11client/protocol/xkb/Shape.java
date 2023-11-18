package com.github.moaxcp.x11client.protocol.xkb;

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
public class Shape implements XStruct, XkbObject {
  private int name;

  private byte primaryNdx;

  private byte approxNdx;

  @NonNull
  private List<Outline> outlines;

  public static Shape readShape(X11Input in) throws IOException {
    Shape.ShapeBuilder javaBuilder = Shape.builder();
    int name = in.readCard32();
    byte nOutlines = in.readCard8();
    byte primaryNdx = in.readCard8();
    byte approxNdx = in.readCard8();
    byte[] pad4 = in.readPad(1);
    List<Outline> outlines = new ArrayList<>(Byte.toUnsignedInt(nOutlines));
    for(int i = 0; i < Byte.toUnsignedInt(nOutlines); i++) {
      outlines.add(Outline.readOutline(in));
    }
    javaBuilder.name(name);
    javaBuilder.primaryNdx(primaryNdx);
    javaBuilder.approxNdx(approxNdx);
    javaBuilder.outlines(outlines);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(name);
    byte nOutlines = (byte) outlines.size();
    out.writeCard8(nOutlines);
    out.writeCard8(primaryNdx);
    out.writeCard8(approxNdx);
    out.writePad(1);
    for(Outline t : outlines) {
      t.write(out);
    }
  }

  @Override
  public int getSize() {
    return 8 + XObject.sizeOf(outlines);
  }

  public static class ShapeBuilder {
    public int getSize() {
      return 8 + XObject.sizeOf(outlines);
    }
  }
}
