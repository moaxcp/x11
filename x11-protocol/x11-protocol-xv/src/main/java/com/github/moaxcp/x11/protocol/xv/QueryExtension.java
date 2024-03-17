package com.github.moaxcp.x11.protocol.xv;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryExtension implements TwoWayRequest<QueryExtensionReply> {
  public static final String PLUGIN_NAME = "xv";

  public static final byte OPCODE = 0;

  public XReplyFunction<QueryExtensionReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryExtensionReply.readQueryExtensionReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryExtension readQueryExtension(X11Input in) throws IOException {
    QueryExtension.QueryExtensionBuilder javaBuilder = QueryExtension.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
  }

  @Override
  public int getSize() {
    return 4;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryExtensionBuilder {
    public int getSize() {
      return 4;
    }
  }
}
