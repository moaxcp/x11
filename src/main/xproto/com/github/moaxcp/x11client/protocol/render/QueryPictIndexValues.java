package com.github.moaxcp.x11client.protocol.render;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryPictIndexValues implements TwoWayRequest<QueryPictIndexValuesReply>, RenderObject {
  public static final byte OPCODE = 2;

  private int format;

  public XReplyFunction<QueryPictIndexValuesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryPictIndexValuesReply.readQueryPictIndexValuesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryPictIndexValues readQueryPictIndexValues(X11Input in) throws IOException {
    QueryPictIndexValues.QueryPictIndexValuesBuilder javaBuilder = QueryPictIndexValues.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int format = in.readCard32();
    javaBuilder.format(format);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(format);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class QueryPictIndexValuesBuilder {
    public int getSize() {
      return 8;
    }
  }
}
