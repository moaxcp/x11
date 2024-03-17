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
public class GetParam implements TwoWayRequest<GetParamReply> {
  public static final String PLUGIN_NAME = "dri2";

  public static final byte OPCODE = 13;

  private int drawable;

  private int param;

  public XReplyFunction<GetParamReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetParamReply.readGetParamReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetParam readGetParam(X11Input in) throws IOException {
    GetParam.GetParamBuilder javaBuilder = GetParam.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int drawable = in.readCard32();
    int param = in.readCard32();
    javaBuilder.drawable(drawable);
    javaBuilder.param(param);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(param);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetParamBuilder {
    public int getSize() {
      return 12;
    }
  }
}
