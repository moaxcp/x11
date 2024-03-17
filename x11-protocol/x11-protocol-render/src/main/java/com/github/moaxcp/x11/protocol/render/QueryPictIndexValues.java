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
public class QueryPictIndexValues implements TwoWayRequest<QueryPictIndexValuesReply> {
  public static final String PLUGIN_NAME = "render";

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
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int format = in.readCard32();
    javaBuilder.format(format);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(format);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryPictIndexValuesBuilder {
    public int getSize() {
      return 8;
    }
  }
}
