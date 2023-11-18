package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryOutputProperty implements TwoWayRequest<QueryOutputPropertyReply>, RandrObject {
  public static final byte OPCODE = 11;

  private int output;

  private int property;

  public XReplyFunction<QueryOutputPropertyReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryOutputPropertyReply.readQueryOutputPropertyReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryOutputProperty readQueryOutputProperty(X11Input in) throws IOException {
    QueryOutputProperty.QueryOutputPropertyBuilder javaBuilder = QueryOutputProperty.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int output = in.readCard32();
    int property = in.readCard32();
    javaBuilder.output(output);
    javaBuilder.property(property);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(output);
    out.writeCard32(property);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class QueryOutputPropertyBuilder {
    public int getSize() {
      return 12;
    }
  }
}
