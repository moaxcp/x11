package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryFence implements TwoWayRequest<QueryFenceReply> {
  public static final String PLUGIN_NAME = "sync";

  public static final byte OPCODE = 18;

  private int fence;

  public XReplyFunction<QueryFenceReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryFenceReply.readQueryFenceReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryFence readQueryFence(X11Input in) throws IOException {
    QueryFence.QueryFenceBuilder javaBuilder = QueryFence.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int fence = in.readCard32();
    javaBuilder.fence(fence);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(fence);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryFenceBuilder {
    public int getSize() {
      return 8;
    }
  }
}
