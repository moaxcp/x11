package com.github.moaxcp.x11.protocol.shape;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import com.github.moaxcp.x11.protocol.xproto.ClipOrdering;
import com.github.moaxcp.x11.protocol.xproto.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetRectanglesReply implements XReply {
  public static final String PLUGIN_NAME = "shape";

  private byte ordering;

  private short sequenceNumber;

  @NonNull
  private List<Rectangle> rectangles;

  public static GetRectanglesReply readGetRectanglesReply(byte ordering, short sequenceNumber,
      X11Input in) throws IOException {
    GetRectanglesReply.GetRectanglesReplyBuilder javaBuilder = GetRectanglesReply.builder();
    int length = in.readCard32();
    int rectanglesLen = in.readCard32();
    byte[] pad5 = in.readPad(20);
    List<Rectangle> rectangles = new ArrayList<>((int) (Integer.toUnsignedLong(rectanglesLen)));
    for(int i = 0; i < Integer.toUnsignedLong(rectanglesLen); i++) {
      rectangles.add(Rectangle.readRectangle(in));
    }
    javaBuilder.ordering(ordering);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.rectangles(rectangles);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
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
