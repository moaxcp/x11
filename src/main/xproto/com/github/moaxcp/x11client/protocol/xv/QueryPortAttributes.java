package com.github.moaxcp.x11client.protocol.xv;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryPortAttributes implements TwoWayRequest<QueryPortAttributesReply>, XvObject {
  public static final byte OPCODE = 15;

  private int port;

  public XReplyFunction<QueryPortAttributesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryPortAttributesReply.readQueryPortAttributesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryPortAttributes readQueryPortAttributes(X11Input in) throws IOException {
    QueryPortAttributes.QueryPortAttributesBuilder javaBuilder = QueryPortAttributes.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int port = in.readCard32();
    javaBuilder.port(port);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(port);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class QueryPortAttributesBuilder {
    public int getSize() {
      return 8;
    }
  }
}
