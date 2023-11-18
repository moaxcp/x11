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
public class QueryEncodings implements TwoWayRequest<QueryEncodingsReply>, XvObject {
  public static final byte OPCODE = 2;

  private int port;

  public XReplyFunction<QueryEncodingsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryEncodingsReply.readQueryEncodingsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryEncodings readQueryEncodings(X11Input in) throws IOException {
    QueryEncodings.QueryEncodingsBuilder javaBuilder = QueryEncodings.builder();
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

  public static class QueryEncodingsBuilder {
    public int getSize() {
      return 8;
    }
  }
}
