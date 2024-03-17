package com.github.moaxcp.x11.protocol.dpms;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetTimeouts implements TwoWayRequest<GetTimeoutsReply> {
  public static final String PLUGIN_NAME = "dpms";

  public static final byte OPCODE = 2;

  public XReplyFunction<GetTimeoutsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetTimeoutsReply.readGetTimeoutsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetTimeouts readGetTimeouts(X11Input in) throws IOException {
    GetTimeouts.GetTimeoutsBuilder javaBuilder = GetTimeouts.builder();
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

  public static class GetTimeoutsBuilder {
    public int getSize() {
      return 4;
    }
  }
}
