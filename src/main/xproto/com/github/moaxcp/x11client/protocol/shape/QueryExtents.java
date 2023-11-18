package com.github.moaxcp.x11client.protocol.shape;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryExtents implements TwoWayRequest<QueryExtentsReply>, ShapeObject {
  public static final byte OPCODE = 5;

  private int destinationWindow;

  public XReplyFunction<QueryExtentsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryExtentsReply.readQueryExtentsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryExtents readQueryExtents(X11Input in) throws IOException {
    QueryExtents.QueryExtentsBuilder javaBuilder = QueryExtents.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int destinationWindow = in.readCard32();
    javaBuilder.destinationWindow(destinationWindow);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(destinationWindow);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class QueryExtentsBuilder {
    public int getSize() {
      return 8;
    }
  }
}
