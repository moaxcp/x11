package com.github.moaxcp.x11.protocol.dri2;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetMSC implements TwoWayRequest<GetMSCReply> {
  public static final String PLUGIN_NAME = "dri2";

  public static final byte OPCODE = 9;

  private int drawable;

  public XReplyFunction<GetMSCReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetMSCReply.readGetMSCReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetMSC readGetMSC(X11Input in) throws IOException {
    GetMSC.GetMSCBuilder javaBuilder = GetMSC.builder();
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

  public static class GetMSCBuilder {
    public int getSize() {
      return 8;
    }
  }
}
