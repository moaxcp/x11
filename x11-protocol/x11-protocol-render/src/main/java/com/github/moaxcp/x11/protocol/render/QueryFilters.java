package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryFilters implements TwoWayRequest<QueryFiltersReply> {
  public static final String PLUGIN_NAME = "render";

  public static final byte OPCODE = 29;

  private int drawable;

  public XReplyFunction<QueryFiltersReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryFiltersReply.readQueryFiltersReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryFilters readQueryFilters(X11Input in) throws IOException {
    QueryFilters.QueryFiltersBuilder javaBuilder = QueryFilters.builder();
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

  public static class QueryFiltersBuilder {
    public int getSize() {
      return 8;
    }
  }
}
