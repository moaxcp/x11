package com.github.moaxcp.x11client.protocol.shape;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryExtentsReply implements XReply, ShapeObject {
  private short sequenceNumber;

  private boolean boundingShaped;

  private boolean clipShaped;

  private short boundingShapeExtentsX;

  private short boundingShapeExtentsY;

  private short boundingShapeExtentsWidth;

  private short boundingShapeExtentsHeight;

  private short clipShapeExtentsX;

  private short clipShapeExtentsY;

  private short clipShapeExtentsWidth;

  private short clipShapeExtentsHeight;

  public static QueryExtentsReply readQueryExtentsReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    QueryExtentsReply.QueryExtentsReplyBuilder javaBuilder = QueryExtentsReply.builder();
    int length = in.readCard32();
    boolean boundingShaped = in.readBool();
    boolean clipShaped = in.readBool();
    byte[] pad6 = in.readPad(2);
    short boundingShapeExtentsX = in.readInt16();
    short boundingShapeExtentsY = in.readInt16();
    short boundingShapeExtentsWidth = in.readCard16();
    short boundingShapeExtentsHeight = in.readCard16();
    short clipShapeExtentsX = in.readInt16();
    short clipShapeExtentsY = in.readInt16();
    short clipShapeExtentsWidth = in.readCard16();
    short clipShapeExtentsHeight = in.readCard16();
    in.readPad(4);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.boundingShaped(boundingShaped);
    javaBuilder.clipShaped(clipShaped);
    javaBuilder.boundingShapeExtentsX(boundingShapeExtentsX);
    javaBuilder.boundingShapeExtentsY(boundingShapeExtentsY);
    javaBuilder.boundingShapeExtentsWidth(boundingShapeExtentsWidth);
    javaBuilder.boundingShapeExtentsHeight(boundingShapeExtentsHeight);
    javaBuilder.clipShapeExtentsX(clipShapeExtentsX);
    javaBuilder.clipShapeExtentsY(clipShapeExtentsY);
    javaBuilder.clipShapeExtentsWidth(clipShapeExtentsWidth);
    javaBuilder.clipShapeExtentsHeight(clipShapeExtentsHeight);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeBool(boundingShaped);
    out.writeBool(clipShaped);
    out.writePad(2);
    out.writeInt16(boundingShapeExtentsX);
    out.writeInt16(boundingShapeExtentsY);
    out.writeCard16(boundingShapeExtentsWidth);
    out.writeCard16(boundingShapeExtentsHeight);
    out.writeInt16(clipShapeExtentsX);
    out.writeInt16(clipShapeExtentsY);
    out.writeCard16(clipShapeExtentsWidth);
    out.writeCard16(clipShapeExtentsHeight);
  }

  @Override
  public int getSize() {
    return 28;
  }

  public static class QueryExtentsReplyBuilder {
    public int getSize() {
      return 28;
    }
  }
}
