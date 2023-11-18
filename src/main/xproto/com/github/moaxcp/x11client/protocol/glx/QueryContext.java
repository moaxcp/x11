package com.github.moaxcp.x11client.protocol.glx;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryContext implements TwoWayRequest<QueryContextReply>, GlxObject {
  public static final byte OPCODE = 25;

  private int context;

  public XReplyFunction<QueryContextReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryContextReply.readQueryContextReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryContext readQueryContext(X11Input in) throws IOException {
    QueryContext.QueryContextBuilder javaBuilder = QueryContext.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int context = in.readCard32();
    javaBuilder.context(context);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(context);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class QueryContextBuilder {
    public int getSize() {
      return 8;
    }
  }
}
