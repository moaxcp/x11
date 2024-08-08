package com.github.moaxcp.x11.protocol.shape;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import com.github.moaxcp.x11.protocol.xproto.ClipOrdering;
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
public class GetRectanglesReply implements XReply {
  public static final String PLUGIN_NAME = "shape";

  private byte ordering;

  private short sequenceNumber;

  @NonNull
  private ImmutableList<Rectangle> rectangles;

  public static GetRectanglesReply readGetRectanglesReply(byte ordering, short sequenceNumber,
      X11Input in) throws IOException {
    GetRectanglesReply.GetRectanglesReplyBuilder javaBuilder = GetRectanglesReply.builder();
    int length = in.readCard32();
    int rectanglesLen = in.readCard32();
    byte[] pad5 = in.readPad(20);
    MutableList<Rectangle> rectangles = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(rectanglesLen)));
    for(int i = 0; i < Integer.toUnsignedLong(rectanglesLen); i++) {
      rectangles.add(Rectangle.readRectangle(in));
    }
    javaBuilder.ordering(ordering);
    javaBuilder.sequenceNumber(sequenceNumber);
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
    out.writeByte(ordering);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    int rectanglesLen = rectangles.size();
    out.writeCard32(rectanglesLen);
    out.writePad(20);
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

  public static class GetRectanglesReplyBuilder {
    public GetRectanglesReply.GetRectanglesReplyBuilder ordering(ClipOrdering ordering) {
      this.ordering = (byte) ordering.getValue();
      return this;
    }

    public GetRectanglesReply.GetRectanglesReplyBuilder ordering(byte ordering) {
      this.ordering = ordering;
      return this;
    }

    public int getSize() {
      return 32 + XObject.sizeOf(rectangles);
    }
  }
}
