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
public class QueryProviderProperty implements TwoWayRequest<QueryProviderPropertyReply>, RandrObject {
  public static final byte OPCODE = 37;

  private int provider;

  private int property;

  public XReplyFunction<QueryProviderPropertyReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryProviderPropertyReply.readQueryProviderPropertyReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryProviderProperty readQueryProviderProperty(X11Input in) throws IOException {
    QueryProviderProperty.QueryProviderPropertyBuilder javaBuilder = QueryProviderProperty.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int provider = in.readCard32();
    int property = in.readCard32();
    javaBuilder.provider(provider);
    javaBuilder.property(property);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(provider);
    out.writeCard32(property);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class QueryProviderPropertyBuilder {
    public int getSize() {
      return 12;
    }
  }
}
