package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryContext implements TwoWayRequest<QueryContextReply> {
  public static final String PLUGIN_NAME = "glx";

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
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int context = in.readCard32();
    javaBuilder.context(context);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(context);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryContextBuilder {
    public int getSize() {
      return 8;
    }
  }
}
