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
public class GetPriority implements TwoWayRequest<GetPriorityReply> {
  public static final String PLUGIN_NAME = "sync";

  public static final byte OPCODE = 13;

  private int id;

  public XReplyFunction<GetPriorityReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetPriorityReply.readGetPriorityReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetPriority readGetPriority(X11Input in) throws IOException {
    GetPriority.GetPriorityBuilder javaBuilder = GetPriority.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int id = in.readCard32();
    javaBuilder.id(id);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(id);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetPriorityBuilder {
    public int getSize() {
      return 8;
    }
  }
}
