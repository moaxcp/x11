package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.xproto.Rectangle;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

@Value
@Builder
public class CreateRegion implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";

  public static final byte OPCODE = 5;

  private int region;

  @NonNull
  private ImmutableList<Rectangle> rectangles;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateRegion readCreateRegion(X11Input in) throws IOException {
    CreateRegion.CreateRegionBuilder javaBuilder = CreateRegion.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int region = in.readCard32();
    javaStart += 4;
    MutableList<Rectangle> rectangles = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(length) - javaStart);
    while(javaStart < Short.toUnsignedInt(length) * 4) {
      Rectangle baseObject = Rectangle.readRectangle(in);
      rectangles.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.region(region);
    javaBuilder.rectangles(rectangles.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(region);
    for(Rectangle t : rectangles) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + XObject.sizeOf(rectangles);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateRegionBuilder {
    public int getSize() {
      return 8 + XObject.sizeOf(rectangles);
    }
  }
}
