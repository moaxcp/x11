package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryTextExtentsReply implements XReply, XprotoObject {
  private byte drawDirection;

  private short sequenceNumber;

  private short fontAscent;

  private short fontDescent;

  private short overallAscent;

  private short overallDescent;

  private int overallWidth;

  private int overallLeft;

  private int overallRight;

  public static QueryTextExtentsReply readQueryTextExtentsReply(byte drawDirection,
      short sequenceNumber, X11Input in) throws IOException {
    QueryTextExtentsReply.QueryTextExtentsReplyBuilder javaBuilder = QueryTextExtentsReply.builder();
    int length = in.readCard32();
    short fontAscent = in.readInt16();
    short fontDescent = in.readInt16();
    short overallAscent = in.readInt16();
    short overallDescent = in.readInt16();
    int overallWidth = in.readInt32();
    int overallLeft = in.readInt32();
    int overallRight = in.readInt32();
    in.readPad(4);
    javaBuilder.drawDirection(drawDirection);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.fontAscent(fontAscent);
    javaBuilder.fontDescent(fontDescent);
    javaBuilder.overallAscent(overallAscent);
    javaBuilder.overallDescent(overallDescent);
    javaBuilder.overallWidth(overallWidth);
    javaBuilder.overallLeft(overallLeft);
    javaBuilder.overallRight(overallRight);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeByte(drawDirection);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeInt16(fontAscent);
    out.writeInt16(fontDescent);
    out.writeInt16(overallAscent);
    out.writeInt16(overallDescent);
    out.writeInt32(overallWidth);
    out.writeInt32(overallLeft);
    out.writeInt32(overallRight);
  }

  @Override
  public int getSize() {
    return 28;
  }

  public static class QueryTextExtentsReplyBuilder {
    public QueryTextExtentsReply.QueryTextExtentsReplyBuilder drawDirection(
        FontDraw drawDirection) {
      this.drawDirection = (byte) drawDirection.getValue();
      return this;
    }

    public QueryTextExtentsReply.QueryTextExtentsReplyBuilder drawDirection(byte drawDirection) {
      this.drawDirection = drawDirection;
      return this;
    }

    public int getSize() {
      return 28;
    }
  }
}
