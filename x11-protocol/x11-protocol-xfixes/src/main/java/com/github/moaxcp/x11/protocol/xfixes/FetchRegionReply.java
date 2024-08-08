package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
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
public class FetchRegionReply implements XReply {
  public static final String PLUGIN_NAME = "xfixes";

  private short sequenceNumber;

  @NonNull
  private Rectangle extents;

  @NonNull
  private ImmutableList<Rectangle> rectangles;

  public static FetchRegionReply readFetchRegionReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    FetchRegionReply.FetchRegionReplyBuilder javaBuilder = FetchRegionReply.builder();
    int length = in.readCard32();
    Rectangle extents = Rectangle.readRectangle(in);
    byte[] pad5 = in.readPad(16);
    MutableList<Rectangle> rectangles = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(length) / 2));
    for(int i = 0; i < Integer.toUnsignedLong(length) / 2; i++) {
      rectangles.add(Rectangle.readRectangle(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.extents(extents);
    javaBuilder.rectangles(rectangles.toImmutable());
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    int length = rectangles.size();
    out.writeCard32(getLength());
    extents.write(out);
    out.writePad(16);
    for(Rectangle t : rectangles) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(rectangles);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class FetchRegionReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(rectangles);
    }
  }
}
