package com.github.moaxcp.x11client.protocol.res;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryClientPixmapBytes implements TwoWayRequest<QueryClientPixmapBytesReply>, ResObject {
  public static final byte OPCODE = 3;

  private int xid;

  public XReplyFunction<QueryClientPixmapBytesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryClientPixmapBytesReply.readQueryClientPixmapBytesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryClientPixmapBytes readQueryClientPixmapBytes(X11Input in) throws IOException {
    QueryClientPixmapBytes.QueryClientPixmapBytesBuilder javaBuilder = QueryClientPixmapBytes.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int xid = in.readCard32();
    javaBuilder.xid(xid);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(xid);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class QueryClientPixmapBytesBuilder {
    public int getSize() {
      return 8;
    }
  }
}
