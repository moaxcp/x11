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
public class QueryAdaptors implements TwoWayRequest<QueryAdaptorsReply> {
  public static final String PLUGIN_NAME = "xv";

  public static final byte OPCODE = 1;

  private int window;

  public XReplyFunction<QueryAdaptorsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryAdaptorsReply.readQueryAdaptorsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryAdaptors readQueryAdaptors(X11Input in) throws IOException {
    QueryAdaptors.QueryAdaptorsBuilder javaBuilder = QueryAdaptors.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    javaBuilder.window(window);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryAdaptorsBuilder {
    public int getSize() {
      return 8;
    }
  }
}
