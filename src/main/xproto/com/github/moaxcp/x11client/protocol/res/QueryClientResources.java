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
public class QueryClientResources implements TwoWayRequest<QueryClientResourcesReply>, ResObject {
  public static final byte OPCODE = 2;

  private int xid;

  public XReplyFunction<QueryClientResourcesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryClientResourcesReply.readQueryClientResourcesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryClientResources readQueryClientResources(X11Input in) throws IOException {
    QueryClientResources.QueryClientResourcesBuilder javaBuilder = QueryClientResources.builder();
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

  public static class QueryClientResourcesBuilder {
    public int getSize() {
      return 8;
    }
  }
}
