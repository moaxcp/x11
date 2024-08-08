package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

@Value
@Builder
public class PolyFillArc implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 71;

  private int drawable;

  private int gc;

  @NonNull
  private ImmutableList<Arc> arcs;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PolyFillArc readPolyFillArc(X11Input in) throws IOException {
    PolyFillArc.PolyFillArcBuilder javaBuilder = PolyFillArc.builder();
    int javaStart = 1;
    byte[] pad1 = in.readPad(1);
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int drawable = in.readCard32();
    javaStart += 4;
    int gc = in.readCard32();
    javaStart += 4;
    MutableList<Arc> arcs = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(length) - javaStart);
    while(javaStart < Short.toUnsignedInt(length) * 4) {
      Arc baseObject = Arc.readArc(in);
      arcs.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.drawable(drawable);
    javaBuilder.gc(gc);
    javaBuilder.arcs(arcs.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(gc);
    for(Arc t : arcs) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + XObject.sizeOf(arcs);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class PolyFillArcBuilder {
    public int getSize() {
      return 12 + XObject.sizeOf(arcs);
    }
  }
}
