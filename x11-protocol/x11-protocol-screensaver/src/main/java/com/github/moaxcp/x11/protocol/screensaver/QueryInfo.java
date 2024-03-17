package com.github.moaxcp.x11.protocol.screensaver;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryInfo implements TwoWayRequest<QueryInfoReply> {
  public static final String PLUGIN_NAME = "screensaver";

  public static final byte OPCODE = 1;

  private int drawable;

  public XReplyFunction<QueryInfoReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryInfoReply.readQueryInfoReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryInfo readQueryInfo(X11Input in) throws IOException {
    QueryInfo.QueryInfoBuilder javaBuilder = QueryInfo.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int drawable = in.readCard32();
    javaBuilder.drawable(drawable);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryInfoBuilder {
    public int getSize() {
      return 8;
    }
  }
}
