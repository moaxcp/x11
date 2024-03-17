package com.github.moaxcp.x11.protocol.present;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryCapabilities implements TwoWayRequest<QueryCapabilitiesReply> {
  public static final String PLUGIN_NAME = "present";

  public static final byte OPCODE = 4;

  private int target;

  public XReplyFunction<QueryCapabilitiesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryCapabilitiesReply.readQueryCapabilitiesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryCapabilities readQueryCapabilities(X11Input in) throws IOException {
    QueryCapabilities.QueryCapabilitiesBuilder javaBuilder = QueryCapabilities.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int target = in.readCard32();
    javaBuilder.target(target);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(target);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryCapabilitiesBuilder {
    public int getSize() {
      return 8;
    }
  }
}
