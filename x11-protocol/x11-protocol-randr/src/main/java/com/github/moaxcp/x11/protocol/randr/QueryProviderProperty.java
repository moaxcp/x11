package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryProviderProperty implements TwoWayRequest<QueryProviderPropertyReply> {
  public static final String PLUGIN_NAME = "randr";

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
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int provider = in.readCard32();
    int property = in.readCard32();
    javaBuilder.provider(provider);
    javaBuilder.property(property);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(provider);
    out.writeCard32(property);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryProviderPropertyBuilder {
    public int getSize() {
      return 12;
    }
  }
}
